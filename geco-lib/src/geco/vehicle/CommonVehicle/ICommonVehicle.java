package geco.vehicle.CommonVehicle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;


public interface ICommonVehicle 
{
	Short 				getAutopilot					();
	Short 				getMavlinkVersion				();
	Integer				getMavState						();
	Integer 			getBaseMode						();
	Vector3D			getAcceleration					();
	Vector3D			getAngularSpeed					();
	Vector3D			getMagneticField				();
	
	Double				getYaw							();
	Double				getPitch						();
	Double				getRoll							();
	
	Double				getYawSpeed						();
	Double				getRollSpeed					();
	Double				getPitchSpeed					();

	Integer 			getLatitude						();
	Integer 			getLongitude					();
	
	Double 				getHeight						();
	
	void 				addListener						(ICommonVehicleListener p_Listener);
	void 				removeListener					(ICommonVehicleListener p_Listener);
	
}









