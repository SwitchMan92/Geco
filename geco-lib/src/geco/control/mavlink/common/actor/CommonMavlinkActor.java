package geco.control.mavlink.common.actor;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_global_position_int;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_home_position;
import com.MAVLink.common.msg_local_position_ned;
import com.MAVLink.common.msg_mission_ack;
import com.MAVLink.common.msg_mission_count;
import com.MAVLink.common.msg_mission_current;
import com.MAVLink.common.msg_mission_item;
import com.MAVLink.common.msg_mission_request;
import com.MAVLink.common.msg_nav_controller_output;
import com.MAVLink.common.msg_param_request_list;
import com.MAVLink.common.msg_param_set;
import com.MAVLink.common.msg_param_value;
import com.MAVLink.common.msg_position_target_global_int;
import com.MAVLink.common.msg_power_status;
import com.MAVLink.common.msg_raw_imu;
import com.MAVLink.common.msg_rc_channels;
import com.MAVLink.common.msg_rc_channels_override;
import com.MAVLink.common.msg_rc_channels_raw;
import com.MAVLink.common.msg_rc_channels_scaled;
import com.MAVLink.common.msg_scaled_imu;
import com.MAVLink.common.msg_scaled_imu2;
import com.MAVLink.common.msg_scaled_imu3;
import com.MAVLink.common.msg_scaled_pressure;
import com.MAVLink.common.msg_scaled_pressure2;
import com.MAVLink.common.msg_scaled_pressure3;
import com.MAVLink.common.msg_set_mode;
import com.MAVLink.common.msg_statustext;
import com.MAVLink.common.msg_sys_status;
import com.MAVLink.common.msg_system_time;
import com.MAVLink.common.msg_terrain_report;
import com.MAVLink.common.msg_terrain_request;
import com.MAVLink.common.msg_vfr_hud;
import com.MAVLink.common.msg_vibration;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_FRAME;

import geco.io.mavlink.IMavlinkMessageEmitter;
import geco.monitoring.mavlink.listener.ICommonMavlinkMessageListener;
import geco.navigation.waypoint.IGlobalWaypoint;

public abstract class CommonMavlinkActor implements ICommonMavlinkMessageListener, ICommonMavlinkActor, IMavlinkMessageEmitter
{

	private int 				m_SystemId;
	private int 				m_ComponentId;
	
	private Lock				m_SetModeLock;
	private Condition 			m_SetModeCondition;
	private short				m_ExpectedMode;
	
	private Lock				m_SetWaypointsLock;
	private Condition			m_SetWaypointsCondition;
	private msg_mission_request m_CurrentMissionRequest;
	
	private Lock				m_WaypointAckLock;
	private Condition			m_waypointAckCondition;
	
	
	public CommonMavlinkActor() 
	{
		this.m_SetModeLock 				= 	new ReentrantLock();
		this.m_SetModeCondition 		= 	this.m_SetModeLock.newCondition();
		
		this.m_SetWaypointsLock			=	new ReentrantLock();
		this.m_SetWaypointsCondition	=	this.m_SetWaypointsLock.newCondition();
		
		this.m_WaypointAckLock			=	new ReentrantLock();
		this.m_waypointAckCondition		=	this.m_WaypointAckLock.newCondition();
	}
	
	
	public void requestParameters()
	{
		msg_param_request_list l_Message = new msg_param_request_list();
		
		l_Message.target_system = 0;
		l_Message.target_component = 0;
		
		try 
		{
			this.sendMessage(l_Message);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setParameter(String p_Name, float p_Value)
	{
		msg_param_set l_Message = new msg_param_set();
		
		l_Message.target_system = 0;
		l_Message.target_component = 0;
		
		l_Message.param_id = p_Name.getBytes();
		l_Message.param_value = p_Value;

		try 
		{
			this.sendMessage(l_Message);
			Thread.sleep(50);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.flush();
		}
	}
	
	@Override
	public void sendHearbeat() 
	{
		
	}

	@Override
	public synchronized void sendWaypoints(ArrayList<IGlobalWaypoint> p_Waypoints) 
	{
		
		this.m_SetWaypointsLock.lock();
		
		try 
			{
				msg_mission_count l_MissionCount = new msg_mission_count();
				
				l_MissionCount.target_system 	= (short) this.m_SystemId;
				
				l_MissionCount.count = p_Waypoints.size();
			
				this.sendMessage(l_MissionCount);
				
				for (int i= 0; i < p_Waypoints.size(); i++)
				{
					this.m_WaypointAckLock.lock();
					
					try 
						{
						
							boolean l_Ok = this.m_SetWaypointsCondition.await(5, TimeUnit.SECONDS);
							
							if (!l_Ok)
								throw new Exception("Didn't receive waypoint request");
							
							msg_mission_item l_ItemMessage 	= 	new msg_mission_item();
							
							l_ItemMessage.target_system 	= 	(short) this.m_SystemId;
							
							l_ItemMessage.current 			= 	0;
							l_ItemMessage.frame 			= 	MAV_FRAME.MAV_FRAME_GLOBAL;
							l_ItemMessage.seq 				= 	this.m_CurrentMissionRequest.seq;
							l_ItemMessage.x 				= 	p_Waypoints.get(i).getLatitude();
							l_ItemMessage.y 				= 	p_Waypoints.get(i).getLongitude();
							l_ItemMessage.z 				= 	p_Waypoints.get(i).getHeight();
							l_ItemMessage.autocontinue 		= 	(short) (i == (p_Waypoints.size()-1) ? 0 : 1);
						
							this.sendMessage(l_ItemMessage);
							
							l_Ok = this.m_waypointAckCondition.await(5, TimeUnit.SECONDS);
							
							if (!l_Ok)
								throw new Exception("Didn't receive waypoint ack");
							
						} 
					catch (Exception e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					finally
						{
							this.m_WaypointAckLock.unlock();
						}
				}	
			} 
		catch (Exception e) 
			{
				e.printStackTrace();
			}
		finally
			{
				this.m_SetWaypointsLock.unlock();
			}
	}

	@Override
	public void setMode(short p_Mode, short p_CustomMode) 
	{
		
		this.m_SetModeLock.lock();
		
		try 
			{
				msg_set_mode l_Message = new msg_set_mode();
				
				l_Message.target_system = (short) this.m_SystemId;
				
				l_Message.base_mode = (short)p_Mode;
				l_Message.custom_mode = (short)p_CustomMode;
		
				this.m_ExpectedMode = p_Mode;
				this.sendMessage(l_Message);
				this.m_SetModeCondition.await();
				System.err.println("Mode successfuly set");
			} 
		catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally
			{
				this.m_SetModeLock.unlock();
			}
	}

	@Override
	public void armMotors() 
	{
		msg_command_long l_Command = new msg_command_long();
		
		l_Command.command = MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
		
		l_Command.target_system = (short) this.m_SystemId;
		
		l_Command.param1 = 1f;
		l_Command.param2 = 0f;
		l_Command.param3 = 0f;
		l_Command.param4 = 0f;
		l_Command.param5 = 0f;
		l_Command.param6 = 0f;
		l_Command.param7 = 0f;
		
		try 
			{
				this.sendMessage(l_Command);
			} 
		catch (Exception e) 
			{
				e.printStackTrace();
			}
	}

	@Override
	public void goToWaypoint(int p_WaypointIndex) 
	{
		
	}

	@Override
	public int getSystemId() 
	{
		return this.m_SystemId;
	}

	@Override
	public int getComponentId() 
	{
		return this.m_ComponentId;
	}

	@Override
	public void onRawImuMessageReceived(msg_raw_imu p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScaledImuMessageReceived(msg_scaled_imu p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScaledImu2MessageReceived(msg_scaled_imu2 p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScaledImu3MessageReceived(msg_scaled_imu3 p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScaledPressureMessageReceived(msg_scaled_pressure p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScaledPressure2MessageReceived(msg_scaled_pressure2 p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScaledPressure3MessageReceived(msg_scaled_pressure3 p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHeartbeatMessageReceived(msg_heartbeat p_Message) 
	{
		this.m_SetModeLock.lock();
		
		try
			{
				if (p_Message.base_mode == this.m_ExpectedMode)
					this.m_SetModeCondition.signalAll();
			}
		finally
			{
				this.m_SetModeLock.unlock();
			}
	}

	@Override
	public void onSysStatusMessageReceived(msg_sys_status p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPowerStatusMessageReceived(msg_power_status p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMissionCurrentMessageReceived(msg_mission_current p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGpsRawIntMessageReceived(msg_gps_raw_int p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGlobalPositionMessageReceived(msg_global_position_int p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocalPositionMessageReceived(msg_local_position_ned p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRcChannelRawMessageReceived(msg_rc_channels_raw p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRcChannelsMessageReceived(msg_rc_channels p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRcChannelsScaledMessageReceived(msg_rc_channels_scaled p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRcChannelsOverrideMessageReceived(msg_rc_channels_override p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVfrHudMessageReceived(msg_vfr_hud p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemTimeMessageReceived(msg_system_time p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVibrationMessageReceived(msg_vibration p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttitudeMessageReceived(msg_attitude p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTerrainReportMessageReceived(msg_terrain_report p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTerrainRequestMessageReceived(msg_terrain_request p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusTextMessageReceived(msg_statustext p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHomePositionMessageReceived(msg_home_position p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNavControllerOutputMessageReceived(msg_nav_controller_output p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPositionTargetGlobalMessageReceived(msg_position_target_global_int p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onParamValueMessageReceived(msg_param_value p_Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCommandAckMessageReceived(msg_command_ack p_Message) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onMissionAckMessageReceived(msg_mission_ack p_Message)
	{
		this.m_WaypointAckLock.lock();
		
		try
			{
				this.m_waypointAckCondition.signalAll();
			}
		finally
			{
				this.m_WaypointAckLock.unlock();
			}
	}

	@Override
	public void onMissionRequestMessageReceived(msg_mission_request p_Message) 
	{
	
		this.m_SetWaypointsLock.lock();
		
		try 
			{
				this.m_SetWaypointsCondition.signalAll();
			} 
		catch (Exception e) 
			{
				this.m_SetWaypointsLock.unlock();
			}
	}

}
