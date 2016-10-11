package geco.vehicle.CommonVehicle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

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

import geco.io.IDataConnector;
import geco.io.TCPConnector;
import geco.io.TCPConnectorFactory;
import geco.io.UDPConnector;
import geco.io.UDPConnectorFactory;
import geco.io.mavlink.IMavlinkMessageEmitter;
import geco.io.mavlink.MavlinkMessageEmitter;
import geco.monitoring.mavlink.dispatcher.CommonMavlinkMessageDispatcher;
import geco.monitoring.mavlink.listener.CommonMavlinkMessageListener;

public abstract class CommonVehicle extends CommonMavlinkMessageListener implements ICommonVehicle, IMavlinkMessageEmitter
{
	
	private MavlinkMessageEmitter				m_Emitter;
	private CommonMavlinkMessageDispatcher		m_MessageParser;
	
	private IDataConnector 						m_Connector;
	
	private Short								m_MavType;
	private Short 								m_Autopilot;
	private Short 								m_MavlinkVersion;
	
	private Integer 							m_MavState;
	private Integer								m_BaseMode;
	
	private Vector3D							m_Acceleration;
	private Vector3D							m_AngularSpeed;
	private Vector3D							m_MagneticField;
	
	private Double								m_Yaw;
	private Double								m_Pitch;
	private Double								m_Roll;

	private Double								m_YawSpeed;
	private Double								m_PitchSpeed;
	private Double								m_RollSpeed;

	private Integer								m_Longitude;
	private Integer								m_Latitude;
	private Double								m_Height;
	
	
	
	protected abstract void	onYawChanged				(double p_Yaw);
	protected abstract void	onPitchChanged				(double p_Pitch);
	protected abstract void	onRollChanged				(double p_Roll);
	
	protected abstract void	onYawSpeedChanged			(double p_YawSpeed);
	protected abstract void	onPitchSpeedChanged			(double p_PitchSpeed);
	protected abstract void	onRollSpeedChanged			(double p_RollSpeed);
	
	protected abstract void onLongitudeChanged			(int p_Longitude);
	protected abstract void onLatitudeChanged			(int p_Latitude);
	protected abstract void onHeightChanged				(double p_Height);
	
	protected abstract void onMagneticFieldChanged		(Vector3D p_MagField);
	protected abstract void onAccelerationChanged		(Vector3D p_Acceleration);
	protected abstract void onAngularSpeedChanged		(Vector3D p_AngularSpeed);
	
	protected abstract void onMavStateChanged			(int p_MavState);
	protected abstract void onMavTypeChanged			(short p_Mavtype);
	protected abstract void onAutopilotChanged			(short p_Autopilot);
	protected abstract void onMavlinkVersionChanged		(short p_MavlinkVersion);
	protected abstract void onBaseModeChanged			(int p_BaseMode);
	
	protected abstract void onStatusTextReceived		(String p_Text);
	
	
	public Short 			getMavtype					() 						{ synchronized(this.m_MavType)				{ return this.m_MavType; 			}	}
	public Short 			getAutopilot				() 						{ synchronized(this.m_Autopilot)			{ return this.m_Autopilot; 			}	}
	public Short 			getMavlinkVersion			() 						{ synchronized(this.m_MavlinkVersion)		{ return this.m_MavlinkVersion; 	}	}
	public Integer			getMavState					()  					{ synchronized(this.m_MavState)				{ return this.m_MavState; 			}	}
	public Integer 			getBaseMode					() 						{ synchronized(this.m_BaseMode)				{ return this.m_BaseMode; 			}	}
	public Vector3D			getAcceleration				()						{ synchronized(this.m_Acceleration)			{ return this.m_Acceleration; 		}	}
	public Vector3D			getAngularSpeed				()						{ synchronized(this.m_AngularSpeed)			{ return this.m_AngularSpeed; 		}	}
	public Vector3D			getMagneticField			()						{ synchronized(this.m_MagneticField)		{ return this.m_MagneticField; 		}	}
	
	public Double 			getYaw						()						{ synchronized(this.m_Yaw)					{ return this.m_Yaw; 				}	};
	public Double 			getPitch					()						{ synchronized(this.m_Pitch)				{ return this.m_Pitch; 				}	};
	public Double 			getRoll						()						{ synchronized(this.m_Roll)					{ return this.m_Roll; 				}	};
	
	public Double 			getYawSpeed					()						{ synchronized(this.m_YawSpeed)				{ return this.m_YawSpeed; 			}	};
	public Double 			getPitchSpeed				()						{ synchronized(this.m_PitchSpeed)			{ return this.m_PitchSpeed; 		}	};
	public Double 			getRollSpeed				()						{ synchronized(this.m_RollSpeed)			{ return this.m_RollSpeed; 			}	};
	
	public Integer 			getLatitude					()						{ synchronized(this.m_Latitude)				{ return this.m_Latitude; 			}	};
	public Integer 			getLongitude				()						{ synchronized(this.m_Longitude)			{ return this.m_Longitude; 			}	};
	
	public Double 			getHeight					()						{ synchronized(this.m_Height)				{ return this.m_Height; 			}	};
	
	private void 			setMavType					(short p_Value)			{ synchronized(this.m_MavType)				{ if (this.m_MavType != p_Value) this.onMavTypeChanged(p_Value); this.m_MavType = new Short(p_Value);						}	}
	private void 			setAutopilotAttr			(short p_Value)			{ synchronized(this.m_Autopilot)			{ if (this.m_Autopilot != p_Value) this.onAutopilotChanged(p_Value); this.m_Autopilot = new Short(p_Value);					}	}
	private void 			setMavlinkVersionAttr		(short p_Value)			{ synchronized(this.m_MavlinkVersion)		{ if (this.m_MavlinkVersion != p_Value) this.onMavlinkVersionChanged(p_Value); this.m_MavlinkVersion = new Short(p_Value); 	} 	}
	private void 			setMavStateAttr				(int p_Value)			{ synchronized(this.m_MavState)				{ if (this.m_MavState != p_Value) this.onMavStateChanged(p_Value); this.m_MavState = new Integer(p_Value); 					}	}
	private void 			setBaseModeAttr				(int p_Value)			{ synchronized(this.m_BaseMode)				{ if (this.m_BaseMode != p_Value) this.onBaseModeChanged(p_Value); this.m_BaseMode = new Integer(p_Value); 					}	}
	private void			setAccelerationAttr			(Vector3D p_Value)		{ synchronized(this.m_Acceleration)			{ if (this.m_Acceleration.getX() != p_Value.getX() || this.m_Acceleration.getY() != p_Value.getY() || this.m_Acceleration.getZ() != p_Value.getZ()) this.onAccelerationChanged(p_Value); this.m_Acceleration = p_Value; 					}	}
	private void			setAngularSpeedAttr			(Vector3D p_Value)		{ synchronized(this.m_AngularSpeed)			{ if (this.m_AngularSpeed.getX() != p_Value.getX() || this.m_AngularSpeed.getY() != p_Value.getY() || this.m_AngularSpeed.getZ() != p_Value.getZ()) this.onAngularSpeedChanged(p_Value); this.m_AngularSpeed = p_Value; 					}	}
	private void			setMagneticFieldAttr		(Vector3D p_Value)		{ synchronized(this.m_MagneticField)		{ if (this.m_MagneticField.getX() != p_Value.getX() || this.m_MagneticField.getY() != p_Value.getY() || this.m_MagneticField.getZ() != p_Value.getZ()) this.onMagneticFieldChanged(p_Value); this.m_MagneticField = p_Value; 				}	}
	
	private void			setYawAttr					(double p_Yaw)			{ synchronized(this.m_Yaw)					{ if (this.m_Yaw != p_Yaw) this.onYawChanged(p_Yaw); this.m_Yaw = p_Yaw; 													}	}
	private void			setPitchAttr				(double p_Pitch)		{ synchronized(this.m_Pitch)				{ if (this.m_Pitch != p_Pitch) this.onPitchChanged(p_Pitch); this.m_Pitch = p_Pitch; 										}	}
	private void			setRollAttr					(double p_Roll)			{ synchronized(this.m_Roll)					{ if (this.m_Roll != p_Roll) this.onRollChanged(p_Roll); this.m_Roll = p_Roll; 												}	}
	
	private void			setYawSpeedAttr				(double p_YawSpeed)		{ synchronized(this.m_YawSpeed)				{ if (this.m_YawSpeed != p_YawSpeed) this.onYawSpeedChanged(p_YawSpeed); this.m_YawSpeed = p_YawSpeed; 						}	}
	private void			setPitchSpeedAttr			(double p_PitchSpeed)	{ synchronized(this.m_PitchSpeed)			{ if (this.m_PitchSpeed != p_PitchSpeed) this.onPitchSpeedChanged(p_PitchSpeed); this.m_PitchSpeed = p_PitchSpeed; 			}	}
	private void			setRollSpeedAttr			(double p_RollSpeed)	{ synchronized(this.m_RollSpeed)			{ if (this.m_RollSpeed != p_RollSpeed) this.onRollSpeedChanged(p_RollSpeed); this.m_RollSpeed = p_RollSpeed; 				}	}
	
	private void			setLatitudeAttr				(int p_Latitude)		{ synchronized(this.m_Latitude)				{ if (this.m_Latitude != p_Latitude) this.onLatitudeChanged(p_Latitude); this.m_Latitude = p_Latitude; 						}	}
	private void			setLongitudeAttr			(int p_Longitude)		{ synchronized(this.m_Longitude)			{ if (this.m_Longitude != p_Longitude) this.onLongitudeChanged(p_Longitude); this.m_Longitude = p_Longitude; 				}	}
	private void			setHeightAttr				(double p_Height)		{ synchronized(this.m_Height)				{ if (this.m_Height != p_Height) this.onHeightChanged(p_Height); this.m_Height = p_Height; 									}	}
	
	public 					CommonVehicle				()
	{	
		super(1, 0);
		
		this.m_MessageParser			=	new CommonMavlinkMessageDispatcher();
		this.m_Acceleration				= 	new Vector3D(0d, 0d, 0d);
		this.m_AngularSpeed				= 	new Vector3D(0d, 0d, 0d);
		this.m_MagneticField			= 	new Vector3D(0d, 0d, 0d);
		this.m_Emitter					=	new MavlinkMessageEmitter();
		
		this.m_Autopilot				=	0;
		this.m_MavlinkVersion			=	0;
		this.m_MavType					=	0;
		this.m_MavState					=	0;
		this.m_BaseMode					=	0;
		
		this.m_Yaw						=	0d;
		this.m_Pitch					=	0d;
		this.m_Roll						=	0d;
		
		this.m_YawSpeed					=	0d;
		this.m_PitchSpeed				=	0d;
		this.m_RollSpeed				=	0d;
		
		this.m_Latitude					=	0;
		this.m_Longitude				=	0;
		this.m_Height					=	0d;
	}
	
	public CommonVehicle(int p_SystemId, int p_ComponentId)
	{
		super(p_SystemId, p_ComponentId);
		
		this.m_MessageParser			=	new CommonMavlinkMessageDispatcher();
		this.m_Acceleration				= 	new Vector3D(0d, 0d, 0d);
		this.m_AngularSpeed				= 	new Vector3D(0d, 0d, 0d);
		this.m_MagneticField			= 	new Vector3D(0d, 0d, 0d);
		this.m_Emitter					=	new MavlinkMessageEmitter();
	}
	
	public void 			connect						(String p_ConnectionMode, String p_Address, int p_Port) throws Exception
	{
		if (this.m_Connector == null) 
			{
				switch(p_ConnectionMode.toUpperCase())
					{
						case "TCP":
							this.m_Connector = TCPConnectorFactory.getInstance().createConnector();
							this.m_MessageParser = new CommonMavlinkMessageDispatcher();
							this.m_MessageParser.addListener(this);
							this.m_Connector.addReceiver(this.m_MessageParser);
							((TCPConnector)this.m_Connector).connect(p_Address, p_Port);
							this.m_Emitter.addConnector(this.m_Connector);
							break;
						case "UDP":
							this.m_Connector = UDPConnectorFactory.getInstance().createConnector();
							this.m_MessageParser = new CommonMavlinkMessageDispatcher();
							this.m_MessageParser.addListener(this);
							this.m_Connector.addReceiver(this.m_MessageParser);
							((UDPConnector)this.m_Connector).connect(p_Address, p_Port);
							this.m_Emitter.addConnector(this.m_Connector);
							break;
					}
			}
	}
	
	public void 			disconnect					() throws Exception
	{
		if (this.m_Connector != null)
			{
				this.m_Emitter.removeConnector(this.m_Connector);
				this.m_Connector.disconnect();
				this.m_Connector = null;
			}
	}
	
	@Override
	public void 			sendMessage					(MAVLinkMessage p_Message) throws Exception 
	{
		this.m_Emitter.sendMessage(p_Message);
	}
	
	public void 	onRawImuMessageReceived					(msg_raw_imu p_Message)
	{
		
	}
	
	public void		onScaledImuMessageReceived				(msg_scaled_imu p_Message)
	{
		
	}
	
	public void		onScaledImu2MessageReceived				(msg_scaled_imu2 p_Message)
	{
		
	}
	
	public void		onScaledImu3MessageReceived				(msg_scaled_imu3 p_Message)
	{
		
	}
	
	public void		onScaledPressureMessageReceived			(msg_scaled_pressure p_Message)
	{
		
	}
	
	public void		onScaledPressure2MessageReceived		(msg_scaled_pressure2 p_Message)
	{
		
	}
	
	public void		onScaledPressure3MessageReceived		(msg_scaled_pressure3 p_Message)
	{
		
	}
	
	public void		onHeartbeatMessageReceived				(msg_heartbeat p_Message)
	{
		this.setAutopilotAttr(p_Message.autopilot);
		this.setBaseModeAttr(p_Message.base_mode);
		this.setMavlinkVersionAttr(p_Message.mavlink_version);
		this.setMavStateAttr(p_Message.system_status);
		this.setMavType(p_Message.type);
	}
	
	public void		onSysStatusMessageReceived				(msg_sys_status p_Message)
	{
		
	}
	
	public void		onPowerStatusMessageReceived			(msg_power_status p_Message)
	{
		
	}
	
	public void		onMissionCurrentMessageReceived			(msg_mission_current p_Message)
	{
		
	}
	
	public void 	onGpsRawIntMessageReceived				(msg_gps_raw_int p_Message)
	{
		
	}
	
	public void		onGlobalPositionMessageReceived			(msg_global_position_int p_Message)
	{
		
	}
	
	public void 	onLocalPositionMessageReceived			(msg_local_position_ned p_Message)
	{
		
	}
	
	public void 	onRcChannelRawMessageReceived			(msg_rc_channels_raw p_Message)
	{
		
	}
	
	public void 	onRcChannelsMessageReceived				(msg_rc_channels p_Message)
	{
		
	}
	
	public void		onRcChannelsScaledMessageReceived		(msg_rc_channels_scaled p_Message)
	{
		
	}
	
	public void		onRcChannelsOverrideMessageReceived		(msg_rc_channels_override p_Message)
	{
		
	}
	
	public void		onVfrHudMessageReceived					(msg_vfr_hud p_Message)
	{
		
	}
	
	public void		onsystemTimeMessageReceived				(msg_system_time p_Message)
	{
		
	}
	
	public void		onVibrationMessageReceived				(msg_vibration p_Message)
	{
		
	}
	
	public void		onAttitudeMessageReceived				(msg_attitude p_Message)
	{
		this.setYawAttr(p_Message.yaw);
		this.setPitchAttr(p_Message.pitch);
		this.setRollAttr(p_Message.roll);
		
		this.setYawSpeedAttr(p_Message.yawspeed);
		this.setPitchSpeedAttr(p_Message.pitchspeed);
		this.setRollSpeedAttr(p_Message.rollspeed);
	}
	
	public void		onTerrainReportMessageReceived			(msg_terrain_report p_Message)
	{
		
	}
	
	public void		onTerrainRequestMessageReceived			(msg_terrain_request p_Message)
	{
		
	}
	
	public void		onstatusTextMessageReceived				(msg_statustext p_Message)
	{
		
	}
	
	public void		onHomePositionMessageReceived			(msg_home_position p_Message)
	{
		
	}
	
	public void		onNavControllerOutputMessageReceived	(msg_nav_controller_output p_Message)
	{
		
	}
	
	public void		onPositionTargetGlobalMessageReceived	(msg_position_target_global_int p_Message)
	{
		
	}
	
	public void		onParamValueMessageReceived				(msg_param_value p_Message)
	{
		
	}
	
	public void		onCommandAckMessageReceived				(msg_command_ack p_Message)
	{
		System.err.println("command acknowledged : " + String.valueOf(p_Message.command));
	}
	
	public void		onMissionRequestMessageReceived			(msg_mission_request p_Message)
	{
		
	}

	
	
}




