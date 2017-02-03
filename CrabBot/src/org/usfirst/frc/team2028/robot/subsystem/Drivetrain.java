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
    
    public void swerveDrive(double angle, double speed)
    {
    	leftSide.swerveDrive(angle, speed);
    	rightSide.swerveDrive(angle,speed);
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
    }
    
    @Override
    protected void initDefaultCommand() 
    {
    	if(defaultCommand != null)
    	{
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
