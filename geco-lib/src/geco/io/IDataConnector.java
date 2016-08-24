package geco.io;

import java.util.ArrayList;

public abstract class IDataConnector
{
	abstract void 						connect				(String p_Address, int p_Port) 		throws Exception;
	abstract void 						disconnect			() 									throws Exception;
	
	abstract void 						sendDataToServer	(byte[] p_Data)						throws Exception;
	abstract void						sendDataToReceivers	(byte[] p_Data)						throws Exception;
	
	abstract void						addReceiver			(IDataReceiver p_Receiver)			throws Exception;
	abstract void						removeReceiver		(IDataReceiver p_Receiver)			throws Exception;
	abstract IDataReceiver				getReceiver			(int p_Index)						throws Exception;
	abstract ArrayList<IDataReceiver>	getReceivers		();
	
	abstract CONNECTOR_STATUS			getStatus			();
}
