package geco.data;

public abstract class IDataPipeline 
{
	abstract void				addConnector			(IDataConnector p_Connector);
	abstract void				removeConnector			(IDataConnector p_Connector);

	abstract void				addEmitter				(IDataEmitter p_Emitter);
	abstract void				removeEmitter			(IDataEmitter p_Emitter);
	
	abstract void 				connect					(IDataConnector p_Connector, IDataReceiver p_Receiver);
	abstract void 				connect					(IDataEmitter p_Emitter, IDataConnector p_Connecter);
	
	abstract void 				disconnect				(IDataConnector p_Connector, IDataReceiver p_Receiver);
	abstract void 				disconnect				(IDataEmitter p_Emitter, IDataConnector p_Connecter);
	
	abstract void 				onDataEmitted			(IDataEmitter p_Emitter, byte[] p_Data);
	abstract void 				onDataReceived			(IDataConnector p_Connector, byte[] p_Data);
	
	abstract void				onConnected				(IDataConnector p_Connector);
	abstract void				onDisconnected			(IDataConnector p_Connector);
	abstract void				onConnectionLost		(IDataConnector p_Connector);
	abstract void				onReconnected			(IDataConnector p_Connector);
	
}
