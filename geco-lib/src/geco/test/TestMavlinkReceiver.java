package geco.test;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_heartbeat;

import geco.io.IDataConnector;
import geco.io.mavlink.MavlinkMessageReceiver;

public class TestMavlinkReceiver extends MavlinkMessageReceiver
{
	private boolean m_DroneConnected = false;
	
	public void onConnected()
		{
			this.m_DroneConnected = true;
			System.out.println("drone connected");
			System.out.flush();
		}
	
	@Override
	public void onMessageReceived(MAVLinkMessage p_Message)
	{
		if (p_Message.getClass() == msg_heartbeat.class)
			{
				System.out.println(String.valueOf(p_Message.sysid) + " : " + p_Message + "(" + String.valueOf(p_Message.msgid) + ")");
				
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
