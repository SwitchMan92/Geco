package geco.test;

import geco.data.CustomER;
import geco.data.UDPConnector;
import geco.data.UDPConnectorFactory;

public class main {

	public static void main(String[] args) {
		
		try
		{
			UDPConnectorFactory.init(32);
			UDPConnector l_Connector = (UDPConnector)UDPConnectorFactory.getInstance().createConnector("127.0.0.1", 14550);
			CustomER l_Er = new CustomER();
			
			l_Connector.addReceiver(l_Er);
			l_Er.getEmitter().addConnector(l_Connector);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
