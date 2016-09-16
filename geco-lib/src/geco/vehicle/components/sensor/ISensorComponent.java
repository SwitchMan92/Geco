package geco.vehicle.components.sensor;

import geco.vehicle.components.common.ICommonComponent;

public interface ISensorComponent extends ICommonComponent
{
	public float getRawSens();
	public float getExtStat();
	public float getRcChannel();
	public float getRawControl();
	public float getPosition();
	public float getParameters();
	public float getAdsb();
	
	public float getExtra1();
	public float getExtra2();
	public float getextra3();
	
}
