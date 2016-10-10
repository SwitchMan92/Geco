package geco.test;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_param_request_list;
import com.MAVLink.common.msg_param_set;
import com.MAVLink.common.msg_set_mode;
import com.MAVLink.enums.MAV_CMD;

import geco.io.IDataConnector;
import geco.vehicle.CommonVehicle.CommonVehicle;

public class CustomVehicle extends CommonVehicle
{
	
	public void setMode(int p_Mode)
	{
		msg_set_mode l_Message = new msg_set_mode();
		
		l_Message.target_system = 0;
		
		l_Message.base_mode = 255;
		l_Message.custom_mode = (short)p_Mode; 
		
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
	
	public void setParameters()
	{
		
		this.setParameter("ARMING_CHECK", 0);
		
		try
			{
				
				BufferedReader l_ParamReader = new BufferedReader(new FileReader("/home/tlangfeldt/mavlink/ardupilot/Tools/autotest/default_params/copter.parm"));
				
				String l_CurrentLine = "";
				
				while ((l_CurrentLine = l_ParamReader.readLine()) != null) 
					{
						System.out.println(l_CurrentLine);
						
						String[] l_Params = l_CurrentLine.replaceAll("\t", " ").split(" ");
						
						if (!l_CurrentLine.startsWith("#"))
							{
								this.setParameter(l_Params[0], Float.parseFloat(l_Params[l_Params.length-1]));
							}
						
					}
			}
		catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		
	}
	
	public void				sendLong					(short command, float...params)
	{
		msg_command_long l_Message = new msg_command_long();
		
		l_Message.target_system = 0;
		l_Message.target_component = 0;
		
		l_Message.command = command;
		
		l_Message.param1 = params[0];
		l_Message.param2 = params[1];
		l_Message.param3 = params[2];
		l_Message.param4 = params[3];
		l_Message.param5 = params[4];
		l_Message.param6 = params[5];
		l_Message.param7 = params[6];
	}
	
	public void arm_throttle()
	{
		
		
		msg_command_long l_Command = new msg_command_long();
		
		l_Command.target_system 	= 0;
		l_Command.target_component 	= 0;
		
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void takeOff(float p_Height)
	{
		msg_command_long l_Command = new msg_command_long();
		
		l_Command.target_system 	= 0;
		l_Command.target_component 	= 0;
		
		l_Command.command = MAV_CMD.MAV_CMD_NAV_TAKEOFF;
		
		l_Command.param1 = 0f;
		l_Command.param2 = 0f;
		l_Command.param3 = 1f;
		l_Command.param4 = 0f;
		l_Command.param5 = 0f;
		l_Command.param6 = 0f;
		l_Command.param7 = 20f;
		
		try 
			{
				this.sendMessage(l_Command);
			} 
		catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void changeSpeed(float p_Speed)
	{
		msg_command_long l_Command = new msg_command_long();
		
		l_Command.target_component = 0;
		l_Command.target_system = 0;
		
		l_Command.command = MAV_CMD.MAV_CMD_DO_CHANGE_SPEED;
		
		l_Command.param1 = 1f;
		l_Command.param2 = p_Speed;
		l_Command.param3 = 1f;
		l_Command.param4 = -1f;
		l_Command.param5 = 1f;
		l_Command.param6 = 0f;
		l_Command.param7 = 0f;
		
		try 
			{
				this.sendMessage(l_Command);
			} 
		catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void onConnected(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		System.out.println("connected");
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

	@Override
	protected void onYawChanged(double p_Yaw) {
		// TODO Auto-generated method stub
		//System.err.println("yaw : " + String.valueOf(p_Yaw));
		//System.err.flush();
	}

	@Override
	protected void onPitchChanged(double p_Pitch) {
		// TODO Auto-generated method stub
		//System.err.println("pitch : " + String.valueOf(p_Pitch));
		System.err.flush();
	}

	@Override
	protected void onRollChanged(double p_Roll) {
		// TODO Auto-generated method stub
		//System.err.println("roll : " + String.valueOf(p_Roll));
		System.err.flush();
	}

	@Override
	protected void onYawSpeedChanged(double p_YawSpeed) {
		// TODO Auto-generated method stub
		//System.err.println("yaw speed : " + String.valueOf(p_YawSpeed));
		System.err.flush();
	}

	@Override
	protected void onPitchSpeedChanged(double p_PitchSpeed) {
		// TODO Auto-generated method stub
		//System.err.println("pitch speed : " + String.valueOf(p_PitchSpeed));
		System.err.flush();
	}

	@Override
	protected void onRollSpeedChanged(double p_RollSpeed) {
		// TODO Auto-generated method stub
		//System.err.println("roll speed : " + String.valueOf(p_RollSpeed));
		System.err.flush();
	}

	@Override
	protected void onLongitudeChanged(int p_Longitude) {
		// TODO Auto-generated method stub
		//System.err.println("longitude : " + String.valueOf(p_Longitude));
		System.err.flush();
	}

	@Override
	protected void onLatitudeChanged(int p_Latitude) {
		// TODO Auto-generated method stub
		//System.err.println("latitude : " + String.valueOf(p_Latitude));
		System.err.flush();
	}

	@Override
	protected void onHeightChanged(double p_Height) {
		// TODO Auto-generated method stub
		System.err.println("height : " + String.valueOf(p_Height));
		System.err.flush();
	}

	@Override
	protected void onMagneticFieldChanged(Vector3D p_MagField) {
		// TODO Auto-generated method stub
		//System.err.println("magnetic field : " + p_MagField.toString());
		System.err.flush();
	}

	@Override
	protected void onAccelerationChanged(Vector3D p_Acceleration) {
		// TODO Auto-generated method stub
		System.err.println("acceleration : " + p_Acceleration.toString());
		System.err.flush();
	}

	@Override
	protected void onAngularSpeedChanged(Vector3D p_AngularSpeed) {
		// TODO Auto-generated method stub
		//System.err.println("angular speed : " + p_AngularSpeed.toString());
		//System.err.flush();
	}

	@Override
	protected void onMavStateChanged(int p_MavState) {
		// TODO Auto-generated method stub
		System.err.println("mav state : " + String.valueOf(p_MavState));
		System.err.flush();
	}

	@Override
	protected void onAutopilotChanged(short p_Autopilot) {
		// TODO Auto-generated method stub
		System.err.println("autopilot : " + String.valueOf(p_Autopilot));
		System.err.flush();
	}

	@Override
	protected void onMavlinkVersionChanged(short p_MavlinkVersion) {
		// TODO Auto-generated method stub
		System.err.println("mavlink version : " + String.valueOf(p_MavlinkVersion));
		System.err.flush();
	}

	@Override
	protected void onBaseModeChanged(int p_BaseMode) {
		System.err.println("base mode : " + String.valueOf(p_BaseMode));
		System.err.flush();
	}

	@Override
	protected void onStatusTextReceived(String p_Text) {
		System.err.println(p_Text);
		System.err.flush();
	}

	@Override
	public void arm(int p_Id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disarm(int p_Id) {
		// TODO Auto-generated method stub
		
	}
}
