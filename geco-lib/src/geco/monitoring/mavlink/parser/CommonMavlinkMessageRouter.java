package geco.monitoring.mavlink.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_command_ack;
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
import com.MAVLink.common.msg_rc_channels_raw;
import com.MAVLink.common.msg_scaled_imu2;
import com.MAVLink.common.msg_scaled_pressure;
import com.MAVLink.common.msg_statustext;
import com.MAVLink.common.msg_sys_status;
import com.MAVLink.common.msg_system_time;
import com.MAVLink.common.msg_terrain_report;
import com.MAVLink.common.msg_terrain_request;
import com.MAVLink.common.msg_vfr_hud;
import com.MAVLink.common.msg_vibration;

import geco.io.IDataConnector;
import geco.io.mavlink.MavlinkMessageReceiver;
import geco.monitoring.mavlink.listener.ICommonMavlinkMessageListener;

public class CommonMavlinkMessageRouter extends MavlinkMessageReceiver implements ICommonMavlinkMessageRouter
{
	
	@Override
	public void onMessageReceived(MAVLinkMessage p_Message) {
		this.parseMessage(p_Message);
	}
	
	
	private Map<Integer, ArrayList<ICommonMavlinkMessageListener>> 		m_Listeners; 
	
	
	public CommonMavlinkMessageRouter() { this.m_Listeners = new HashMap<Integer, ArrayList<ICommonMavlinkMessageListener>>(); }
	
	@Override
	public void addListener(ICommonMavlinkMessageListener p_Listener)
	{
		if (!this.m_Listeners.containsKey(p_Listener.getSystemId()))
			this.m_Listeners.put(p_Listener.getSystemId(),new ArrayList<ICommonMavlinkMessageListener>());
		
		if (!this.m_Listeners.get(p_Listener.getSystemId()).contains(p_Listener))
			this.m_Listeners.get(p_Listener.getSystemId()).add(p_Listener);
	}
	
	@Override
	public void removeListener(ICommonMavlinkMessageListener p_Listener)
	{
		if (this.m_Listeners.containsKey(p_Listener.getSystemId()))
			if (this.m_Listeners.get(p_Listener.getSystemId()).contains(p_Listener))
				this.m_Listeners.get(p_Listener.getSystemId()).remove(p_Listener);
	}

	private void parseMessage(MAVLinkMessage p_Message) 
	{
		Integer l_SystemId = p_Message.sysid;
		
		if (p_Message instanceof msg_heartbeat)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onHeartbeatMessageReceived((msg_heartbeat) p_Message);
				}); 
			}
		else if (p_Message instanceof msg_raw_imu)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onRawImuMessageReceived((msg_raw_imu) p_Message);
				}); 
			}
		else if (p_Message instanceof msg_scaled_imu2)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onScaledImu2MessageReceived((msg_scaled_imu2) p_Message);
				}); 
			}
		else if (p_Message instanceof msg_scaled_pressure)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onScaledPressureMessageReceived((msg_scaled_pressure) p_Message);
				}); 
			}
		else if (p_Message instanceof msg_sys_status)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onPowerStatusMessageReceived((msg_power_status) p_Message);
				}); 
			}		
		else if (p_Message instanceof msg_power_status)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onPowerStatusMessageReceived((msg_power_status) p_Message);
				}); 
			}
		else if (p_Message instanceof msg_mission_current)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onMissionCurrentMessageReceived((msg_mission_current) p_Message);
				}); 
			}
		else if (p_Message instanceof msg_gps_raw_int)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onGpsRawIntMessageReceived((msg_gps_raw_int) p_Message);
				});
			}
		else if (p_Message instanceof msg_global_position_int)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onGlobalPositionMessageReceived((msg_global_position_int) p_Message);
				});
			}
		else if (p_Message instanceof msg_local_position_ned)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onLocalPositionMessageReceived((msg_local_position_ned) p_Message);
				});
			}
		else if (p_Message instanceof msg_rc_channels_raw)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onRcChannelRawMessageReceived((msg_rc_channels_raw) p_Message);
				});
			}
		else if (p_Message instanceof msg_rc_channels)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onRcChannelsMessageReceived((msg_rc_channels) p_Message);
				});
			}
		else if (p_Message instanceof msg_vfr_hud)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onRcChannelsMessageReceived((msg_rc_channels) p_Message);
				});
			}
		else if (p_Message instanceof msg_system_time)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onsystemTimeMessageReceived((msg_system_time) p_Message);
				});
			}
		else if (p_Message instanceof msg_vibration)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onVibrationMessageReceived((msg_vibration) p_Message);
				});
			}
		else if (p_Message instanceof msg_attitude)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onAttitudeMessageReceived((msg_attitude) p_Message);
				});
			}
		else if (p_Message instanceof msg_terrain_report)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onTerrainReportMessageReceived((msg_terrain_report) p_Message);
				});
			}
		else if (p_Message instanceof msg_terrain_request)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onTerrainRequestMessageReceived((msg_terrain_request) p_Message);
				});
			}
		else if (p_Message instanceof msg_statustext)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onstatusTextMessageReceived((msg_statustext) p_Message);
				});
			}
		else if (p_Message instanceof msg_home_position)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onHomePositionMessageReceived((msg_home_position) p_Message);
				});
			}
		else if (p_Message instanceof msg_nav_controller_output)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onNavControllerOutputMessageReceived((msg_nav_controller_output) p_Message);
				});
			}
		else if (p_Message instanceof msg_position_target_global_int)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onPositionTargetGlobalMessageReceived((msg_position_target_global_int) p_Message);
				});
			}
		else if (p_Message instanceof msg_param_value)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onParamValueMessageReceived((msg_param_value) p_Message);
				});
			}
		else if (p_Message instanceof msg_command_ack)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onCommandAckMessageReceived((msg_command_ack) p_Message);
				});
			}
		else if (p_Message instanceof msg_mission_request)
			{
				this.m_Listeners.get(l_SystemId).forEach((l_Listener) -> {
					l_Listener.onMissionRequestMessageReceived((msg_mission_request) p_Message);
				});
			}
	}

	
	
	@Override
	public void onConnected(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionLost(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReconnected(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

	
	
}
