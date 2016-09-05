package geco.io.mavlink;

import com.MAVLink.Messages.MAVLinkMessage;

import geco.io.DataEmitter;

public class MavlinkMessageEmitter extends DataEmitter implements IMavlinkMessageEmitter
{
	public void sendMessage(MAVLinkMessage p_Message) throws Exception 
		{
			this.sendData(p_Message.pack().encodePacket());
		}
}
