package geco.data;

public class CustomER extends IDataReceiver
{
	private DataEmitter 	m_Emitter;
	
	public IDataEmitter		getEmitter()	{ return this.m_Emitter; }
	
	
	public CustomER()
	{
		this.m_Emitter = new DataEmitter();
	}
	
	@Override
	void onDataReceived(byte[] p_Data) {
		// TODO Auto-generated method stub
		
		System.out.println("Data received : ");
		
		System.out.println(new String(p_Data));
		
		String l_Hello = new String(" sending : Hello !");
		this.m_Emitter.sendData(l_Hello.getBytes());
	}
	
	@Override
	void onConnected() {
		// TODO Auto-generated method stub
		System.out.println("Connected");
	}
	@Override
	void onDisconnected() {
		System.out.println("Disconnected");
	}
	@Override
	void onConnectionLost() {
		// TODO Auto-generated method stub
		System.err.println("Connection lost");
	}
	@Override
	void onReconnected() {
		// TODO Auto-generated method stub
		System.out.println("Reconnected");
	}
}
