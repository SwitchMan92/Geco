package geco.io;

import geco.utility.worker_handler.WorkerHandler;

public abstract class DataConnectorFactory 
{
	
	
	protected DataConnectorFactory() throws Exception
	{
		WorkerHandler.init(32);
	}
	
	protected abstract DataConnector CreateConnector() throws Exception;
	
	public IDataConnector createConnector() throws Exception
	{
		DataConnector l_Connector = this.CreateConnector();
		WorkerHandler.getInstance().addWorker(l_Connector);
		return l_Connector;
	}
}
