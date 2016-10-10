package geco.control.mavlink.common.actor;

import java.util.ArrayList;

import geco.navigation.waypoint.IGlobalWaypoint;

public interface ICommonMavlinkActor 
{
	void 	sendHearbeat	();
	void	sendWaypoints	(ArrayList<IGlobalWaypoint> p_Waypoints);
	void 	setMode			(short p_Mode);
	void 	armMotors		();
	void	goToWaypoint	(int p_WaypointIndex);
}
