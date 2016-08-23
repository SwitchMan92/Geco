package geco.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DataPipeline extends IDataPipeline implements Runnable
{
	private Map<IDataConnector, ArrayList<IDataReceiver>> 		m_ConnectorMap;
	private Map<IDataEmitter, ArrayList<IDataConnector>> 		m_EmitterMap;
	
	private Map<IDataConnector, ConcurrentLinkedQueue<byte[]>>	m_ConnectorData;
	private Map<IDataEmitter, ConcurrentLinkedQueue<byte[]>>	m_EmitterData;
	
	private Thread												m_InternalThread;
	private	boolean												m_Running;
	private ReentrantLock										m_RunningLock;
	
	
	public DataPipeline()
	{
		this.m_ConnectorMap 	=	new HashMap<IDataConnector, ArrayList<IDataReceiver>>();
		this.m_EmitterMap 		= 	new HashMap<IDataEmitter, ArrayList<IDataConnector>>();
		
		this.m_ConnectorData 	= 	new HashMap<IDataConnector, ConcurrentLinkedQueue<byte[]>>();
		this.m_EmitterData 		= 	new HashMap<IDataEmitter, ConcurrentLinkedQueue<byte[]>>();
		
		this.m_RunningLock		=	new ReentrantLock();
		
		
		this.setRunning(true);
		this.m_InternalThread = new Thread(this);
		//this.m_InternalThread.setDaemon(true);
		this.m_InternalThread.start();
	}
	
	private final boolean isRunning()
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
	
	private final void setRunning(boolean p_Running)
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
	public void addConnector(IDataConnector p_Connector) 
	{
		if (!this.m_ConnectorMap.containsKey(p_Connector))
			this.m_ConnectorMap.put(p_Connector, new ArrayList<IDataReceiver>());
		
		if (!this.m_ConnectorData.containsKey(p_Connector))
			this.m_ConnectorData.put(p_Connector, new ConcurrentLinkedQueue<byte[]>());
		
		p_Connector.setDataPipeline(this);
	}

	@Override
	public void removeConnector(IDataConnector p_Connector) 
	{
		this.m_ConnectorMap.remove(p_Connector);
		this.m_ConnectorData.remove(p_Connector);
		
		p_Connector.setDataPipeline(null);
	}

	@Override
	public void addEmitter(IDataEmitter p_Emitter) 
	{
		if (!this.m_EmitterMap.containsKey(p_Emitter))
			this.m_EmitterMap.put(p_Emitter, new ArrayList<IDataConnector>());
		
		if (!this.m_EmitterData.containsKey(p_Emitter))
			this.m_EmitterData.put(p_Emitter, new ConcurrentLinkedQueue<byte[]>());
		
		p_Emitter.addPipeline(this);
		
	}

	@Override
	public void removeEmitter(IDataEmitter p_Emitter) 
	{
		this.m_EmitterMap.remove(p_Emitter);
		this.m_EmitterData.remove(p_Emitter);
		p_Emitter.removePipeline(this);
	}
	
	@Override
	public void connect(IDataConnector p_Connector, IDataReceiver p_Receiver) 
	{
		ArrayList<IDataReceiver> l_ReceiverList = this.m_ConnectorMap.get(p_Connector);
		
		if (!l_ReceiverList.contains(p_Receiver))
			l_ReceiverList.add(p_Receiver);
	}

	@Override
	public void connect(IDataEmitter p_Emitter, IDataConnector p_Connecter) 
	{
		ArrayList<IDataConnector> l_ConnectorList = this.m_EmitterMap.get(p_Emitter);
		
		if (!l_ConnectorList.contains(p_Connecter))
			l_ConnectorList.add(p_Connecter);
	}

	@Override
	public void disconnect(IDataConnector p_Connector, IDataReceiver p_Receiver) 
	{
		this.m_ConnectorMap.get(p_Connector).remove(p_Receiver);
	}

	@Override
	public void disconnect(IDataEmitter p_Emitter, IDataConnector p_Connecter) 
	{
		this.m_EmitterMap.get(p_Emitter).remove(p_Connecter);
	}

	@Override
	protected void onDataEmitted(IDataEmitter p_Emitter, byte[] p_Data) 
	{
		if (p_Data.length > 0)
			this.m_EmitterData.get(p_Emitter).add(p_Data);
	}

	@Override
	protected void onDataReceived(IDataConnector p_Connector, byte[] p_Data) 
	{
		if (p_Data.length > 0)
			this.m_ConnectorData.get(p_Connector).add(p_Data);
	}

	@Override
	public void run() 
	{
		while(this.isRunning())
			{
				
				ExecutorService l_ThreadPool = Executors.newFixedThreadPool(10);
				
				try 
					{
						
						Thread.sleep(1000);
					
						this.m_ConnectorData.forEach
							( 
								(l_Connector, l_DataList) -> 
									{
										
										Iterator<byte[]> l_DataIterator = l_DataList.iterator();
										
										while (l_DataIterator.hasNext())
											{
												byte[] l_Data = l_DataIterator.next();
												
												this.m_ConnectorMap.get(l_Connector).forEach((l_Receiver) -> { 
													l_Receiver.onDataReceived(l_Data);
												});
												
												l_DataIterator.remove();
											}
									}
							);
										
						this.m_EmitterData.forEach
							(
								(l_Emitter, l_DataList) -> 
									{
										
										Iterator<byte[]> l_DataIterator = l_DataList.iterator();
										
										while (l_DataIterator.hasNext())
											{
												byte[] l_Data = l_DataIterator.next();
												l_DataIterator.remove();
												
												System.out.println(new String(l_Data));
												System.out.flush();
												
												this.m_EmitterMap.get(l_Emitter).forEach(
														(l_Connector) -> 
															{
																try
																	{
																		l_Connector.sendDataToServer(l_Data);
																	} 
																catch (Exception e) 
																	{
																		e.printStackTrace();
																	}
															}
												);
											}		
									}
							);
						
						this.m_ConnectorMap.forEach(
								(l_Connector, l_ReceiverList) -> 
									{
										l_ThreadPool.execute((DataConnector)l_Connector);
									} 
								);
						
						l_ThreadPool.shutdown();
						l_ThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
						
					}
				catch (InterruptedException e1) 
					{
						e1.printStackTrace();
						l_ThreadPool.shutdownNow();
					}
			} 
	}
	
	public void finalize() throws Throwable
	{
		this.setRunning(false);
		//this.m_InternalThread.notify();
		this.m_InternalThread.join();
	}

	@Override
	protected void onConnected(IDataConnector p_Connector) {
		this.m_ConnectorMap.get(p_Connector).forEach((l_DataEmitter) -> {
			l_DataEmitter.onConnected();
		});
	}

	@Override
	protected void onDisconnected(IDataConnector p_Connector) {
		this.m_ConnectorMap.get(p_Connector).forEach((l_DataEmitter) -> {
			l_DataEmitter.onDisconnected();
		});
	}

	@Override
	protected void onConnectionLost(IDataConnector p_Connector) {
		this.m_ConnectorMap.get(p_Connector).forEach((l_DataEmitter) -> {
			l_DataEmitter.onConnectionLost();
		});
	}

	@Override
	protected void onReconnected(IDataConnector p_Connector) {
		this.m_ConnectorMap.get(p_Connector).forEach((l_DataEmitter) -> {
			l_DataEmitter.onReconnected();
		});
	}

}
