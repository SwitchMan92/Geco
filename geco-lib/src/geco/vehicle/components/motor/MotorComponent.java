package geco.vehicle.components.motor;

import geco.vehicle.CommonVehicle.ICommonVehicle;

public class MotorComponent implements IMotorComponent
{
	private Integer 		m_Id;
	private ICommonVehicle 	m_Vehicle;
	private Float			m_Pwm;
	private Float			m_MinPwm;
	private Float			m_MaxPwm;
	private Float			m_PwmType;
	private Float			m_Spin;
	private Float			m_MinSpin;
	private Float 			m_MaxSpin;
	
	
	@Override
	public int getId() 
	{
		synchronized(this.m_Id) { return this.m_Id; }
	}

	@Override
	public void setId(int p_Id) 
	{
		synchronized(this.m_Id) { this.m_Id = p_Id; }
	}

	@Override
	public void arm() 
	{
		synchronized(this.m_Vehicle) { this.m_Vehicle.arm(this.m_Id); }
	}

	@Override
	public void disarm() 
	{
		synchronized(this.m_Vehicle) { this.m_Vehicle.disarm(this.m_Id); }
	}

	@Override
	public float getPwm() 
	{
		synchronized(this.m_Pwm) { return this.m_Pwm; }
	}

	@Override
	public float getPwmMin() 
	{
		synchronized(this.m_MinPwm) { return this.m_MinPwm; }
	}

	@Override
	public float getPwmMax() 
	{
		synchronized(this.m_MaxPwm) { return this.m_MaxPwm; }
	}

	@Override
	public float getPwmType() 
	{
		synchronized(this.m_PwmType) { return this.m_PwmType; }
	}

	@Override
	public float getSpinMin() 
	{
		synchronized(this.m_MinSpin) { return this.m_MinSpin; }
	}

	@Override
	public float getSpinMax() 
	{
		synchronized(this.m_MaxSpin) { return this.m_MaxSpin; }
	}

	@Override
	public float getSpin() 
	{
		synchronized(this.m_Spin) { return this.m_Spin; }
	}
	
}
