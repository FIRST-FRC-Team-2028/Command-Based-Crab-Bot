package org.usfirst.frc.team2028.robot.subsystem;

import java.util.List;

import org.usfirst.frc.team2028.robot.VisionTarget;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PixyCamera extends Subsystem {
    private SerialPort serialPort;
    private String newString,prevString;
    private StringBuilder builder;
    private Command defaultCom;
    private VisionTarget target;
    
    public PixyCamera() {
    	serialPort = new SerialPort(9600, Port.kUSB1);
    	serialPort.setWriteBufferMode(WriteBufferMode.kFlushOnAccess);
    	serialPort.writeString("init\n");
    }

    public int getFuelCount() {
    	return 0;
    }

    public int getTapeCount() {
    	return 0;
    }

    public List<VisionTarget> getAllFuel() {
    	return null;
    }

    public List<VisionTarget> getAllTape() {
    	return null;
    }

	@Override
	protected void initDefaultCommand() {
		if(defaultCom != null)
		{
			setDefaultCommand(defaultCom);
		}
	}
	
	public void setDefaultCommand(Command com)
	{
		defaultCom = com;
	}
	
	public void getTargets()
	{
		if(builder == null)builder = new StringBuilder();
		try{
		if(serialPort.getBytesReceived() > 0)
		{
			builder.append(serialPort.readString().trim());
//			newString += serialPort.readString();
//			System.out.println("here");
//			System.out.print("\nBuilding:\n"+builder.toString());
			if(builder.toString().contains("$"))
			{
				prevString = builder.toString();
				builder = new StringBuilder();
//				System.out.print("\nFinished Sting:\n"+prevString);
				
				String[] s = prevString.split("\n");
//				System.out.println("Printing "+s.length);
				if(s.length == 2)
				{
					s[1] = s[1].substring(0,s[1].length()-1);
					int[] target1 = new int[4];
					int[] target2 = new int[4];
					String[] str = s[0].split(",");
					String[] str2 = s[1].split(",");
					str2[3].replace("$", "");
					for(int i =0;i<str.length;i++)
					{
						target1[i] = Integer.parseInt(str[i]);
						target2[i] = Integer.parseInt(str2[i]);
					}
					int rectx = (target1[0]<target2[0]) ? target1[0]:target2[0];
					int recty = (target1[1]<target2[1]) ? target1[1]:target2[1];
					int rectwidth = (target1[0]>target2[0]) ? (target1[0]+target1[2])-target2[0]:(target2[0]+target2[2])-target1[0];
					int rectheight = (target1[1]>target2[0]) ? (target1[1]+target1[3])-target2[1]:(target2[1]+target2[3])-target1[1];
					target = new VisionTarget(rectx,recty,rectwidth,rectheight);
					System.out.println("Target: "+target.getAngleToCenter());
				}
//				for(String st:s)
//				{
//					System.out.println(st);
//				}
//				System.out.println("\nFirst: "+prevString.split(" ")[0]);
//				System.out.println("\nSecond: "+prevString.split(" ")[1]);
			}
		}
		}catch(Exception e)
		{
			e.printStackTrace();
//			System.out.println("Exception trying to get serial bytes: "+e.getMessage());
		}
	}

}
