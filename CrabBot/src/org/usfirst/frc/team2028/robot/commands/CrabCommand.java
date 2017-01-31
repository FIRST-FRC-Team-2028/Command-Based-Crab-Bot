package org.usfirst.frc.team2028.robot.commands;

import org.usfirst.frc.team2028.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrabCommand extends Command {

	Joystick stick;
	
    public CrabCommand(Joystick stick) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.stick = stick;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double[] polar = getPolarCoords();
    	Robot.drive.crabDrive(polar[0],polar[1]);
    	
    }

    public double[] getPolarCoords()
    {
    	double x = normalizeInput(stick.getRawAxis(0));
		double y = normalizeInput(-stick.getRawAxis(1));

		double degrees = Math.toDegrees(Math.atan2(x,y));
    	degrees += 180;
    	degrees /= 360;
    	double volts = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
    	
    	return new double[] {degrees,volts};
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
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
