package geco.io;

public abstract class SocketConnector extends DataConnector
{

	private int m_Port;

	public SocketConnector() { super(); }
	
	public void connect(String p_Address, int p_Port) throws Exception
		{
			this.setPort(p_Port);
			super.connect(p_Address);
		}
		
	public void setPort(int p_Port) throws Exception
		{
			if (this.getStatus() == CONNECTOR_STATUS.CS_DISCONNECTED)
				this.m_Port = p_Port;
			else
				throw new Exception("Close Connector before trying to change port");
		}
	
	public int getPort()
		{
			return this.m_Port;
		}
	
}
