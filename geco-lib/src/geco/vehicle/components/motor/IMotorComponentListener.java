package geco.vehicle.components.motor;

public interface IMotorComponentListener 
{
	void onArmed			();
	void onDisarmed			();
	
	void onPwmChanged		(float p_Value);
	void onPwmMinChanged	(float p_Value);
	void onPwmMaxChanged	(float p_Value);
	void onPwmTypeChanged	(float p_Value);
	void onSpinChanged		(float p_Value);
	void onSpinMinChanged	(float p_Value);
	void onSpinMaxChanged	(float p_Value);
}
