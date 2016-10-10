package geco.io;

public class UDPConnectorFactory extends DataConnectorFactory
{
private static UDPConnectorFactory g_Handler;
	
	public static void init() throws Exception
		{
			if (UDPConnectorFactory.g_Handler == null)
				UDPConnectorFactory.g_Handler = new UDPConnectorFactory();
		}
	
	public static UDPConnectorFactory getInstance()
		{
			return UDPConnectorFactory.g_Handler;
		}
	
	public UDPConnectorFactory() throws Exception
	{
		super();
	}

	@Override
	protected DataConnector CreateConnector() throws Exception 
	{
		UDPConnector l_Connector = new UDPConnector();
		return l_Connector;
	}
}
