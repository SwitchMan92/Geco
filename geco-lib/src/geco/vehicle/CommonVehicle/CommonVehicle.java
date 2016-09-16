package geco.vehicle.CommonVehicle;

import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.ardupilotmega.msg_data64;
import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_command_int;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_global_position_int;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_home_position;
import com.MAVLink.common.msg_local_position_ned;
import com.MAVLink.common.msg_mission_current;
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
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_PARAM_TYPE;

import geco.io.IDataConnector;
import geco.io.TCPConnectorFactory;
import geco.io.UDPConnectorFactory;
import geco.io.mavlink.IMavlinkMessageEmitter;
import geco.io.mavlink.MavlinkMessageEmitter;
import geco.io.mavlink.MavlinkMessageReceiver;

public abstract class CommonVehicle extends MavlinkMessageReceiver implements ICommonVehicle, IMavlinkMessageEmitter
{
	
	private MavlinkMessageEmitter				m_Emitter;
	
	private IDataConnector 						m_Connector;
	private ArrayList<ICommonVehicleListener>	m_Listeners;
	
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
	protected abstract void onAutopilotChanged			(short p_Autopilot);
	protected abstract void onMavlinkVersionChanged		(short p_MavlinkVersion);
	protected abstract void onBaseModeChanged			(int p_BaseMode);
	
	protected abstract void onStatusTextReceived		(String p_Text);
	
	
	
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
	
	
	public void 			addListener					(ICommonVehicleListener p_Listener)
	{
		synchronized(this.m_Listeners)
			{
				if (!this.m_Listeners.contains(p_Listener))
					{
						this.m_Listeners.add(p_Listener);
					}
			}
	}
	
	public void 			removeListener				(ICommonVehicleListener p_Listener)
	{
		synchronized(this.m_Listeners)
			{
				if (this.m_Listeners.contains(p_Listener))
					{
						this.m_Listeners.remove(p_Listener);
					}
			}
	}
	
	public void 			connect						(String p_ConnectionMode, String p_Address, int p_Port) throws Exception
	{
		if (this.m_Connector == null) 
			{
				switch(p_ConnectionMode.toUpperCase())
					{
						case "TCP":
							this.m_Connector = TCPConnectorFactory.getInstance().createConnector();
							this.m_Connector.addReceiver(this);
							this.m_Connector.connect(p_Address, p_Port);
							this.m_Emitter.addConnector(this.m_Connector);
							break;
						case "UDP":
							this.m_Connector = UDPConnectorFactory.getInstance().createConnector();
							this.m_Connector.addReceiver(this);
							this.m_Connector.connect(p_Address, p_Port);
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
	
	public void 			onMessageReceived			(MAVLinkMessage p_Message)
	{
		if (p_Message instanceof msg_heartbeat)
			{
				msg_heartbeat l_Message = (msg_heartbeat)p_Message;
				
				this.setAutopilotAttr(l_Message.autopilot);
				this.setBaseModeAttr(l_Message.base_mode);
				this.setMavlinkVersionAttr(l_Message.mavlink_version);
				this.setMavStateAttr(l_Message.system_status);
				
				msg_heartbeat l_Hb = new msg_heartbeat();
				l_Hb.sysid=255;
				l_Hb.compid=0;
				
				try 
					{
						this.sendMessage(l_Hb);
					} 
				catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		else if (p_Message instanceof msg_raw_imu)
			{
				msg_raw_imu l_Message = (msg_raw_imu)p_Message;
				
				this.setAccelerationAttr(new Vector3D(l_Message.xacc, l_Message.yacc, l_Message.zacc));
				this.setAngularSpeedAttr(new Vector3D(l_Message.xgyro, l_Message.ygyro, l_Message.zgyro));
				this.setMagneticFieldAttr(new Vector3D(l_Message.xmag, l_Message.ymag, l_Message.zmag));
			}
		else if (p_Message instanceof msg_scaled_imu2)
			{
				//msg_scaled_imu2 l_Message = (msg_scaled_imu2)p_Message;
			}
		else if (p_Message instanceof msg_scaled_pressure)
			{
				//msg_scaled_pressure l_Message = (msg_scaled_pressure)p_Message;
			}
		else if (p_Message instanceof msg_sys_status)
			{
				//msg_sys_status l_Message = (msg_sys_status)p_Message;
			}		
		else if (p_Message instanceof msg_power_status)
			{
				//msg_power_status l_Message = (msg_power_status)p_Message;
			}
		else if (p_Message instanceof msg_mission_current)
			{
				//msg_mission_current l_Message = (msg_mission_current)p_Message;
			}
		else if (p_Message instanceof msg_gps_raw_int)
			{
				//msg_gps_raw_int l_Message = (msg_gps_raw_int)p_Message;
			}
		else if (p_Message instanceof msg_global_position_int)
			{
				//msg_global_position_int l_Message = (msg_global_position_int)p_Message;
			}
		else if (p_Message instanceof msg_local_position_ned)
			{
				//msg_local_position_ned l_Message = (msg_local_position_ned)p_Message;
			}
		else if (p_Message instanceof msg_rc_channels_raw)
			{
				//msg_rc_channels_raw l_Message = (msg_rc_channels_raw)p_Message;
			}
		else if (p_Message instanceof msg_rc_channels)
			{
				//msg_rc_channels l_Message = (msg_rc_channels)p_Message;
			}
		else if (p_Message instanceof msg_vfr_hud)
			{
				//msg_vfr_hud l_Message = (msg_vfr_hud)p_Message;
			}
		else if (p_Message instanceof msg_system_time)
			{
				//msg_system_time l_Message = (msg_system_time)p_Message;
			}
		else if (p_Message instanceof msg_vibration)
			{
				//msg_vibration l_Message = (msg_vibration)p_Message;
			}
		else if (p_Message instanceof msg_attitude)
			{
				msg_attitude l_Message = (msg_attitude)p_Message;
				
				this.setYawAttr(l_Message.yaw);
				this.setPitchAttr(l_Message.pitch);
				this.setRollAttr(l_Message.roll);
				
				this.setPitchSpeedAttr(l_Message.pitchspeed);
				this.setRollSpeedAttr(l_Message.rollspeed);
				this.setYawSpeedAttr(l_Message.yawspeed);
			}
		else if (p_Message instanceof msg_terrain_report)
			{
				msg_terrain_report l_Message = (msg_terrain_report)p_Message;
				
				this.setHeightAttr(l_Message.current_height);
				this.setLatitudeAttr(l_Message.lat);
				this.setLongitudeAttr(l_Message.lon);
			}
		else if (p_Message instanceof msg_terrain_request)
			{
				//msg_terrain_request l_Message = (msg_terrain_request)p_Message;
			}
		else if (p_Message instanceof msg_statustext)
			{
				msg_statustext l_Message = (msg_statustext)p_Message;
				this.onStatusTextReceived(l_Message.getText());
			}
		else if (p_Message instanceof msg_home_position)
			{
			
			}
		else if (p_Message instanceof msg_nav_controller_output)
			{
			
			}
		else if (p_Message instanceof msg_position_target_global_int)
			{
				
			}
		else if (p_Message instanceof msg_param_value)
			{
				msg_param_value l_Message = (msg_param_value)p_Message;
				
				System.err.println("parameter : " + new String(l_Message.param_id) + " - " + String.valueOf(l_Message.param_value));
				System.err.flush();
			}
		else if (p_Message instanceof msg_command_ack)
			{
				msg_command_ack l_Message = (msg_command_ack)p_Message;
				
				System.err.println("command acknowledged : " + String.valueOf(l_Message.command) + " with status " + String.valueOf(l_Message.result));
				System.err.flush();
			}
		else
			{
				//System.out.println(p_Message.getClass());
				//System.out.flush();
			}
	}
	
	@Override
	public void 			sendMessage					(MAVLinkMessage p_Message) throws Exception {
		this.m_Emitter.sendMessage(p_Message);
	}


	public 					CommonVehicle				()
	{	
		this.m_Autopilot 				= 	0;
		this.m_MavlinkVersion 			= 	0;
		this.m_BaseMode					=	0;
		this.m_MavState					= 	0;
		this.m_Acceleration				= 	new Vector3D(0d, 0d, 0d);
		this.m_AngularSpeed				= 	new Vector3D(0d, 0d, 0d);
		this.m_MagneticField			= 	new Vector3D(0d, 0d, 0d);
		this.m_Yaw						=	0d;
		this.m_Pitch					=	0d;
		this.m_Roll						=	0d;
		this.m_YawSpeed					=	0d;
		this.m_PitchSpeed				=	0d;
		this.m_RollSpeed				=	0d;
		this.m_Longitude				=	0;
		this.m_Latitude					=	0;
		this.m_Height					=	0d;
		this.m_Listeners				=	new ArrayList<ICommonVehicleListener>();
		this.m_Emitter					=	new MavlinkMessageEmitter();
	}
	
}




