package geco.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;



public abstract class DataConnector extends IDataConnector implements Runnable
{
	public class DisconnectedException 			extends Exception { 
		public DisconnectedException(String p_Cause){
			super(p_Cause);
		}
	};
	
	public class LinkException					extends Exception {
		public LinkException(String p_Cause){
			super(p_Cause);
		}
	};
	
	public class SocketAlreadyClosedException 	extends Exception {
		public SocketAlreadyClosedException(){
			super("Socket is already closed");
		}
	};
	
	
	private String									m_Address;
	private int										m_Port;
	private CopyOnWriteArrayList<IDataReceiver> 	m_Receivers;
	
	private boolean 								m_Running;
	private ReentrantLock							m_RunningLock;
	
	private CONNECTOR_STATUS						m_Status;
	private ReentrantLock							m_StatusLock;
	
	abstract byte[] 	readDataFromServer	() throws Exception;
	
	protected String 	getAddress			() { return this.m_Address; }
	protected int 		getPort				() { return this.m_Port; }
	
	protected void connect(String p_Address, int p_Port) throws Exception
	{
		this.m_RunningLock	=	new ReentrantLock();
		this.m_StatusLock	=	new ReentrantLock();
		this.m_Receivers	=	new CopyOnWriteArrayList<IDataReceiver>();
		this.m_Address 		= 	p_Address;
		this.m_Port			=	p_Port;
		this.setRunning(true);
		
		this.onConnected();
	}
	
	void disconnect() throws Exception
		{
			this.onDisconnected();
		}
	
	public CONNECTOR_STATUS getStatus()
	{
		this.m_StatusLock.lock();
		
		try
			{
				return this.m_Status;
			}
		finally
			{
				this.m_StatusLock.unlock();
			}
	}
	
	private void setStatus(CONNECTOR_STATUS p_Status)
	{
		this.m_StatusLock.lock();
		
		try
			{
				this.m_Status = p_Status;
			}
		finally
			{
				this.m_StatusLock.unlock();
			}
	}
	
	
	protected boolean isRunning()
	{
		this.m_RunningLock.lock();
		
		try
			{
				return this.m_Running;
			}
		finally
			{
				this.m_RunningLock.unlock();
			}
	}
	
	protected void setRunning(boolean p_Running)
	{
		this.m_RunningLock.lock();
		
		try
			{
				this.m_Running = p_Running;
			}
		finally
			{
				this.m_RunningLock.unlock();
			}
	}
	
	@Override
	public void run()
	{
		while(this.isRunning())
			{
				try 
					{	
						byte[] l_Data = this.readDataFromServer();
						this.sendDataToReceivers(l_Data);	
					}
				catch (DisconnectedException|LinkException ioe) 
					{
						ioe.printStackTrace();
						try { this.disconnect(); this.onConnectionLost(); } 
						catch (Exception e) { e.printStackTrace(); }
					}
				catch(InterruptedException ie)
					{
						try { this.disconnect(); this.onDisconnected(); } 
						catch (Exception e) { e.printStackTrace(); }
						this.setRunning(false);
					}
				catch (Exception e) 
					{
						e.printStackTrace();
					}	
			}
	}
	
	private void onConnected()
	{
		this.setStatus(CONNECTOR_STATUS.CS_CONNECTED);
		
		this.m_Receivers.forEach((l_Receiver) -> {
			l_Receiver.onConnected(this);
		});
	}
	
	private void onDisconnected()
	{
		this.setStatus(CONNECTOR_STATUS.CS_DISCONNECTED);
		
		this.m_Receivers.forEach((l_Receiver) -> {
			l_Receiver.onDisconnected(this);
		});
	}
	
	private void onConnectionLost()
	{
		this.setStatus(CONNECTOR_STATUS.CS_DISCONNECTED);
		
		this.m_Receivers.forEach((l_Receiver) -> {
			l_Receiver.onConnectionLost(this);
		});
	}
	
	void sendDataToReceivers(byte[] p_Data) throws Exception
	{
		Iterator<IDataReceiver> l_Iterator = this.m_Receivers.iterator();
		
		while(l_Iterator.hasNext())
			{
				IDataReceiver l_Receiver = l_Iterator.next();
				l_Receiver.onDataReceived(p_Data);
			}
	}
	
	@Override
	public final void addReceiver(IDataReceiver p_Receiver) throws Exception 
	{
		if (!this.m_Receivers.contains(p_Receiver))
			this.m_Receivers.add(p_Receiver);
		else
			throw new Exception("Connector already connected to receiver");
	}
	@Override
	public final void removeReceiver(IDataReceiver p_Receiver) throws Exception 
	{
		if (this.m_Receivers.contains(p_Receiver))
			this.m_Receivers.remove(p_Receiver);
		else
			throw new Exception("Connector not connected to receiver");
	}
	@Override
	public final IDataReceiver getReceiver(int p_Index) throws Exception 
	{
		return this.m_Receivers.get(p_Index);
	}
	
	@Override
	public final ArrayList<IDataReceiver> getReceivers() 
	{
		return new ArrayList<IDataReceiver>(this.m_Receivers);
	}
	
}
