package geco.test;

import geco.io.CustomER;
import geco.io.UDPConnector;
import geco.io.UDPConnectorFactory;

public class main {

	public static void main(String[] args) {
		
		try
		{
			UDPConnectorFactory.init(32);
			UDPConnector l_Connector = (UDPConnector)UDPConnectorFactory.getInstance().createConnector("127.0.0.1", 14550);
			TestMavlinkReceiver l_Er = new TestMavlinkReceiver();
			
			l_Connector.addReceiver(l_Er);
			l_Connector.connect("127.0.0.1", 14550);
			
			Thread.sleep(3000);
			
			l_Connector.disconnect();
			
			Thread.sleep(2000);
			
			l_Connector.connect("127.0.0.1", 14550);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
