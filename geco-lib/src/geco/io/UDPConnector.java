package geco.io;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.locks.ReentrantLock;

public class UDPConnector extends DataConnector
{
	private DatagramSocket 	m_ClientSocket;
	private InetAddress		m_IpAddress;
	private ReentrantLock	m_SocketLock;
	
	
	protected UDPConnector() { this.m_SocketLock = new ReentrantLock(); }
	
	public void connect(String p_Address, int p_Port) throws Exception 
	{
		this.m_SocketLock.lock();
		
		try
			{
			    this.m_IpAddress 	= 	InetAddress.getByName(p_Address);
			    
			    this.m_ClientSocket = 	new DatagramSocket(null);
			    this.m_ClientSocket.setReuseAddress(true);
			    this.m_ClientSocket.bind(new InetSocketAddress(InetAddress.getByName(p_Address), p_Port));
			    
				super.connect(p_Address, p_Port);
			}
		catch (Exception e)
			{
				throw e;
			}
		finally
			{
				this.m_SocketLock.unlock();
			}
	}

	@Override
	public final void disconnect() throws Exception 
	{
		this.m_SocketLock.lock();
		
		try
			{
				if (!this.isConnected())
					throw new ConnectionAlreadyClosedException();
				
				super.disconnect();
				this.m_ClientSocket.close();
			}
		catch (Exception e)
			{
				throw e;
			}
		finally
			{
				this.m_SocketLock.unlock();
			}
	}

	protected final byte[] readDataFromServer() throws Exception 
	{
		this.m_SocketLock.lock();
		
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
		finally
			{
				this.m_SocketLock.unlock();
			}
	}

	@Override
	public void sendDataToServer(byte[] p_Data) throws Exception 
	{
		this.m_SocketLock.lock();
		
		try
			{
				DatagramPacket sendPacket = new DatagramPacket(p_Data, p_Data.length, this.m_IpAddress, this.getPort());
				this.m_ClientSocket.send(sendPacket);
			}
		finally
			{
				this.m_SocketLock.unlock();
			}
	}

}
