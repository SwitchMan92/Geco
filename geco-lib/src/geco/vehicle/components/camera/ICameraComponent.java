package geco.vehicle.components.camera;

public interface ICameraComponent 
{
	public void onVideoCaptureStart();
	public void onImageCaptureStart();
	
	public void onVideoCaptureStop();
	public void OnImageCaptureStop();
	
	public void onVideoDataReceived(byte[] p_Data);
	public void onImageDataReceived(byte[] p_Data);
	
	public void startImageCapture();
	public void startVideoCapture();
	
	public void stopVideoCapture();
	public void stopImageCapture();
	
}
