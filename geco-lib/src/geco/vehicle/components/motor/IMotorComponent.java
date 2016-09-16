package geco.vehicle.components.motor;

import geco.vehicle.components.common.ICommonComponent;

public interface IMotorComponent extends ICommonComponent
{
	public void 	arm			();
	public void 	disarm		();
	
	public float	getPwm		();
	public float	getPwmMin	();
	public float	getPwmMax	();
	public float	getPwmType	();
	
	public float	getSpinMin	();
	public float	getSpinMax	();
	public float	getSpin		();
}
