package geco.monitoring.mavlink.listener;

public abstract class MavlinkMessageListener implements IMavlinkMessageListener
{
	private short m_SystemId;
	private short m_ComponentId;
	
	public MavlinkMessageListener() {}
	
	public MavlinkMessageListener(short p_SystemId, short p_ComponentId)
	{
		this.m_SystemId 	= 	p_SystemId;
		this.m_ComponentId 	= 	p_ComponentId;
	}
	
	public short getSystemId()
	{
		return this.m_SystemId;
	}
	
	public short getComponentId()
	{
		return this.m_ComponentId;
	}
	
	
	
	
}
