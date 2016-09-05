package geco.test;



import geco.io.IDataConnector;
import geco.vehicle.BasicVehicle.BasicVehicle;

public class main
{

	public static void main(String[] args) 
	{
		try
			{
				BasicVehicle l_Vehicle = new BasicVehicle() {
					
					@Override
					public void onReconnected(IDataConnector p_Connector) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onDisconnected(IDataConnector p_Connector) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onConnectionLost(IDataConnector p_Connector) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onConnected(IDataConnector p_Connector) {
						// TODO Auto-generated method stub
						
					}
				};
				
				l_Vehicle.connect("tcp", "127.0.0.1", 5760);
				
			}
		catch(Exception e)
			{
				e.printStackTrace();
			}	
	}

}
