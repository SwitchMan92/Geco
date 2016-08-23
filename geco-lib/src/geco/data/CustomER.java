package geco.data;

import com.MAVLink.Parser;

import MAVLink.MAVLinkPacket;

public class CustomER extends IDataReceiver
{
	private DataEmitter 	m_Emitter;
	private Parser			m_Parser;
	
	public IDataEmitter		getEmitter()	{ return this.m_Emitter; }
	
	public CustomER()
	{
		this.m_Emitter 	= new DataEmitter();
		this.m_Parser 	= new Parser(); 
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
	void onDataReceived(byte[] p_Data) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println(bytesToHex(p_Data));
		
		for (byte l_Byte : p_Data)
			{
				MAVLinkPacket l_Packet = this.m_Parser.mavlink_parse_char(Byte.toUnsignedInt(l_Byte));
				
				if (l_Packet != null)
					System.out.println(l_Packet.unpack());
			}
		
		
		//String l_Hello = new String(" sending : Hello !");
		//this.m_Emitter.sendData(l_Hello.getBytes());
	}

	@Override
	void onConnected(IDataConnector p_Connector) {
		System.out.println("Connected");
	}


	@Override
	void onDisconnected(IDataConnector p_Connector) {
		System.out.println("Disconnected");
	}


	@Override
	void onConnectionLost(IDataConnector p_Connector) {
		System.err.println("Connection lost");
	}


	@Override
	void onReconnected(IDataConnector p_Connector) {
		System.out.println("Reconnected");
	}
}
