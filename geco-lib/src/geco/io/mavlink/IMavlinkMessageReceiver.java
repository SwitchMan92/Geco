package geco.io.mavlink;

import com.MAVLink.Messages.MAVLinkMessage;

public interface IMavlinkMessageReceiver 
{
	void onMessageReceived(MAVLinkMessage p_Message);
}
