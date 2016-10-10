package geco.test;

import java.util.Arrays;
import java.util.Scanner;

import com.MAVLink.common.msg_mission_count;

public class main
{
	public static void main(String[] args) 
	{
		try
			{
				CustomVehicle l_Vehicle = new CustomVehicle();
			
				l_Vehicle.connect("tcp", "127.0.0.1", 5762);
				
				Scanner reader = new Scanner(System.in);  // Reading from System.in
				
				while(true)
				{
					System.out.println("Enter a command: ");
					System.out.println("1 - switch to guided armed");
					System.out.println("2 - arm throttle");
					System.out.println("3 - takeoff 20");
					System.out.println("4 - send long command");
					System.out.println("5 - load parameters");
					System.out.println("6 - request parameters");
					System.out.println("7 - set waypoint");
					
					int n = reader.nextInt();
					
					switch (n)
					{
						case 1:
							l_Vehicle.setMode(4);
							break;
							
						case 2:
							l_Vehicle.arm_throttle();
							break;
							
						case 3:
							l_Vehicle.takeOff(30);
							break;
							
						case 4:
							reader.nextLine();
							String l_NextLine = reader.nextLine();
							
							float[] l_Params = new float[9];
							
							int counter=0;
							
							for (String l_ParamString : l_NextLine.split(" "))
								{
									l_Params[counter] = Float.parseFloat(l_ParamString);
									counter++;
								}
							
							l_Vehicle.sendLong((short)l_Params[0], Arrays.copyOfRange(l_Params, 1, 8));
							break;
							
						case 5:
							l_Vehicle.setParameters();
							break;
						
						case 6:
							l_Vehicle.requestParameters();
							break;
						
						case 7:
							
							msg_mission_count l_MsgCount = new msg_mission_count();
							
							l_MsgCount.target_system 	= 0;
							l_MsgCount.target_component = 0;
							
							l_MsgCount.count = 1;
							
							l_Vehicle.sendMessage(l_MsgCount);
							
						default:
							break;
					}
				}
			}
		catch(Exception e)
			{
				e.printStackTrace();
			}	
	}

}
