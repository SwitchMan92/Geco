package geco.vehicle.components.compass;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import geco.vehicle.components.common.ICommonComponent;

public interface ICompassComponent extends ICommonComponent
{
	public Vector3D getOffset1();
	public Vector3D getOffset2();
	public Vector3D getOffset3();
	
}
