package geco.io.mavlink;

import com.MAVLink.Messages.MAVLinkMessage;

import MAVLink.MAVLinkPacket;
import geco.io.DataEmitter;

public class MavlinkMessageEmitter extends DataEmitter implements IMavlinkMessageEmitter
{
	private short m_SequenceNumber;
	
	
	public MavlinkMessageEmitter()
	{
		this.m_SequenceNumber = 0;
	}
	public void sendMessage(MAVLinkMessage p_Message) throws Exception 
		{
			MAVLinkPacket l_Packet = p_Message.pack();
			
			l_Packet.seq = this.m_SequenceNumber;
			
			l_Packet.sysid = 255;
			l_Packet.compid = 0;
			
			this.sendData(l_Packet.encodePacket());
		}
}
