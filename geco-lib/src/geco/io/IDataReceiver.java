package geco.io;

public interface IDataReceiver 
{
	public abstract void onConnected		(IDataConnector p_Connector);
	public abstract void onDisconnected		(IDataConnector p_Connector);
	public abstract void onConnectionLost	(IDataConnector p_Connector);
	public abstract void onReconnected		(IDataConnector p_Connector);
}
