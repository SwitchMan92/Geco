package geco.monitoring.mavlink.listener;

import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_global_position_int;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_home_position;
import com.MAVLink.common.msg_local_position_ned;
import com.MAVLink.common.msg_mission_ack;
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
import com.MAVLink.common.msg_statustext;
import com.MAVLink.common.msg_sys_status;
import com.MAVLink.common.msg_system_time;
import com.MAVLink.common.msg_terrain_report;
import com.MAVLink.common.msg_terrain_request;
import com.MAVLink.common.msg_vfr_hud;
import com.MAVLink.common.msg_vibration;

public interface ICommonMavlinkMessageListener 
{

	int		getSystemId								();
	int		getComponentId							();
	
	void 	onRawImuMessageReceived					(msg_raw_imu p_Message);
	void	onScaledImuMessageReceived				(msg_scaled_imu p_Message);
	void	onScaledImu2MessageReceived				(msg_scaled_imu2 p_Message);
	void	onScaledImu3MessageReceived				(msg_scaled_imu3 p_Message);
	void	onScaledPressureMessageReceived			(msg_scaled_pressure p_Message);
	void	onScaledPressure2MessageReceived		(msg_scaled_pressure2 p_Message);
	void	onScaledPressure3MessageReceived		(msg_scaled_pressure3 p_Message);
	void	onHeartbeatMessageReceived				(msg_heartbeat p_Message);
	void	onSysStatusMessageReceived				(msg_sys_status p_Message);
	void	onPowerStatusMessageReceived			(msg_power_status p_Message);
	void	onMissionCurrentMessageReceived			(msg_mission_current p_Message);
	void 	onGpsRawIntMessageReceived				(msg_gps_raw_int p_Message);
	void	onGlobalPositionMessageReceived			(msg_global_position_int p_Message);
	void 	onLocalPositionMessageReceived			(msg_local_position_ned p_Message);
	void 	onRcChannelRawMessageReceived			(msg_rc_channels_raw p_Message);
	void 	onRcChannelsMessageReceived				(msg_rc_channels p_Message);
	void	onRcChannelsScaledMessageReceived		(msg_rc_channels_scaled p_Message);
	void	onRcChannelsOverrideMessageReceived		(msg_rc_channels_override p_Message);
	void	onVfrHudMessageReceived					(msg_vfr_hud p_Message);
	void	onSystemTimeMessageReceived				(msg_system_time p_Message);
	void	onVibrationMessageReceived				(msg_vibration p_Message);
	void	onAttitudeMessageReceived				(msg_attitude p_Message);
	void	onTerrainReportMessageReceived			(msg_terrain_report p_Message);
	void	onTerrainRequestMessageReceived			(msg_terrain_request p_Message);
	void	onStatusTextMessageReceived				(msg_statustext p_Message);
	void	onHomePositionMessageReceived			(msg_home_position p_Message);
	void	onNavControllerOutputMessageReceived	(msg_nav_controller_output p_Message);
	void	onPositionTargetGlobalMessageReceived	(msg_position_target_global_int p_Message);
	void	onParamValueMessageReceived				(msg_param_value p_Message);
	void	onCommandAckMessageReceived				(msg_command_ack p_Message);
	void	onMissionRequestMessageReceived			(msg_mission_request p_Message);
	void	onMissionAckMessageReceived				(msg_mission_ack p_Message);
	
}
