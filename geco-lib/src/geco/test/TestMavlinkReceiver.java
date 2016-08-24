package geco.test;

import com.MAVLink.Messages.MAVLinkMessage;

import geco.io.IDataConnector;
import geco.io.MavlinkMessageReceiver;

public class TestMavlinkReceiver extends MavlinkMessageReceiver
{

	@Override
	public void onMessageReceived(MAVLinkMessage p_Message) 
	{
		System.out.println(String.valueOf(p_Message.sysid) + " : " + p_Message);
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
