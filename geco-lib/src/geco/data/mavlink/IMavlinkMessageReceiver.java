package geco.data.mavlink;

public abstract class IMavlinkMessageReceiver 
{
	abstract void receiveMessage(IMavlinkMessage p_Message);
}
