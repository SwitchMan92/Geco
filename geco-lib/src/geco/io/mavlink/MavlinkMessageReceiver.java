package geco.io.mavlink;

import com.MAVLink.Parser;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkStats;

import MAVLink.MAVLinkPacket;
import geco.io.DataReceiver;

public abstract class MavlinkMessageReceiver extends DataReceiver implements IMavlinkMessageReceiver
{
	private Parser m_MavlinkPacketParser;
	
	public MavlinkMessageReceiver() 
		{
			this.m_MavlinkPacketParser = new Parser();
		}

	protected void onDataReceived(byte[] p_Data) throws Exception
	{
		for (byte l_Byte : p_Data)
			{
				MAVLinkPacket l_Packet = this.m_MavlinkPacketParser.mavlink_parse_char(Byte.toUnsignedInt(l_Byte));
				
				if (l_Packet != null)
				{
					try
						{
							MAVLinkMessage l_Message = l_Packet.unpack();
							
							this.onMessageReceived(l_Packet.unpack());
						}
					catch(java.lang.IndexOutOfBoundsException e)
						{
							
						}
					catch (Exception e)
						{
							throw e;
						}
				}
					
						
			}
	}

}
