package geco.vehicle.CommonVehicle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.MAVLink.common.msg_statustext;

public interface ICommonVehicleListener 
{
	void 			onStatusTextReceived				(msg_statustext p_StatusTextMessage);
	
	void 			onAccelerationChanged				(Vector3D p_Acceleration, 	Long p_TimeStamp);
	void			onAngularSpeedChanged				(Vector3D p_AngularSpeed, 	Long p_TimeStamp);
	void 			onMagneticFieldChanged				(Vector3D p_MagneticField, 	Long p_TimeStamp);
	
	void			onYawChanged						(Double p_Value, 			Long p_TimeStamp);
	void			onPitchChanged						(Double p_Value, 			Long p_TimeStamp);
	void			onRollChanged						(Double p_Value, 			Long p_TimeStamp);
	
	void			onYawSpeedChanged					(Double p_Value, 			Long p_TimeStamp);
	void			onPitchSpeedChanged					(Double p_Value, 			Long p_TimeStamp);
	void			onRollSpeedChanged					(Double p_Value, 			Long p_TimeStamp);
	
	void			onLatitudeChanged					(Integer p_Value, 			Long p_TimeStamp);
	void			onLongitudeChanged					(Integer p_Value, 			Long p_TimeStamp);
	void			onHeightChanged						(Double p_Value,  			Long p_TimeStamp);
	
}
