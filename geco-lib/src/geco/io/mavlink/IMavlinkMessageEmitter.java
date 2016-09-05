package geco.io.mavlink;

import com.MAVLink.Messages.MAVLinkMessage;

public interface IMavlinkMessageEmitter 
{
	public void sendMessage(MAVLinkMessage p_Message) throws Exception;
}
