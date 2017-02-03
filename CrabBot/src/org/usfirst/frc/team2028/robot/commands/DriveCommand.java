package org.usfirst.frc.team2028.robot.commands;

import org.usfirst.frc.team2028.robot.Parameters.Buttons;
import org.usfirst.frc.team2028.robot.Robot;
import org.usfirst.frc.team2028.robot.subsystem.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveCommand extends Command {

	static Joystick stick;
	static Drivetrain drive;
	
    public DriveCommand(Joystick stick) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	drive = Robot.drive;
    	this.stick = stick;
    	System.out.println("got here");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	System.out.println("Comm init");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
//    	Beginning of Copy from Old Crab Command Code
//    	double[] stickPolar = stick.getInput;
//    	Robot.drive.crabDrive(stickPolar[0], stickPolar[1]);
//    	End of Copy from Old Crab Command Code
    	double[] polar = getPolarCoords();
////    	Beginning of test code
//    	if(-stick.getRawAxis(1) > 1 )
//    	{
//    		drive.crabDrive(0, 1);
//    	}
//    	else if(-stick.getRawAxis(1) < -1 )
//    	{
//    		drive.crabDrive(0, -1);
//    	}
//    	else
//    	{
//    		System.out.println("Blank");
//    	}
////    	End of test code
    	if(isButtonPressed(Buttons.SWEARVE))
    	{
    		System.out.println("swearve");
    		
    		drive.swerveDrive(-stick.getRawAxis(2), polar[1]);
    	}
    	else if(isButtonPressed(Buttons.SPIN_ON_AXIS))
    	{
    		drive.spinOnAxis(-stick.getRawAxis(2));
    	}
    	else
    	{
    		drive.crabDrive(polar[0],polar[1]);
    	}
    	
//    	System.out.println("I AM HERE");
    	
    }

    public double[] getPolarCoords()
    {
    	double x = normalizeInput(stick.getRawAxis(0));
		double y = normalizeInput(-stick.getRawAxis(1));
//		Beginning of test code
//    	if(-stick.getRawAxis(1) > 0.1)
//    	{
//    		drive.crabDrive(0, 0.25);
//    	}
//    	else if(-stick.getRawAxis(1) < -0.1)
//    	{
//    		drive.crabDrive(0, -0.25);
//    	}
//    	else
//    	{
//    		System.out.println("if statement not met.");
//    	}
//    	End of test code
		double degrees = Math.toDegrees(Math.atan2(x,y));
    	degrees += 180;     //was 180 not 90
    	degrees /= 360;     //was 360 not 180
    	double volts = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
    	
    	System.out.println("Degree: "+degrees);
    	return new double[] {degrees,volts};

    }
    
    public boolean isButtonPressed(Buttons b)
    {
    	return stick.getRawButton(b.getID());
    }
    
    public double normalizeInput(double pos)
    {
    	if(isWithinDeadband(pos))
    		return 0;
    	return pos;
    }
    
    public boolean isWithinDeadband(double pos)
    {
    	return Math.abs(pos)<0.09;
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
//    	System.out.println("isfinished");
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	System.out.println("end");

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	System.out.println("ive been ...");

    }
}
