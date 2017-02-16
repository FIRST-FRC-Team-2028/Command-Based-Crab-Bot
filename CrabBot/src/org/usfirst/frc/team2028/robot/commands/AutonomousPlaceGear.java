package org.usfirst.frc.team2028.robot.commands;

import org.usfirst.frc.team2028.robot.Robot;
import org.usfirst.frc.team2028.robot.subsystem.PixyCamera;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousPlaceGear extends CommandGroup {
	
	boolean done = false;
	int num = 0;
	
	public AutonomousPlaceGear() {
		requires(Robot.pixyCam);
		requires(Robot.drive);
	}
    protected void execute() {
//    	System.out.println("Finding");
    	Robot.pixyCam.getTargets();
    	if(!Robot.pixyCam.isCloseEnough() && Robot.pixyCam.hasTarget())
    	{
    		Robot.drive.swerveDrive(-Robot.pixyCam.getTargetAngle(), 0.25);
    		done = false;
    		num = 0;
    	} 
    	else
    	{
    		if(Robot.pixyCam.hasTarget())
    		{
    			Robot.drive.swerveDrive(0, 0);
    			done = true;
    		}
    		else
    		{
    			num++;
    			if(num >= 20)
    			{
    				System.out.println("Canceled");
    		    	Robot.drive.swerveDrive(0, 0);
    				cancel();
    			}
    		}
    	}
    }

    protected boolean isFinished() {
        // TODO Auto-generated method stub
    	if(done)System.out.println("Finished");
        return done;
    }

    public void setup()
    {
    	done = false;
    	num = 0;
    }
    
    protected void initialize() {
//    	System.out.println("");
    	done = false;
    	num = 0;
    }

}
