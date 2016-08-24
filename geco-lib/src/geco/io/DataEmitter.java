package geco.io;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataEmitter extends IDataEmitter
{
	private CopyOnWriteArrayList<IDataConnector> m_Connectors;

	public DataEmitter() { this.m_Connectors = new CopyOnWriteArrayList<IDataConnector>(); }
	
	@Override
	public void sendData(byte[] p_Data) throws Exception
	{
		Iterator<IDataConnector> l_Iterator = this.m_Connectors.iterator();
		
		while (l_Iterator.hasNext())
		{
			IDataConnector l_Connector = l_Iterator.next();
			
			l_Connector.sendDataToServer(p_Data);
		}
		
	}

	@Override
	public final void addConnector(IDataConnector p_Connector) throws Exception
		{
			if (this.m_Connectors.contains(p_Connector))
				throw new Exception("Connector already present in the connector list");
			
			this.m_Connectors.add(p_Connector);
		}

	@Override
	public final void removeConnector(IDataConnector p_Connector) throws Exception
		{
			if (!this.m_Connectors.contains(p_Connector))
				throw new Exception("Connector not present in the connector list");
			
			this.m_Connectors.remove(p_Connector);
		}
}
