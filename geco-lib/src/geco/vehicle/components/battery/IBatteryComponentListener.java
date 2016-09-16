package geco.vehicle.components.battery;

public interface IBatteryComponentListener 
{
	void onBatteryPercentageChanged	(float p_Percentage);
	void onMinVoltageChanged		(float p_Voltage);
	void onMaxVoltageChanged		(float p_Voltage);
}
