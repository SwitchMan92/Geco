package geco.vehicle.BasicVehicle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;




public interface IBasicVehicle 
{
	Short 			getAutopilot();
	Short 			getMavlinkVersion();
	
	Integer			getMavState();
	Integer 		getBaseMode();
	
	Vector3D		getAcceleration();
	Vector3D		getAngularSpeed();
	Vector3D		getMagneticField();
	
	AttitudeData	getAttitude();
	
}
