package geco.io;

import com.MAVLink.Messages.MAVLinkMessage;

public class MavlinkMessageEmitter extends DataEmitter
{
	public void sendMessage(MAVLinkMessage p_Message) throws Exception 
		{
			this.sendData(p_Message.pack().encodePacket());
		}
}
