package geco.navigation;


public interface IZone
{
	double 				getSurface		();
	ITerrainPoint 		getCenter		();
	double				getRadius		();
	
	boolean				isColliding		(IZone p_Zone);
	boolean				isColliding		(ITerrainPoint p_Point);
}
