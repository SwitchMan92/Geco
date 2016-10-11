package geco.io;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UDPConnector extends SocketConnector
{
	private DatagramSocket 	m_ClientSocket;
	private InetAddress		m_IpAddress;
	
	
	protected UDPConnector() { super(); }
	
	public void connect(String p_Address, int p_Port) throws Exception 
	{
		this.m_IpAddress 	= 	InetAddress.getByName(p_Address);
			    
		this.m_ClientSocket = 	new DatagramSocket(null);
		this.m_ClientSocket.setSoTimeout(1);
		this.m_ClientSocket.setReuseAddress(true);
		this.m_ClientSocket.bind(new InetSocketAddress(InetAddress.getByName(p_Address), p_Port));
		
		super.connect(p_Address, p_Port);
	}

	@Override
	public final void disconnect() throws Exception 
	{
		if (!this.isConnected())
			throw new ConnectionAlreadyClosedException();
				
		super.disconnect();
		this.m_ClientSocket.close();
	}

	protected final byte[] readDataFromServer() throws Exception 
	{	
		
		byte[] l_Buffer = new byte[this.m_ClientSocket.getReceiveBufferSize()];
		DatagramPacket receivePacket = new DatagramPacket(l_Buffer, l_Buffer.length);
		
		try
			{	
				this.m_ClientSocket.receive(receivePacket);
			}
		catch(java.net.SocketException se)
			{
				
			}
		catch (Exception e)
			{
				throw e;
			}
		
		return receivePacket.getData();	
	}

	@Override
	public void sendDataToServer(byte[] p_Data) throws Exception 
	{
		DatagramPacket sendPacket = new DatagramPacket(p_Data, p_Data.length, this.m_IpAddress, this.getPort());
		this.m_ClientSocket.send(sendPacket);
	}

}
