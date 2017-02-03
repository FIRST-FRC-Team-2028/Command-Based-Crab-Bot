package org.usfirst.frc.team2028.robot.subsystem;

import org.usfirst.frc.team2028.robot.Parameters;
import org.usfirst.frc.team2028.robot.component.DriveSide;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
//    	Check this code before testing
//     	String Current1 = "CurrentFL";
//     	String Current2 = "CurrentFR";
//     	String Current3 = "CurrentRL";
//     	String Current4 = "CurrentRR";
//    	double current = 0.002;
//    	SmartDashboard.putNumber(Current1, current);
//    	SmartDashboard.putNumber(Current2, current);
//    	SmartDashboard.putNumber(Current3, current);
//    	SmartDashboard.putNumber(Current4, current);
//    	Check this code before testing
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
