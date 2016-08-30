package geco.test;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_debug;
import com.MAVLink.common.msg_global_position_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_radio_status;
import com.MAVLink.common.msg_statustext;
import com.MAVLink.common.msg_terrain_data;

import geco.io.IDataConnector;
import geco.io.MavlinkMessageReceiver;

public class TestMavlinkReceiver extends MavlinkMessageReceiver
{
	private boolean m_DroneConnected = false;
	
	public void onConnected()
		{
			this.m_DroneConnected = true;
			System.out.println("drone connected");
		}
	
	@Override
	public void onMessageReceived(MAVLinkMessage p_Message) 
	{
		if (p_Message.getClass() == msg_heartbeat.class)
			{
				System.out.println(String.valueOf(p_Message.sysid) + " : " + p_Message);
				
				if (!this.m_DroneConnected)
					this.onConnected();
			}
	}

	@Override
	public void onConnected(IDataConnector p_Connector) 
	{
		System.out.println("connected");
	}

	@Override
	public void onDisconnected(IDataConnector p_Connector) 
	{
		System.out.println("disconnected");
	}

	@Override
	public void onConnectionLost(IDataConnector p_Connector) 
	{
		System.err.println("connection lost");
	}

	@Override
	public void onReconnected(IDataConnector p_Connector) 
	{
		System.out.println("reconnected");
	}

}
