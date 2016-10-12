package geco.vehicle.CommonVehicle;

import java.util.HashMap;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;


public interface ICommonVehicle 
{
	Short					getMavType						();
	Short 					getAutopilot					();
	Short 					getMavlinkVersion				();
	Integer					getMavState						();
	Integer 				getBaseMode						();
	Vector3D				getAcceleration					();
	Vector3D				getAngularSpeed					();
	Vector3D				getMagneticField				();
	
	Double					getYaw							();
	Double					getPitch						();
	Double					getRoll							();
	
	Double					getYawSpeed						();
	Double					getRollSpeed					();
	Double					getPitchSpeed					();

	Integer 				getLatitude						();
	Integer 				getLongitude					();
	
	Double 					getHeight						();
	
	void 					arm								(int p_Id);
	void					disarm							(int p_Id);
	void 					setMode							(int p_Mode, int p_SubMode);
	void 					setParameter					(String p_Name, float p_Value);
	HashMap<String, Float> 	requestParameters				() throws Exception;
	void					sendLongCommand					(short p_Command, float...params);
	void					sendIntCommand					(short p_Command, float... params);
	void 					arm_throttle					();
	void 					disarm_throttle					();
}









