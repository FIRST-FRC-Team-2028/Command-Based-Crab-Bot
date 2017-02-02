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
    }
    
    public void swerveDrive(double angle, double speed)
    {
    	System.out.println("Sw: "+angle);
//    	angle*=0.125;
    	angle += 0.5;
    	System.out.println("SW After: "+angle);
		double in = (45.0/360.0);
		double out = (16.97/360.0);
		if(angle > 0)
		{
			System.out.println("here");
			leftSide.swerveDrive(angle*in, speed);
			rightSide.swerveDrive(angle*out, speed);
		}
		else
		{
			leftSide.swerveDrive(angle*out, speed);
			rightSide.swerveDrive(angle*in, speed);
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

}
