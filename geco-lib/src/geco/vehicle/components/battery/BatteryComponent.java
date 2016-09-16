package geco.vehicle.components.battery;

import java.util.ArrayList;

public class BatteryComponent implements IBatteryComponent
{
	private Float m_BatteryPercentage;
	private Float m_MinVoltage;
	private Float m_MaxVoltage;
	
	private ArrayList<IBatteryComponentListener> m_BatteryListener;
	
	
	protected void onBatteryPercentageChanged	(float p_Percentage)
	{
		synchronized(this.m_BatteryPercentage)
			{
				this.m_BatteryPercentage = p_Percentage;
				
				synchronized(this.m_BatteryListener)
					{
						this.m_BatteryListener.forEach((l_BatteryListener) -> l_BatteryListener.onBatteryPercentageChanged(p_Percentage));
					}
			}
	}
	
	protected void onMinVoltageChanged			(float p_Voltage)
	{
		synchronized(this.m_MinVoltage)
			{
				this.m_MinVoltage = p_Voltage;
				
				synchronized(this.m_BatteryListener)
					{
						this.m_BatteryListener.forEach((l_BatteryListener) -> l_BatteryListener.onMinVoltageChanged(p_Voltage));
					}
			}
	}
	
	protected void onMaxVoltageChanged			(float p_Voltage)
	{
		synchronized(this.m_MaxVoltage)
			{
				this.m_MaxVoltage = p_Voltage;
				
				synchronized(this.m_BatteryListener)
					{
						this.m_BatteryListener.forEach((l_BatteryListener) -> l_BatteryListener.onMaxVoltageChanged(p_Voltage));
					}
			}
	}
	
	
	@Override
	public float getRemainingPercentage() 
	{
		synchronized(this.m_BatteryPercentage) { return this.m_BatteryPercentage; }
	}
	
	@Override
	public float getMaxVoltage() 
	{
		synchronized(this.m_MaxVoltage) 		{ return this.m_MaxVoltage; }
	}
	
	@Override
	public float getMinVoltage() 
	{
		synchronized(this.m_MinVoltage) 		{ return this.m_MinVoltage; }
	}
}
