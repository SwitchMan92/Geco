package geco.io;

import java.util.Vector;

import com.MAVLink.Parser;
import com.MAVLink.common.msg_command_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.enums.MAV_CMD;

import MAVLink.MAVLinkPacket;

public class CustomER extends IDataReceiver
{
	private DataEmitter 		m_Emitter;
	private Parser				m_Parser;
	private Vector<Integer>		m_MsgIds;
	
	public IDataEmitter		getEmitter()	{ return this.m_Emitter; }
	
	public CustomER()
	{
		this.m_Emitter 	= 	new DataEmitter();
		this.m_Parser 	= 	new Parser(); 
		this.m_MsgIds	=	new Vector<Integer>();
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	@Override
	void onDataReceived(byte[] p_Data) throws Exception 
	{
		for (byte l_Byte : p_Data)
			{
				MAVLinkPacket l_Packet = this.m_Parser.mavlink_parse_char(Byte.toUnsignedInt(l_Byte));
				
				if (l_Packet != null)
					{
						//if (!this.m_MsgIds.contains(l_Packet.msgid))
						//{
							this.m_MsgIds.add(l_Packet.msgid);
							System.out.println(String.valueOf(l_Packet.sysid) + " : " + l_Packet.unpack());
							
							//msg_heartbeat l_Message = new msg_heartbeat();
							
							//MAVLinkPacket l_PacketToSend = l_Message.pack();
							//this.getEmitter().sendData(l_PacketToSend.encodePacket());
						//}
						
					}
			}
	}

	@Override
	public void onConnected(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionLost(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReconnected(IDataConnector p_Connector) {
		// TODO Auto-generated method stub
		
	}

}
