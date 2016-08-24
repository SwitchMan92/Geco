package geco.io;

public class UDPConnectorFactory extends DataConnectorFactory
{
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
	protected DataConnector CreateConnector(String p_Address, int p_Port) throws Exception 
	{
		UDPConnector l_Connector = new UDPConnector();
		l_Connector.setAddress(p_Address);
		l_Connector.setPort(p_Port);
		return l_Connector;
	}
}
