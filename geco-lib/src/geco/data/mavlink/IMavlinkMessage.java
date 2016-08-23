package geco.data.mavlink;

public abstract class IMavlinkMessage 
{
	abstract byte 		getHeader			();
	abstract byte 		getLength			();
	abstract byte 		getSequenceNumber	();
	abstract byte 		getSystemID			();
	abstract byte 		getComponentID		();
	abstract byte 		getMessageID		();
	abstract byte[] 	getPayload			();
	abstract byte[]		getCRC				();
	
	abstract void		setHeader			(byte p_Header);
	abstract void		setSequenceNumber	(byte p_SequenceNumber);
	abstract void		setSystemID			(byte p_SystemID);
	abstract void		setComponentID		(byte p_ComponentID);
	abstract void 		setMessageID		(byte p_MessageID);
	abstract void 		setPayload			(byte[] p_Payload);
}
