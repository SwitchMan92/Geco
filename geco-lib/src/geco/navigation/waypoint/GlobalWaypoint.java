package geco.navigation.waypoint;

public class GlobalWaypoint 
{
	private float	m_Latitude;
	private float 	m_Longitude;
	private float 	m_Height;
	private long	m_Timestamp;
	
	public GlobalWaypoint() {}
	
	public GlobalWaypoint(float p_Latitude, float p_Longitude, float p_Height, long p_Timestamp)
	{
		this.m_Latitude 	= 	p_Latitude;
		this.m_Longitude 	= 	p_Longitude;
		this.m_Height 		= 	p_Height;
		this.m_Timestamp	=	p_Timestamp;
	}
	
	public float 	getLongitude	() 		{ return this.m_Longitude; 	}
	public float 	getLatitude		() 		{ return this.m_Latitude; 	}
	public float 	getHeight		()		{ return this.m_Height; 	}
	public long		getTimestamp	()		{ return this.m_Timestamp;	}
}
