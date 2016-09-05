package geco.vehicle.BasicVehicle;

public class AttitudeData 
{
	private Double m_Roll;
	private Double m_Pitch;
	private Double m_Yaw;
	private Double m_YawSpeed;
	private Double m_PitchSpeed;
	private Double m_RollSpeed;
	
	protected AttitudeData()
	{
		this.m_Roll 		= 	new Double(0d);
		this.m_Pitch 		= 	new Double(0d);
		this.m_Yaw			=	new Double(0d);
		this.m_RollSpeed	=	new Double(0d);
		this.m_YawSpeed		=	new Double(0d);
		this.m_PitchSpeed	=	new Double(0d);
	}
	
	protected AttitudeData(double p_Yaw, double p_Pitch, double p_Roll)
	{
		this();
		
		this.m_Roll 	= 	new Double(p_Roll);
		this.m_Pitch 	= 	new Double(p_Pitch);
		this.m_Yaw		=	new Double(p_Yaw);
	}
	
	protected AttitudeData(double p_Yaw, double p_Pitch, double p_Roll, double p_YawSpeed, double p_PitchSpeed, double p_RollSpeed)
	{
		this(p_Yaw, p_Pitch, p_Roll);
		
		this.m_RollSpeed 	= 	new Double(p_RollSpeed);
		this.m_PitchSpeed	=	new Double(p_PitchSpeed);
		this.m_YawSpeed		=	new Double(p_YawSpeed);
	}
	
	public Double 		getYaw			() 						{return this.m_Yaw; 							}
	public Double 		getPitch		()						{return this.m_Pitch;							}
	public Double 		getRoll			()						{return this.m_Roll;							}
	
	public Double 		getYawSpeed		() 						{return this.m_YawSpeed; 						}
	public Double 		getPitchSpeed	()						{return this.m_PitchSpeed;						}
	public Double 		getRollSpeed	()						{return this.m_RollSpeed;						}
	
	
	public void			setYaw			(double p_Yaw)			{this.m_Yaw			= new Double(p_Yaw);		}
	public void			setPitch		(double p_Pitch)		{this.m_Pitch		= new Double(p_Pitch);		}
	public void			setRoll			(double p_Roll)			{this.m_Roll 		= new Double(p_Roll);		}
	
	public void			setYawSpeed		(double p_YawSpeed)		{this.m_YawSpeed	= new Double(p_YawSpeed);	}
	public void			setPitchSpeed	(double p_PitchSpeed)	{this.m_PitchSpeed	= new Double(p_PitchSpeed);	}
	public void			setRollSpeed	(double p_RollSpeed)	{this.m_RollSpeed 	= new Double(p_RollSpeed);	}
	
	
}
