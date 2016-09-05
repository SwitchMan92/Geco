package geco.io;

import java.util.ArrayList;

public abstract class IDataConnector
{
	public abstract void 						connect				(String p_Address, int p_Port) 		throws Exception;
	public abstract void 						disconnect			() 									throws Exception;
	
	abstract void 								sendDataToServer	(byte[] p_Data)						throws Exception;
	abstract void								sendDataToReceivers	(byte[] p_Data)						throws Exception;
	
	public abstract void						addReceiver			(DataReceiver p_Receiver)			throws Exception;
	public abstract void						removeReceiver		(DataReceiver p_Receiver)			throws Exception;
	public abstract DataReceiver				getReceiver			(int p_Index)						throws Exception;
	public abstract ArrayList<DataReceiver>		getReceivers		();
	
	abstract CONNECTOR_STATUS					getStatus			();
}
