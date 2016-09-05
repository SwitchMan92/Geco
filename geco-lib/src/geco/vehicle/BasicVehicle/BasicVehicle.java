package geco.vehicle.BasicVehicle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_global_position_int;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_local_position_ned;
import com.MAVLink.common.msg_mission_current;
import com.MAVLink.common.msg_power_status;
import com.MAVLink.common.msg_raw_imu;
import com.MAVLink.common.msg_rc_channels;
import com.MAVLink.common.msg_rc_channels_raw;
import com.MAVLink.common.msg_scaled_imu2;
import com.MAVLink.common.msg_scaled_pressure;
import com.MAVLink.common.msg_sys_status;
import com.MAVLink.common.msg_system_time;
import com.MAVLink.common.msg_terrain_report;
import com.MAVLink.common.msg_terrain_request;
import com.MAVLink.common.msg_vfr_hud;
import com.MAVLink.common.msg_vibration;

import geco.io.IDataConnector;
import geco.io.TCPConnectorFactory;
import geco.io.UDPConnectorFactory;
import geco.io.mavlink.MavlinkMessageReceiver;

public abstract class BasicVehicle extends MavlinkMessageReceiver implements IBasicVehicle
{
	private IDataConnector 	m_Connector;
	
	private Short 			m_Autopilot;
	private Short 			m_MavlinkVersion;
	
	private Integer 		m_MavState;
	private Integer			m_BaseMode;
	
	private Vector3D		m_Acceleration;
	private Vector3D		m_AngularSpeed;
	private Vector3D		m_MagneticField;
	
	private AttitudeData m_Attitude;
	
	
	public Short 			getAutopilot			() 					{ synchronized(this.m_Autopilot)			{ return this.m_Autopilot; 			}	}
	public Short 			getMavlinkVersion		() 					{ synchronized(this.m_MavlinkVersion)		{ return this.m_MavlinkVersion; 	}	}
	public Integer			getMavState				()  				{ synchronized(this.m_MavState)				{ return this.m_MavState; 			}	}
	public Integer 			getBaseMode				() 					{ synchronized(this.m_BaseMode)				{ return this.m_BaseMode; 			}	}
	public Vector3D			getAcceleration			()					{ synchronized(this.m_Acceleration)			{ return this.m_Acceleration; 		}	}
	public Vector3D			getAngularSpeed			()					{ synchronized(this.m_AngularSpeed)			{ return this.m_AngularSpeed; 		}	}
	public Vector3D			getMagneticField		()					{ synchronized(this.m_MagneticField)		{ return this.m_MagneticField; 		}	}
	public AttitudeData		getAttitude				()					{ synchronized(this.m_Attitude)				{ return this.m_Attitude; 			}	}
	
	
	private void 			setAutopilot			(short p_Value)		{ synchronized(this.m_Autopilot)			{ this.m_Autopilot = new Short(p_Value); 		}	}
	private void 			setMavlinkVersion		(short p_Value)		{ synchronized(this.m_MavlinkVersion)		{ this.m_MavlinkVersion = new Short(p_Value); 	} 	}
	private void 			setMavState				(int p_Value)		{ synchronized(this.m_MavState)				{ this.m_MavState = new Integer(p_Value); 		}	}
	private void 			setBaseMode				(int p_Value)		{ synchronized(this.m_BaseMode)				{ this.m_BaseMode = new Integer(p_Value); 		}	}
	private void			setAcceleration			(Vector3D p_Value)	{ synchronized(this.m_Acceleration)			{ this.m_Acceleration = p_Value; 				}	}
	private void			setAngularSpeed			(Vector3D p_Value)	{ synchronized(this.m_AngularSpeed)			{ this.m_AngularSpeed = p_Value; 				}	}
	private void			setMagneticField		(Vector3D p_Value)	{ synchronized(this.m_MagneticField)		{ this.m_MagneticField = p_Value; 				}	}
	

	
	public BasicVehicle()
	{
		this.m_Autopilot 				= 	new Short((short) 0);
		this.m_MavlinkVersion 			= 	new Short((short) 0);
		this.m_BaseMode					=	new Integer(0);
		this.m_MavState					= 	new Integer(0);
		this.m_Acceleration				= 	new Vector3D(0d, 0d, 0d);
		this.m_AngularSpeed				= 	new Vector3D(0d, 0d, 0d);
		this.m_MagneticField			= 	new Vector3D(0d, 0d, 0d);
		this.m_Attitude					=	new AttitudeData();
	}
	
	
	public void connect(String p_ConnectionMode, String p_Address, int p_Port) throws Exception
	{
		if (this.m_Connector == null) 
			{
				switch(p_ConnectionMode.toUpperCase())
					{
						case "TCP":
							this.m_Connector = TCPConnectorFactory.getInstance().createConnector();
							this.m_Connector.addReceiver(this);
							this.m_Connector.connect(p_Address, p_Port);
							break;
						case "UDP":
							this.m_Connector = UDPConnectorFactory.getInstance().createConnector();
							this.m_Connector.addReceiver(this);
							this.m_Connector.connect(p_Address, p_Port);
							break;
					}
			}
	}
	
	
	public void disconnect() throws Exception
	{
		if (this.m_Connector != null)
			{
				this.m_Connector.disconnect();
				this.m_Connector = null;
			}
	}
	
	
	public void onMessageReceived(MAVLinkMessage p_Message)
	{
		if (p_Message instanceof msg_heartbeat)
			{
				msg_heartbeat l_Message = (msg_heartbeat)p_Message;
				
				this.setAutopilot(l_Message.autopilot);
				this.setBaseMode(l_Message.base_mode);
				this.setMavlinkVersion(l_Message.mavlink_version);
				this.setMavState(l_Message.system_status);
			}
		else if (p_Message instanceof msg_raw_imu)
			{
				msg_raw_imu l_Message = (msg_raw_imu)p_Message;
				
				this.setAcceleration(new Vector3D(l_Message.xacc, l_Message.yacc, l_Message.zacc));
				this.setAngularSpeed(new Vector3D(l_Message.xgyro, l_Message.ygyro, l_Message.zgyro));
				this.setMagneticField(new Vector3D(l_Message.xmag, l_Message.ymag, l_Message.zmag));
			}
		else if (p_Message instanceof msg_scaled_imu2)
			{
				msg_scaled_imu2 l_Message = (msg_scaled_imu2)p_Message;
				
			}
		else if (p_Message instanceof msg_scaled_pressure)
			{
				msg_scaled_pressure l_Message = (msg_scaled_pressure)p_Message;
				
			}
		else if (p_Message instanceof msg_sys_status)
			{
				msg_sys_status l_Message = (msg_sys_status)p_Message;
				
			}		
		else if (p_Message instanceof msg_power_status)
			{
				msg_power_status l_Message = (msg_power_status)p_Message;
				
			}
		else if (p_Message instanceof msg_mission_current)
			{
				msg_mission_current l_Message = (msg_mission_current)p_Message;
				
			}
		else if (p_Message instanceof msg_gps_raw_int)
			{
				msg_gps_raw_int l_Message = (msg_gps_raw_int)p_Message;
				
			}
		else if (p_Message instanceof msg_global_position_int)
			{
				msg_global_position_int l_Message = (msg_global_position_int)p_Message;
				
			}
		else if (p_Message instanceof msg_local_position_ned)
			{
				msg_local_position_ned l_Message = (msg_local_position_ned)p_Message;
				
			}
		else if (p_Message instanceof msg_rc_channels_raw)
			{
				msg_rc_channels_raw l_Message = (msg_rc_channels_raw)p_Message;
				
			}
		else if (p_Message instanceof msg_rc_channels)
			{
				msg_rc_channels l_Message = (msg_rc_channels)p_Message;
				
			}
		else if (p_Message instanceof msg_vfr_hud)
			{
				msg_vfr_hud l_Message = (msg_vfr_hud)p_Message;
				
			}
		else if (p_Message instanceof msg_system_time)
			{
				msg_system_time l_Message = (msg_system_time)p_Message;
				
			}
		else if (p_Message instanceof msg_vibration)
			{
				msg_vibration l_Message = (msg_vibration)p_Message;
			}
		else if (p_Message instanceof msg_attitude)
			{
				msg_attitude l_Message = (msg_attitude)p_Message;
				
				synchronized(this.m_Attitude)
					{
						this.m_Attitude.setPitch(l_Message.pitch);
						this.m_Attitude.setRoll(l_Message.roll);
						this.m_Attitude.setYaw(l_Message.yaw);
						
						this.m_Attitude.setPitchSpeed(l_Message.pitchspeed);
						this.m_Attitude.setRollSpeed(l_Message.rollspeed);
						this.m_Attitude.setYawSpeed(l_Message.yawspeed);
					}
				
			}
		else if (p_Message instanceof msg_terrain_report)
			{
				msg_terrain_report l_Message = (msg_terrain_report)p_Message;
				
			}
		else if (p_Message instanceof msg_terrain_request)
			{
				msg_terrain_request l_Message = (msg_terrain_request)p_Message;
			}
		else
			{
			
				System.out.println(p_Message.getClass());
			}
	}
	
	
}
