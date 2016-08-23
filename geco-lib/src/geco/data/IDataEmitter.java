package geco.data;

public abstract class IDataEmitter 
{
	abstract void sendData				(byte[] p_Data);
	
	abstract void addPipeline			(IDataPipeline p_Pipeline);
	abstract void removePipeline		(IDataPipeline p_Pipeline);
}
