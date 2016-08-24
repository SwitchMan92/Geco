package geco.io;

public abstract class IDataReceiver 
{
	abstract void onDataReceived(byte[] p_Data) throws Exception;
	
	public abstract void onConnected		(IDataConnector p_Connector);
	public abstract void onDisconnected	(IDataConnector p_Connector);
	public abstract void onConnectionLost	(IDataConnector p_Connector);
	public abstract void onReconnected		(IDataConnector p_Connector);
}
