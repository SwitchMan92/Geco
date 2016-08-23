package geco.data;

public abstract class IDataEmitter 
{
	public abstract void sendData				(byte[] p_Data) 				throws Exception;
	public abstract void addConnector			(IDataConnector p_Connector) 	throws Exception;
	public abstract void removeConnector		(IDataConnector p_Connector) 	throws Exception;
}
