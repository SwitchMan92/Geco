package geco.io;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class DataConnectorFactory 
{
	
	private ExecutorService 	m_ThreadPool;
	private int					m_Numthreads;
	
	protected DataConnectorFactory(int p_NumThreads) 
	{
		this.m_Numthreads = p_NumThreads;
		this.m_ThreadPool = Executors.newFixedThreadPool(m_Numthreads);
	}
	
	protected abstract DataConnector CreateConnector() throws Exception;
	//String p_Address, int p_Port
	public IDataConnector createConnector() throws Exception
	{
		DataConnector l_Connector = this.CreateConnector();
		this.m_ThreadPool.execute(l_Connector);
		return l_Connector;
	}
	
	@Override
	protected void finalize() throws Throwable 
	{
		this.m_ThreadPool.shutdownNow();
		super.finalize();
	}
	
}
