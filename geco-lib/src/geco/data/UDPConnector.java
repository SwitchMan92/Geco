package geco.data;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class UDPConnector extends DataConnector
{
	private DatagramSocket 	m_ClientSocket;
	private InetAddress		m_IpAddress;
	
	protected UDPConnector() {}
	protected UDPConnector(String p_Address, int p_Port) throws Exception { this.connect(p_Address, p_Port); }
	
	protected void connect(String p_Address, int p_Port) throws Exception 
	{
	    this.m_IpAddress 	= 	InetAddress.getByName(p_Address);
	    this.m_ClientSocket = 	new DatagramSocket(p_Port);
		super.connect(p_Address, p_Port);
	}

	@Override
	protected void disconnect() throws Exception 
	{
		if (this.m_ClientSocket.isConnected())
			this.m_ClientSocket.close();
	}

	protected final byte[] readDataFromServer() throws Exception 
	{
		try
			{
				byte[] l_Buffer = new byte[this.m_ClientSocket.getReceiveBufferSize()];
				
				DatagramPacket receivePacket = new DatagramPacket(l_Buffer, l_Buffer.length);
				
				this.m_ClientSocket.receive(receivePacket);
					
				return receivePacket.getData();
			}
		catch (java.net.SocketTimeoutException ste)
			{
				return new byte[0];
			}
	}

	@Override
	public void sendDataToServer(byte[] p_Data) throws Exception 
	{
		DatagramPacket sendPacket = new DatagramPacket(p_Data, p_Data.length, this.m_IpAddress, this.getPort());
		this.m_ClientSocket.send(sendPacket);
	}

}
