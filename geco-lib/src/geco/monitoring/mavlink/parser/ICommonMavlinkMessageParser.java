package geco.monitoring.mavlink.parser;

import com.MAVLink.Messages.MAVLinkMessage;

import geco.monitoring.mavlink.listener.IMavlinkMessageListener;

public interface ICommonMavlinkMessageParser 
{
	void 	addListener		(IMavlinkMessageListener p_Listener);
	void 	removeListener	(IMavlinkMessageListener p_Listener);
	void	parseMessage	(MAVLinkMessage p_Message);
}
