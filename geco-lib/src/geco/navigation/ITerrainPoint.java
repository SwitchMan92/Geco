package geco.navigation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;


public interface ITerrainPoint 
{
	double 		getLatitude			();
	double 		getLongitude		();
	
	void		setLatitude			(double p_Lat);
	void		setLongitude		(double p_Long);
	
	Vector2D 	getDistanceVector	();
	double 		getDistance			();
}
