package geco.vehicle.components.gps;

public interface IGpsComponent 
{
	public float getType				();
	public float getNavigationFilter	();
	public float getAutoSwitch			();
	public float getMinDgps				();
	public float getSbasMode			();
	public float getMinElevation		();
	public float getRawData				();
	public float getGnssMode			(); // GNSS == GLOBAL NAVIGATION SATELLITE SYSTEM
	
}
