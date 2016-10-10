package geco.monitoring.mavlink.listener;

public abstract class CommonMavlinkMessageListener implements ICommonMavlinkMessageListener
{
	private int m_SystemId;
	private int m_ComponentId;
	
	public CommonMavlinkMessageListener() {}
	
	public CommonMavlinkMessageListener(int p_SystemId, int p_ComponentId)
	{
		this.m_SystemId 	= 	p_SystemId;
		this.m_ComponentId 	= 	p_ComponentId;
	}
	
	public int getSystemId()
	{
		return this.m_SystemId;
	}
	
	public int getComponentId()
	{
		return this.m_ComponentId;
	}
	
	
	
	
}
