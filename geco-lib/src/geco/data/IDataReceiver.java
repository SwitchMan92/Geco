package geco.data;

public abstract class IDataReceiver 
{
	abstract void onDataReceived(byte[] p_Data) throws Exception;
	
	abstract void onConnected		(IDataConnector p_Connector);
	abstract void onDisconnected	(IDataConnector p_Connector);
	abstract void onConnectionLost	(IDataConnector p_Connector);
	abstract void onReconnected		(IDataConnector p_Connector);
}
