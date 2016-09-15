package geco.io;

public interface IDataConnectorFactory 
{
	IDataConnector createConnector(String p_Address, int p_Port) throws Exception;
}
