package geco.data;

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
	
	protected abstract DataConnector CreateConnector(String p_Address, int p_Port) throws Exception;
	
	public IDataConnector createConnector(String p_Address, int p_Port) throws Exception
	{
		DataConnector l_Connector = this.CreateConnector(p_Address, p_Port);
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
