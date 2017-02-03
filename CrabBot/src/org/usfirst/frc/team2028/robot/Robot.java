
package org.usfirst.frc.team2028.robot;


import org.usfirst.frc.team2028.robot.commands.DriveCommand;
import org.usfirst.frc.team2028.robot.subsystem.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends IterativeRobot {

	Joystick stick,potStick,oneStick;
	
	
	public static DriveCommand driveComm;
	
	public static Drivetrain drive;
	
    public Robot() {
    	oneStick = new Joystick(5);
    	
    	drive = new Drivetrain(driveComm);
    	
    	driveComm = new DriveCommand(oneStick);
    	
    	drive.setDefaultCommand(driveComm);
        
    	driveComm.start();
    }
    
    @Override
    public void robotInit() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void teleopInit() {
    	drive.enableTurning(true);
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopPeriodic() {
    	Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void disabledPeriodic() {

    }
    
    /**
     * Runs during test mode
     */
    @Override
    public void testInit() {
    }
    
    @Override
    public void testPeriodic()
    {
    	drive.enableTurning(false);
    	drive.printNeededOffsets();
    }
}
