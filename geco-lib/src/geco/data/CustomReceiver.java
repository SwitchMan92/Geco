package geco.data;

public class CustomReceiver extends IDataReceiver
{

	@Override
	void onDataReceived(byte[] p_Data) 
	{
		for (byte l_Byte : p_Data)
		{
			System.out.print((char)l_Byte);
		}
	}

	@Override
	void onConnected() {
		System.out.println("Connected");
	}

	@Override
	void onDisconnected() {
		System.out.println("Disconnected");
	}

	@Override
	void onConnectionLost() {
		System.err.println("Connection lost");
	}

	@Override
	void onReconnected() {
		System.err.println("Reconnected");
	}

}
