package geco.io;

public class TCPConnectorFactory extends DataConnectorFactory
{
	
	private static TCPConnectorFactory g_Handler;
	
	public static void init() throws Exception
		{
			if (TCPConnectorFactory.g_Handler == null)
				TCPConnectorFactory.g_Handler = new TCPConnectorFactory();
		}
	
	public static TCPConnectorFactory getInstance()
		{
			return TCPConnectorFactory.g_Handler;
		}
	

	public TCPConnectorFactory() throws Exception
	{
		super();
	}

	@Override
	protected DataConnector CreateConnector() throws Exception 
	{
		TCPConnector l_Connector = new TCPConnector();
		return l_Connector;
	}

}
