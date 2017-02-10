package org.usfirst.frc.team2028.robot.subsystem;

import org.usfirst.frc.team2028.robot.Parameters;
import org.usfirst.frc.team2028.robot.component.DriveSide;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Drivetrain extends Subsystem {
    private DriveSide leftSide;
    private DriveSide rightSide;

    private Solenoid lowGear,highGear;
    
    private Command defaultCommand;
    
    public Drivetrain(Command defaultCommand)
    {
    	this.defaultCommand = defaultCommand;
    	
    	leftSide = new DriveSide(Parameters.SideOfRobot.LEFT);
    	rightSide = new DriveSide(Parameters.SideOfRobot.RIGHT);
    	
    	lowGear = new Solenoid(Parameters.PneumaticChannel.LOW_GEAR.getChannel());
    	highGear = new Solenoid(Parameters.PneumaticChannel.HIGH_GEAR.getChannel());
    	
    	highGear.set(false);
    	lowGear.set(true);
    }
    
    public void crabDrive(double angle, double speed)
    {
    	angle += 0.5;
    	leftSide.crabDrive(angle, speed);
    	rightSide.crabDrive(angle, speed);
    	
		double sumCurAngle =0;
//		double angle = 0;
//		double sumPrevQuarterAngle = 0;
//		double prevQuarterAngle = angle-0.25;
//		double sumNextQuarterAngle = 0;
//		double nextQuarterAngle = angle+0.25;
		
//		Beginning of test code
		if(sumCurAngle >= 0.5)
		{
			System.out.println("Positive");;
		}
		else if(sumCurAngle <= 0.5)
		{
			System.out.println("Negative");
		}
		else
		{
			System.out.println("Failed");
		}
//		Ending of test code
    	
//    	if(stick.getRawAxis(0) > 0)
//    	{
//    		speed = -speed
//    	} 
//    	else if(stick.getRawAxis(0) < 0)
//    	{
//    		speed = speed
//    	}
//    	else
//    	{
//    		System.out.println(Failed);
//    	}
    }
    
    public void swerveDrive(double angle, double speed)
    {
    	System.out.println("Sw: "+angle);
//    	angle*=0.125;
//    	angle += 0.5;
    	System.out.println("SW After: "+angle);
		double in = ((45.0/360.0)*angle);
		double out = ((16.97/360.0)*angle);
		if(angle > 0)
		{
			System.out.println("here");
			leftSide.swerveDrive(in, speed);
			rightSide.swerveDrive(out, speed);
		}
		else
		{
			leftSide.swerveDrive(out, speed);
			rightSide.swerveDrive(in, speed);
		}
    }
    
    public void spinOnAxis(double speed)
    {
    	leftSide.spinOnAxis(speed);
    	rightSide.spinOnAxis(speed);
    }
    
    public void setLowGear()
    {
    	highGear.set(false);
    	lowGear.set(true);
    	System.out.println("Hi");
    }
    
    public void setHighGear()
    {
    	lowGear.set(false);
    	highGear.set(true);
    }
    
    public void setDefaultCommand(Command command)
    {
    	defaultCommand = command;
    	System.out.println("Default");
    }
    
    @Override
    protected void initDefaultCommand() 
    {
    	if(defaultCommand != null)
    	{
    		System.out.println("InitDef");
    		setDefaultCommand(defaultCommand);
    	}
    }
    
    public void printNeededOffsets()
    {
    	leftSide.printNeededOffsets();
    	rightSide.printNeededOffsets();
    }
    
    public void enableTurning(boolean enable)
    {
    	leftSide.enableTurngin(enable);
    	rightSide.enableTurngin(enable);
    }

}
