package geco.data;

public abstract class IDataConnector
{
	abstract void 			connect				(String p_Address, int p_Port) 		throws Exception;
	abstract void 			disconnect			() 									throws Exception;
	
	abstract void 			sendDataToServer	(byte[] p_Data)						throws Exception;
	abstract void 			sendDataToPipeline	(byte[] p_Data)						throws Exception;
	
	abstract void 			setDataPipeline		(IDataPipeline p_Pipeline);
	abstract IDataPipeline	getDataPipeline		();
	
	abstract void			onConnected			();
	abstract void			onDisconnected		();
	abstract void			onConnectionLost	();
	abstract void			onReconnected		();
}
