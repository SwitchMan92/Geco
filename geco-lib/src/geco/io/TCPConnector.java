package geco.io;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;


public class TCPConnector extends DataConnector
{
	private Socket 			m_ClientSocket;
	private ReentrantLock 	m_SocketLock;
	
	protected TCPConnector() { this.m_SocketLock = new ReentrantLock(); }
	
	public final void connect(String p_Address, int p_Port) throws Exception 
	{
		//this.m_SocketLock.lock();
		
		try
			{
				this.m_ClientSocket	=	new Socket(p_Address, p_Port);
				super.connect(p_Address, p_Port);
			}
		catch(Exception e)
			{
				throw e;
			}
		finally
			{
				//this.m_SocketLock.unlock();
			}
	}

	public final void disconnect() throws Exception 
	{
		//this.m_SocketLock.lock();
		
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
				//this.m_SocketLock.unlock();
			}
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
	

	public final void sendDataToServer(byte[] p_Data) throws Exception
	{
		
		try
			{
				this.m_ClientSocket.getOutputStream().write(p_Data);
				this.m_ClientSocket.getOutputStream().flush();
			}
		catch (IOException ioe)
			{
				throw new DisconnectedException(ioe.getCause().toString());
			}
		catch (Exception e)
			{
				throw new LinkException(e.getCause().toString());
			}
		finally
			{
				//this.m_SocketLock.unlock();
			}
	}

	protected final byte[] readDataFromServer() throws Exception 
	{
		//this.m_SocketLock.lock();
		
		try
			{
				int l_BytesAvailable = this.m_ClientSocket.getInputStream().available();
				
				byte[] l_ByteBufferIn = new byte[l_BytesAvailable];
				int l_BytesRead = this.m_ClientSocket.getInputStream().read(l_ByteBufferIn);
				
				byte[] l_ByteBufferOut = new byte[l_BytesRead];
				System.arraycopy(l_ByteBufferIn, 0, l_ByteBufferOut, 0, l_BytesRead);
				
				return l_ByteBufferOut;
			}
		catch (IOException ioe)
			{
				throw new DisconnectedException(ioe.getCause().toString());
			}
		catch (Exception e)
			{
				throw new LinkException(e.getCause().toString());
			}
		finally
			{
				//this.m_SocketLock.unlock();
			}
	}

}
