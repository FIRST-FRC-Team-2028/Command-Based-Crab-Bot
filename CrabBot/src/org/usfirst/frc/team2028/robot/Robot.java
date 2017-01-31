
package org.usfirst.frc.team2028.robot;


import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
public class Robot extends SampleRobot {
    
	Wheel rearLeft,rearRight,frontLeft,frontRight;
	DriveUnit rightDrive, leftDrive;
	CANTalon frontDrive, rearDrive;
	int count =0;
	boolean enabled = false;
	Compressor comp;
	Solenoid low,high;
	double p = 9, i =0.0002, d=0, pos = 0;
	boolean centered = false; 
	boolean oneStickDriving = true;
	Joystick stick,potStick,oneStick;
	
	
    public Robot() {
    	stick = new Joystick(1);
    	potStick = new Joystick(4);
    	oneStick = new Joystick(5);
    	comp = new Compressor();
    	comp.setClosedLoopControl(true);
    	
    	rearLeft = new Wheel(31,-0.03);
//    	rearLeft.disableControl();
    	rearRight = new Wheel(22,0);
    	frontLeft = new Wheel(20,0.18);
    	frontRight = new Wheel(44,0.70);
    	
//    	rearLeft.disableControl();
//    	rearRight.disableControl();
//    	frontLeft.disableControl();
//  	frontRight.disableControl();
        
    	rightDrive = new DriveUnit(11,43);
    	leftDrive = new DriveUnit(32,51);
        
        low = new Solenoid(0);
        high = new Solenoid(7);
        high.set(false);
        low.set(true);
        
        SmartDashboard.putNumber("Steering P", p);
        SmartDashboard.putNumber("Steering I", i);
        SmartDashboard.putNumber("Steering D", d);
        SmartDashboard.putNumber("Steering Position Setpoint", pos);
    }
    
    public void robotInit() {
        
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {
    	
    
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
    	comp.start();
        while (isOperatorControl() && isEnabled()) {
        	
//        	System.out.println();
        	double x,y,swerve;
        	if(!oneStickDriving)
        	{
	        	x = normalizeInput(stick.getRawAxis(0));
	        	y = normalizeInput(-stick.getRawAxis(1));//The problem might be in here. We
	//        	have to make it to where the y is different that way it can cross with no problem.
	//        	Hope it works
	        	swerve = normalizeInput(potStick.getRawAxis(3));
        	}
        	else
        	{
        		x = normalizeInput(oneStick.getRawAxis(0));
        		y = normalizeInput(-oneStick.getRawAxis(1));
        		if(oneStick.getRawButton(2))
        			swerve = normalizeInput(-oneStick.getRawAxis(2));
        		else
        			swerve =0;
        	}
        	
//        	System.out.println("X: "+x+ "  Y: "+y);
        	double degrees = Math.toDegrees(Math.atan2(x,y));
        	degrees += 180;
        	degrees /= 360;
        	double volts = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));

//        	System.out.println("Controller Degrees: "+degrees);

//        	if(isWithinDeadband(y))
//        		degrees = 0.5;
//        	degrees+=0.25;
//        	System.out.println("Controller Degrees: "+degrees);
        	
        	if(!centered)
        	{
        		rearLeft.center();
        		rearRight.center();
        		frontLeft.center();
        		frontRight.center();
        		if(rearLeft.atSetpoint() && rearRight.atSetpoint() && frontLeft.atSetpoint() && frontRight.atSetpoint())
        		{
        			centered = true;
//        			rightDrive.set(0.25);
        		}
        	}
        	else if((stick.getRawButton(1)&&!oneStickDriving) ||(oneStick.getRawButton(1)&&oneStickDriving))
        	{
        		if(!oneStickDriving)
        			volts = normalizeInput(potStick.getRawAxis(3));
        		else
        			volts = normalizeInput(oneStick.getRawAxis(2));
        		frontLeft.setPosition(0.125);
        		frontRight.setPosition(-0.125);
        		rearLeft.setPosition(-0.125);
        		rearRight.setPosition(0.125);
        		
        		leftDrive.set(volts);
        		rightDrive.set(-volts);
        	}
        	else
        	{
        		if(swerve == 0)
        		{
	        		rearLeft.setPosition(degrees);
	//        		System.out.println("W/o:"+frontRight.getPositionWithoutOffset());
	//        		System.out.println("W:"+frontRight.getPosition());
	//        		System.out.println("Pos:"+frontRight.getOffsetPosition(0));
	        		rearRight.setPosition(degrees);
	        		frontLeft.setPosition(degrees);
	        		frontRight.setPosition(degrees);
	        		
	        	}
	        	else
	        	{
//	        		swerve*=0.125;
	        		double in = (45.0/360);
	        		double out = 16.97/360;
	        		double swerveRight = swerve;
	        		double swerveLeft= swerve;
	        		
	        		System.out.println(in);
	        		if(swerve >0)
	        		{
	        			System.out.println("swerve pos");
	        			swerveRight *= out;
	        			swerveLeft *= in;
	        		}
	        		else if(swerve <0)
	        		{
	        			System.out.println("swerve neg");

	        			swerveRight *= in;
	        			swerveLeft *= out;
	        		}
	        		System.out.println("SL:"+swerveLeft);
	        		System.out.println("SR:"+swerveRight);
	    			frontLeft.setPosition(0.5-swerveLeft);
	    			frontRight.setPosition(0.5-swerveRight);
	    			rearLeft.setPosition(0.5+swerveLeft);
	    			rearRight.setPosition(0.5+swerveRight);
	    			if(!oneStickDriving)
	    				volts = -stick.getRawAxis(1);
	    			else
	    				volts = -oneStick.getRawAxis(1);

	        	}
        		rightDrive.set(volts);
            	leftDrive.set(volts);
        	}
        	if(stick.getRawButton(5) || oneStick.getRawButton(5))
        	{
        		toggleGear(false);
        	}
        	else if(stick.getRawButton(6) || oneStick.getRawButton(6))
        	{
        		toggleGear(true);
        	}
        	
        	
        	
//        	System.out.println(rearRight);
//        	System.out.println(frontLeft);
//        	System.out.println(frontRight);
//        	 stick.setRumble(RumbleType.kLeftRumble, 1);
//     		stick.setRumble(RumbleType.kRightRumble, 1);//        	motor.
//        	motor.set(0.75);
//        	frontDrive.set(0.5);
//        	rearDrive.set(0.5);
//        	motor.set(pos);
//        	SmartDashboard.putNumber("Steering Position",motor.getPosition());
//        	SmartDashboard.putNumber("Steering Position Setpoint", stick.getRawAxis(1));
            Timer.delay(0.05);		// wait for a motor update time
        }
        stick.setRumble(RumbleType.kLeftRumble, 0);
		stick.setRumble(RumbleType.kRightRumble, 0);
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
    
    public void toggleGear(boolean highGear)
    {
    	if(highGear)
    	{
    		high.set(false);
    		low.set(true);
    	}
    	else
    	{
    		low.set(false);
    		high.set(true);
    	}
    }
    
    /**
     * Runs during test mode
     */
    public void test() {
    }
}
