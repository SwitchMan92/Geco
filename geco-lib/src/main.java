import geco.data.CustomER;
import geco.data.DataEmitter;
import geco.data.DataPipeline;
import geco.data.TCPConnector;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DataPipeline l_Dp = new DataPipeline();
		TCPConnector l_Connector = new TCPConnector();
		CustomER l_Receiver = new CustomER();
		
		l_Dp.addConnector(l_Connector);
		l_Dp.addEmitter(l_Receiver.getEmitter());
		
		l_Dp.connect(l_Connector, l_Receiver);
		l_Dp.connect(l_Receiver.getEmitter(), l_Connector);
		
		try
		{
			l_Connector.connect("127.0.0.1", 10000);
			Thread.sleep(5);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
	}

}
