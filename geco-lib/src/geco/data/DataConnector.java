package geco.data;


public abstract class DataConnector extends IDataConnector implements Runnable
{
	private IDataPipeline 			m_DataPipeline;
	private String					m_Address;
	private int						m_Port;
	
	
	protected String 	getAddress	() { return this.m_Address; }
	protected int 		getPort		() { return this.m_Port; }
	
	abstract byte[] readDataFromServer() throws Exception;
	
	
	void connect(String p_Address, int p_Port) throws Exception
	{
		this.m_Address 	= 	p_Address;
		this.m_Port		=	p_Port;
	}
	
	protected final IDataPipeline getDataPipeline() 
	{
		return this.m_DataPipeline;
	};
	
	protected final void setDataPipeline(IDataPipeline p_Pipeline) 
	{
		this.m_DataPipeline = p_Pipeline;
	};
	
	protected final void sendDataToPipeline(byte[] p_Data) 
	{
		this.getDataPipeline().onDataReceived(this, p_Data);
	}
	
	@Override
	public final void run() 
	{
		try 
			{
				byte[] l_Data = this.readDataFromServer();
				this.sendDataToPipeline(l_Data);
			}
		catch (Exception e) 
			{
				e.printStackTrace();
			}	
			
	}
	
	@Override
	void onConnected() {
		this.getDataPipeline().onConnected(this);
	}

	@Override
	void onDisconnected() {
		this.getDataPipeline().onDisconnected(this);
	}

	@Override
	void onConnectionLost() {
		this.getDataPipeline().onConnectionLost(this);
	}

	@Override
	void onReconnected() {
		this.getDataPipeline().onReconnected(this);
	}
	
}
