package geco.io;

public abstract class DataReceiver implements IDataReceiver
{
	protected abstract void onDataReceived(byte[] p_Data) throws Exception;
}
