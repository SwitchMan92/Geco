package geco.data;

import java.io.IOException;
import java.net.Socket;


public class TCPConnector extends DataConnector
{
	private Socket 			m_ClientSocket;
	
	
	public final void connect(String p_Address, int p_Port) throws Exception 
	{
		this.m_ClientSocket	=	new Socket(p_Address, p_Port);
		super.connect(p_Address, p_Port);
		this.onConnected();
	}

	public final void disconnect() throws IOException 
	{
		if (this.m_ClientSocket.isConnected())
			{
				this.m_ClientSocket.close();
				this.onDisconnected();
			}
			
	}

	protected final void sendDataToServer(byte[] p_Data) throws IOException
	{
		this.m_ClientSocket.getOutputStream().write(p_Data);
		this.m_ClientSocket.getOutputStream().flush();
	}

	protected final byte[] readDataFromServer() throws Exception 
	{
		try
			{
				int l_BytesAvailable = this.m_ClientSocket.getInputStream().available();
				
				byte[] l_ByteBufferIn = new byte[l_BytesAvailable];
				int l_BytesRead = this.m_ClientSocket.getInputStream().read(l_ByteBufferIn);
				
				byte[] l_ByteBufferOut = new byte[l_BytesRead];
				System.arraycopy(l_ByteBufferIn, 0, l_ByteBufferOut, 0, l_BytesRead);
				
				return l_ByteBufferOut;
			}
		catch ( java.net.SocketTimeoutException ste)
			{
			
				System.err.println(ste);
			
				this.onConnectionLost();
				
				this.disconnect();
				
				try
				{
					this.connect(this.getAddress(), this.getPort());
					this.onReconnected();
				}
				catch (Exception e)
				{
					System.err.println(e);
				}
								
				return new byte[0];
			}
		catch (Exception e)
			{
				System.err.println(e);
				return new byte[0];
			}
	}

}
