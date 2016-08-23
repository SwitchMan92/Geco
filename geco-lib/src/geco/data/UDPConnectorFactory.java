package geco.data;

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
	protected DataConnector CreateConnector(String p_Address, int p_Port) throws Exception {
		return new UDPConnector(p_Address, p_Port);
	}
}
