package org.usfirst.frc.team2028.robot.commands;

import org.usfirst.frc.team2028.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DefaultPixyCommand extends Command {

    public DefaultPixyCommand() {
        requires(Robot.pixyCam);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	System.out.println("here");
    	Robot.pixyCam.getTargets();
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
