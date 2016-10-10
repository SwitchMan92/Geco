package geco.control.mavlink.common.actor;

import java.util.ArrayList;
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
import com.MAVLink.common.msg_mission_current;
import com.MAVLink.common.msg_mission_request;
import com.MAVLink.common.msg_nav_controller_output;
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

import geco.io.mavlink.IMavlinkMessageEmitter;
import geco.monitoring.mavlink.listener.ICommonMavlinkMessageListener;
import geco.navigation.waypoint.IGlobalWaypoint;

public abstract class CommonMavlinkActor implements ICommonMavlinkMessageListener, ICommonMavlinkActor, IMavlinkMessageEmitter
{

	private Lock		m_SetModeLock;
	private Condition 	m_SetModeCondition;
	private short		m_ExpectedMode;
	
	public CommonMavlinkActor() 
	{
		this.m_SetModeLock 			= 	new ReentrantLock();
		this.m_SetModeCondition 	= 	this.m_SetModeLock.newCondition();
	}
	
	
	@Override
	public void sendHearbeat() 
	{
		
	}

	@Override
	public void sendWaypoints(ArrayList<IGlobalWaypoint> p_Waypoints) 
	{
		
	}

	@Override
	public void setMode(short p_Mode) 
	{
		msg_set_mode l_Message = new msg_set_mode();
		
		l_Message.base_mode = 255;
		l_Message.custom_mode = (short)p_Mode; 
		
		try 
			{
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
	}

	@Override
	public void armMotors() 
	{
		msg_command_long l_Command = new msg_command_long();
		
		l_Command.command = MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
		
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
		return 0;
	}

	@Override
	public int getComponentId() 
	{
		return 0;
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
		if (p_Message.base_mode == this.m_ExpectedMode)
			this.m_SetModeCondition.signalAll();
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
	public void onstatusTextMessageReceived(msg_statustext p_Message) {
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
	public void onMissionRequestMessageReceived(msg_mission_request p_Message) {
		// TODO Auto-generated method stub
		
	}

}
