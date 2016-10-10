package geco.monitoring.mavlink.parser;

import geco.monitoring.mavlink.listener.ICommonMavlinkMessageListener;

public interface ICommonMavlinkMessageRouter 
{
	void 	addListener		(ICommonMavlinkMessageListener p_Listener);
	void 	removeListener	(ICommonMavlinkMessageListener p_Listener);
}
