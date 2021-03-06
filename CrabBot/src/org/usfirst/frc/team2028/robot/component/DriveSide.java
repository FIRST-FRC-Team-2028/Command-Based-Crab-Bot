package org.usfirst.frc.team2028.robot.component;

import org.usfirst.frc.team2028.robot.Parameters.CanId;
import org.usfirst.frc.team2028.robot.Parameters.SideOfRobot;
import org.usfirst.frc.team2028.robot.Parameters.SteeringOffset;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class DriveSide {
    private Wheel frontWheel;

    private Wheel rearWheel;

    private CANTalon masterMotor;

    private CANTalon followerMotor;

    private SideOfRobot side;
    
    public DriveSide(SideOfRobot side) {
    	this.side = side;
        switch(side)
        {
        case RIGHT:
            masterMotor = new CANTalon(CanId.DRIVE_RIGHT_MASTER.getId());
            followerMotor = new CANTalon(CanId.DRIVE_RIGHT_FOLLOWER.getId());
            frontWheel = new Wheel(CanId.STEERING_RIGHT_FRONT,SteeringOffset.RIGHT_FRONT.getOffset());
            rearWheel = new Wheel(CanId.STEERING_RIGHT_REAR,SteeringOffset.RIGHT_REAR.getOffset());
            break;
        case LEFT:
            masterMotor = new CANTalon(CanId.DRIVE_LEFT_MASTER.getId());
            followerMotor = new CANTalon(CanId.DRIVE_LEFT_FOLLOWER.getId());
            frontWheel = new Wheel(CanId.STEERING_LEFT_FRONT, SteeringOffset.LEFT_FRONT.getOffset());
            rearWheel = new Wheel(CanId.STEERING_LEFT_REAR,SteeringOffset.LEFT_REAR.getOffset());
            break;
        default:
            throw new IllegalArgumentException("Invalid side of robot");
        }
        
        masterMotor.changeControlMode(TalonControlMode.PercentVbus);
		masterMotor.enableBrakeMode(false);
		masterMotor.enable();
		
		followerMotor.changeControlMode(TalonControlMode.Follower);
		followerMotor.enableBrakeMode(false);
		followerMotor.enable();
    }
    
    public void setSpeed(double speed)
    {
//    	if(frontWheel.getPosition() > 0.25 || frontWheel.getPosition() == 0.5)
//    	{
//    		masterMotor.set(speed);
//    	}
//    	else if(frontWheel.getPosition() < 0.25 || frontWheel.getPosition() == 0)
//    	{
//    		masterMotor.set(-speed);
//    	}
//    	else
//    	{
//    		masterMotor.set(0);
//    	}
    	masterMotor.set(speed);
    	followerMotor.set(speed);
    }
    
    public void crabDrive(double angle, double speed)
    {
    	frontWheel.setPosition(angle);
    	rearWheel.setPosition(angle);
    	setSpeed(speed);
    }
    
    public void swerveDrive(double angle, double speed)
    {
//    	System.out.println(side + " angle: "+angle);
    	switch(side)
    	{
    	case LEFT:
    		frontWheel.setPosition(-angle);
    		rearWheel.setPosition(angle);
    		break;
    	case RIGHT:
    		frontWheel.setPosition(-angle);
    		rearWheel.setPosition(angle);
    		break;
    	default:
    		
    	}
    	setSpeed(speed);
    }
    
    public void spinOnAxis(double speed)
    {
    	switch(side)
    	{
    	case LEFT:
    		swerveDrive(-0.125,-speed);
    		break;
    	case RIGHT:
    		swerveDrive(0.125,speed);
    		break;
    	default:
    	}
    }

	public void printNeededOffsets()
	{
		frontWheel.printNeededOffsets();
		rearWheel.printNeededOffsets();
	}

	public void enableTurngin(boolean enable)
	{
		frontWheel.enableTurning(enable);
		rearWheel.enableTurning(enable);
	}
}
