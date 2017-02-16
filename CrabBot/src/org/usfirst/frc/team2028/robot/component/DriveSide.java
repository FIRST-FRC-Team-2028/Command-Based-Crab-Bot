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
    	int masterCanId = -1;
        switch(side)
        {
        case RIGHT:
        	masterCanId = CanId.DRIVE_RIGHT_MASTER.getId();
            masterMotor = new CANTalon(masterCanId);
            followerMotor = new CANTalon(CanId.DRIVE_RIGHT_FOLLOWER.getId());
            frontWheel = new Wheel(CanId.STEERING_RIGHT_FRONT,SteeringOffset.RIGHT_FRONT.getOffset());
            rearWheel = new Wheel(CanId.STEERING_RIGHT_REAR,SteeringOffset.RIGHT_REAR.getOffset());
            break;
        case LEFT:
        	masterCanId = CanId.DRIVE_LEFT_MASTER.getId();
            masterMotor = new CANTalon(masterCanId);
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
		followerMotor.set(masterCanId);
		followerMotor.enableBrakeMode(false);
		followerMotor.enable();
    }
    
    public void setSpeed(double speed)
    {
    	masterMotor.set(speed);
    }
    
    public void crabDrive(double angle, double speed)
    {
    	double startingpos = frontWheel.getPosition();
    	double distance = 0.0;
        if(startingpos > angle)
		{
			distance = startingpos - angle;						
		}
		else if (startingpos < angle)
		{
			distance = angle - startingpos;
		}
		else
		{
			distance = 0;}
		
		if (distance >= 1)
		{
			int distance2 = (int) distance;
			distance = distance - distance2;}
		
		if (angle == 1)
		{
			angle = 0;
		}
		else if (distance > 0.5)
		{
			angle = startingpos - (1 - distance);
		}
		else if (distance <= 0.5 && startingpos > 1)
		{
			angle = startingpos - distance;
		}
		else if (distance <= 0.5)
		{
			angle = startingpos + distance;}
		
		distance = startingpos - angle;
		
		if (distance > 0.25 || distance <- 0.25)
		{
			// Flip the angle setpoint by 180 degrees and run the motor in reverse
			if (distance < 0)
			{
				angle = angle - 0.5;
			}
			else
			{
				angle = angle + 0.5;}
			
			speed *= -1.0;
		}
		
		frontWheel.setPosition(angle);
    	rearWheel.setPosition(angle);
    	setSpeed(speed);
		
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
