package geco.test;



import geco.io.UDPConnector;
import geco.io.UDPConnectorFactory;

public class main
{

	public static void main(String[] args) 
	{
		try
			{
				UDPConnector l_Connector = (UDPConnector)UDPConnectorFactory.getInstance().createConnector();
				TestMavlinkReceiver l_Er = new TestMavlinkReceiver();
				
				l_Connector.addReceiver(l_Er);
				l_Connector.connect("127.0.0.1", 14550);
			}
		catch(Exception e)
			{
				e.printStackTrace();
			}	
	}

}
