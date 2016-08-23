package geco.data;

import java.util.ArrayList;

public class DataEmitter extends IDataEmitter
{
	private ArrayList<IDataPipeline> m_Pipelines;
	
	public DataEmitter()
	{
		this.m_Pipelines = new ArrayList<IDataPipeline>();
	}	
	
	protected void addPipeline(IDataPipeline p_Pipeline)
	{
		if (!this.m_Pipelines.contains(p_Pipeline))
			this.m_Pipelines.add(p_Pipeline);
	}
	
	protected void removePipeline(IDataPipeline p_Pipeline)
	{
		if (this.m_Pipelines.contains(p_Pipeline))
			this.m_Pipelines.remove(p_Pipeline);
	}

	public void sendData(byte[] p_Data)
	{
		this.m_Pipelines.forEach((l_Pipeline) -> {
			l_Pipeline.onDataEmitted(this, p_Data);			
		});
	}
	
	
}
