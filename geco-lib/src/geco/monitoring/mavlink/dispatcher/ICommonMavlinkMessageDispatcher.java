package geco.monitoring.mavlink.dispatcher;

import geco.monitoring.mavlink.listener.ICommonMavlinkMessageListener;

public interface ICommonMavlinkMessageDispatcher 
{
	void 	addListener		(ICommonMavlinkMessageListener p_Listener);
	void 	removeListener	(ICommonMavlinkMessageListener p_Listener);
}
