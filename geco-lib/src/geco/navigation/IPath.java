package geco.navigation;

public interface IPath extends ITerrainPointManager
{
	double 			getDistance			();
	
	ITerrainPoint 	getStartingPoint	();
	ITerrainPoint 	getEndingPoint		();
}
