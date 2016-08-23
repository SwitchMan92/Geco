package geco.navigation;

public interface ITerrainPointManager 
{
	void addTerrainPoint		(ITerrainPoint p_Point);
	void removeTerrainPoint		(ITerrainPoint p_Point);
	
	void getTerrainPoint		(long p_Index);
}
