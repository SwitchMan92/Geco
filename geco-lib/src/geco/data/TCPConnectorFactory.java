package geco.data;

public class TCPConnectorFactory extends DataConnectorFactory
{
	private static TCPConnectorFactory g_Factory;
	
	public static void init(int p_NumThreads) throws Exception
	{
		if (TCPConnectorFactory.g_Factory == null)
			TCPConnectorFactory.g_Factory = new TCPConnectorFactory(p_NumThreads);
		else
			throw new Exception("Factory already initialized");
	}
	
	public static TCPConnectorFactory getInstance()
	{
		return TCPConnectorFactory.g_Factory;
	}

	private TCPConnectorFactory(int p_NumThreads)
	{
		super(p_NumThreads);
	}

	@Override
	protected DataConnector CreateConnector(String p_Address, int p_Port) throws Exception {
		return new TCPConnector(p_Address, p_Port);
	}

}
