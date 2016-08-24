package geco.io;

import com.MAVLink.Messages.MAVLinkMessage;

public interface IMavlinkMessageReceiver 
{
	void onMessageReceived(MAVLinkMessage p_Message);
}
