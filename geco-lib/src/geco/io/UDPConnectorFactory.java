package geco.io;

public class UDPConnectorFactory extends DataConnectorFactory
{
	static
		{
			try
				{
					UDPConnectorFactory.init(32);
				}
			catch (Exception e)
				{
					e.printStackTrace();
				}
		}
	
	
	private static UDPConnectorFactory g_Factory;
	
	public static void init(int p_NumThreads) throws Exception
	{
		if (UDPConnectorFactory.g_Factory == null)
			UDPConnectorFactory.g_Factory = new UDPConnectorFactory(p_NumThreads);
		else
			throw new Exception("Factory already initialized");
	}
	
	public static UDPConnectorFactory getInstance()
	{
		return UDPConnectorFactory.g_Factory;
	}

	private UDPConnectorFactory(int p_NumThreads)
	{
		super(p_NumThreads);
	}

	@Override
	protected DataConnector CreateConnector() throws Exception 
	{
		UDPConnector l_Connector = new UDPConnector();
		return l_Connector;
	}
}
