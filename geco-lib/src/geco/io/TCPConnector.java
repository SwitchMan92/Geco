package geco.io;

import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;


public class TCPConnector extends DataConnector
{
	private Socket 			m_ClientSocket;
	private ReentrantLock	m_SocketLock;
	
	protected TCPConnector() { super(); }
	
	public final void connect(String p_Address, int p_Port) throws Exception 
	{
		this.m_ClientSocket	=	new Socket(p_Address, p_Port);
		this.m_SocketLock	=	new ReentrantLock();
		super.connect(p_Address, p_Port);
	}

	public final void disconnect() throws Exception 
	{
		if (!this.isConnected())
			throw new ConnectionAlreadyClosedException();
				
		super.disconnect();
		this.m_ClientSocket.close();
	}
	
	public final void sendDataToServer(byte[] p_Data) throws Exception
	{
		this.m_SocketLock.lock();
		
		try
			{
				this.m_ClientSocket.getOutputStream().write(p_Data);
				this.m_ClientSocket.getOutputStream().flush();
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
				int l_BytesAvailable = this.m_ClientSocket.getInputStream().available();
						
				byte[] l_ByteBufferIn = new byte[l_BytesAvailable];
				int l_BytesRead = this.m_ClientSocket.getInputStream().read(l_ByteBufferIn);
						
				byte[] l_ByteBufferOut = new byte[l_BytesRead];
				System.arraycopy(l_ByteBufferIn, 0, l_ByteBufferOut, 0, l_BytesRead);
						
				return l_ByteBufferOut;
			}
		finally
			{
				this.m_SocketLock.unlock();
			}
		
	}

}
