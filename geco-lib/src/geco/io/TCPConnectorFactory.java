package geco.io;

public class TCPConnectorFactory extends DataConnectorFactory
{
	static
		{
			try
				{
					TCPConnectorFactory.init(32);
				}
			catch (Exception e)
				{
					e.printStackTrace();
				}
		}
	
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
	protected DataConnector CreateConnector() throws Exception 
	{
		TCPConnector l_Connector = new TCPConnector();
		return l_Connector;
	}

}
