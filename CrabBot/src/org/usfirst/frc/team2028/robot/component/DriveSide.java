package org.usfirst.frc.team2028.robot.component;

import org.usfirst.frc.team2028.robot.Parameters.CanId;
import org.usfirst.frc.team2028.robot.Parameters.SideOfRobot;
import org.usfirst.frc.team2028.robot.Parameters.SteeringOffset;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSide {
    private Wheel frontWheel;

    private Wheel rearWheel;

    private CANTalon masterMotor;

    private CANTalon followerMotor;
    
    private CANTalon masterMotorm;
    
    private CANTalon followerMotorm;

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
//    		SmartDashboard.putNumber("CurrMR", masterMotor.getOutputCurrent());
//    		SmartDashboard.putNumber("CurrFR", followerMotor.getOutputCurrent());
            break;
            
        case LEFT:
            masterMotor = new CANTalon(CanId.DRIVE_LEFT_MASTER.getId());
            followerMotor = new CANTalon(CanId.DRIVE_LEFT_FOLLOWER.getId());
            frontWheel = new Wheel(CanId.STEERING_LEFT_FRONT, SteeringOffset.LEFT_FRONT.getOffset());
            rearWheel = new Wheel(CanId.STEERING_LEFT_REAR,SteeringOffset.LEFT_REAR.getOffset());
//    		SmartDashboard.putNumber("CurrML", masterMotor.getOutputCurrent());
//    		SmartDashboard.putNumber("CurrFL", followerMotor.getOutputCurrent());
            break;
        default:
            throw new IllegalArgumentException("Invalid side of robot");
        }
        
        masterMotor.changeControlMode(TalonControlMode.PercentVbus);
		masterMotor.enableBrakeMode(false);
		masterMotor.enable();
		
		followerMotor.changeControlMode(TalonControlMode.PercentVbus);
		followerMotor.enableBrakeMode(false);
		followerMotor.enable();
    }
    
    public void setSpeed(double speed)
    {
    	masterMotor.set(speed);
    	followerMotor.set(speed);
		SmartDashboard.putNumber("CurrM", masterMotor.getOutputCurrent());
		SmartDashboard.putNumber("CurrF", followerMotor.getOutputCurrent());
    }
    
    public void crabDrive(double angle, double speed)
    {
    	frontWheel.setPosition(angle);
    	rearWheel.setPosition(angle);
    	setSpeed(speed);
////		//    	Beginning of bad test code
//////    	if(angle==0)
//////    	{
//////    		speed*=-1;
//////    		angle=0.5;
//////    	}
//////    	End of bad test code
//    		double[] leftPos = null;
//    		double[] rightPos = null;
//    		SmartDashboard.putNumber("Front Left", leftPos[0]);
//    		SmartDashboard.putNumber("Rear Left", leftPos[1]);
//    		SmartDashboard.putNumber("Front Right", rightPos[0]);
//    		SmartDashboard.putNumber("Rear Right", rightPos[1]);
//    		
//    		leftPos[0] -= (int)leftPos[0]; if(leftPos[0] <0)leftPos[0]+=1;
//    		leftPos[1] -= (int)leftPos[1]; if(leftPos[1] <0)leftPos[1]+=1;
//    		rightPos[0] -= (int)rightPos[0]; if(rightPos[0] <0)rightPos[0]+=1;
//    		rightPos[1] -= (int)rightPos[1]; if(rightPos[1] <0)rightPos[1]+=1;
//    		
//    		double sumCurAngle =0;
//    		sumCurAngle += angle-leftPos[0];
//    		sumCurAngle += angle-leftPos[1];
//    		sumCurAngle += angle-rightPos[0];
//    		sumCurAngle += angle-rightPos[1];
//
//    		double sumPrevHalfAngle = 0;
//    		double prevHalfAngle = angle-0.5;
//    		double sumNextHalfAngle = 0;
//    		double nextHalfAngle = angle+0.5;
//    		
//    		sumCurAngle = 0 + Math.abs(angle-leftPos[0]);
//    		sumCurAngle += Math.abs(angle-leftPos[1]);
//    		sumCurAngle += Math.abs(angle-rightPos[0]);
//    		sumCurAngle += Math.abs(angle-rightPos[1]);
//    		
//    		sumNextHalfAngle += Math.abs(nextHalfAngle-leftPos[0]);
//    		sumNextHalfAngle += Math.abs(nextHalfAngle-leftPos[1]);
//    		sumNextHalfAngle += Math.abs(nextHalfAngle-rightPos[0]);
//    		sumNextHalfAngle += Math.abs(nextHalfAngle-rightPos[1]);
//		
//    		sumPrevHalfAngle += Math.abs(prevHalfAngle-leftPos[0]);
//    		sumPrevHalfAngle += Math.abs(prevHalfAngle-leftPos[1]);
//    		sumPrevHalfAngle += Math.abs(prevHalfAngle-rightPos[0]);
//    		sumPrevHalfAngle += Math.abs(prevHalfAngle-rightPos[1]);
//    		
//    		System.out.println("Next: "+nextHalfAngle);
//    		System.out.println("Sum: "+sumNextHalfAngle);
//    		System.out.println("Prev: "+prevHalfAngle);
//    		System.out.println("Sum: "+sumPrevHalfAngle);
//    		System.out.println("Curr: "+angle);
//    		
//    		if(sumNextHalfAngle < sumCurAngle && sumNextHalfAngle < sumPrevHalfAngle)
//    		{
//    			System.out.println("going to next");
//    			angle = nextHalfAngle;
//    			speed = -speed;
//    		}	
//    		else if(sumPrevHalfAngle < sumCurAngle && sumPrevHalfAngle < sumNextHalfAngle)
//    		{
//    			System.out.println("going to prev");
//
//    			angle = prevHalfAngle;
//    			speed = -speed;
//    		}
//		
//    		if(angle >= 1)
//    		{
//    			angle-=1;
//    		}
////    		else if(angle < 0)
////    		{
////    			angle +=1;
////    		}
////    		else if(angle )
////    			angle -= 0.001;
////    		if()
//    		System.out.println("Crabbing at: "+angle+"/"+speed);
	}
    	
    
    
    public void swerveDrive(double angle, double speed)
    {
    	System.out.println(side + " angle: "+angle);
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
