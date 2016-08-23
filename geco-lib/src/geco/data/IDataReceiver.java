package geco.data;

public abstract class IDataReceiver 
{
	abstract void onDataReceived(byte[] p_Data);
	
	abstract void onConnected		();
	abstract void onDisconnected	();
	abstract void onConnectionLost	();
	abstract void onReconnected		();
	
}
