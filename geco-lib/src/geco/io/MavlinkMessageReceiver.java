package geco.io;

import com.MAVLink.Parser;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkStats;

import MAVLink.MAVLinkPacket;

public abstract class MavlinkMessageReceiver extends IDataReceiver implements IMavlinkMessageReceiver
{
	private Parser m_MavlinkPacketParser;
	
	public MavlinkMessageReceiver() 
		{
			this.m_MavlinkPacketParser = new Parser();
		}

	@Override
	protected void onDataReceived(byte[] p_Data) throws Exception 
	{
		for (byte l_Byte : p_Data)
			{
				MAVLinkPacket l_Packet = this.m_MavlinkPacketParser.mavlink_parse_char(Byte.toUnsignedInt(l_Byte));
				
				if (l_Packet != null)
					this.onMessageReceived(l_Packet.unpack());
			}
	}

}
