package org.usfirst.frc.team2028.robot.component;

import org.usfirst.frc.team2028.robot.Parameters.CanId;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wheel 
{
	CANTalon wheelMotor;
	double offset = 0;
	double p =9,i=0.0002,d=0.0;
	int canId =0;
	double constRev = 0;
	public Wheel(CanId canId, double argOffset)
	{
		wheelMotor = new CANTalon(canId.getId());
		wheelMotor.changeControlMode(TalonControlMode.Position);
		wheelMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		wheelMotor.setPID(p, i, d);
		wheelMotor.enable();
		offset = argOffset;
		this.canId = canId.getId();
	}
	
	public void center()
	{
		if(isPastCenter())
		{
			setPosition(0);
		}
		else
		{
			setPosition(1);
		}
	}
	
	public boolean isPastCenter()
	{
		return wheelMotor.getPosition()< getOffsetPosition(0.5);
	}
	
	public double setPosition(double pos)
	{
//		System.out.println(toString()+ " pos "+pos);
		double currentpos = wheelMotor.getPosition()-offset;
		constRev = (int)currentpos;
		double nextRev;
		double currentRev;
		double prevRev;
		
		if(currentpos < 0)
		{
			constRev-=1;
		}
		
		nextRev = constRev+1.0+pos;
		currentRev = constRev+pos;
		prevRev = constRev-1.0+pos;
		
//		System.out.println("Motor: "+canId);
//		System.out.println("Goal: "+pos);
//		System.out.println("Const: "+constRev);
//		System.out.println("Current pos"+currentpos);
//		System.out.println("Next rotg"+nextRev);
//		System.out.println("Current rotg"+currentRev);
//		System.out.println("Prev rotg"+prevRev);
//		System.out.println("-------------------------------");
//		Test
		double dnext = Math.abs(nextRev-currentpos);
		double dcurr = Math.abs(currentRev-currentpos);
		double dprev = Math.abs(prevRev-currentpos);
//		System.out.println("Next: "+dnext);
//		System.out.println("Curr: "+dcurr);
//		SmartDashboard.putNumber("Curr", wheelMotor.getOutputCurrent());
//		System.out.println("Prev: "+dprev);
		if(dnext < dcurr && dnext < dprev)
			pos = nextRev;
		else if(dcurr < dnext && dcurr < dprev)
			pos = currentRev;
		else if(dprev < dcurr && dprev < dnext)
			pos = prevRev;
//		double d0 = Math.abs((wheelMotor.getPosition()-offset)-constRot);
//		double d1 = Math.abs((wheelMotor.getPosition()-offset)-(constRot+1));
		
		//		if(d1 < d0)
//		{
//			System.out.println("here");
//			constRot++;
//		}
    	pos +=offset;
    	
//	    	System.out.println("Offset: "+pos);
    	pos *= Math.pow(10, 3);
//	    	System.out.println("AM: "+pos);
    	pos = (int)pos;
//	    	System.out.println("Int: "+pos);
    	pos /= Math.pow(10, 3);
//	    	System.out.println("Div: "+pos);
    	
    	wheelMotor.set(pos);
    	return pos;
//	    wheelMotor.setPosition(pos);
//	   	{
//	   	if(wheelMotor != null)
//	   		wheelMotor.setPosition(0);
//	  		else
//   			return pos;
//		}
			
	}
	
	public void disableControl()
	{
		wheelMotor.disableControl();
	}
	
	public double getOffsetPosition(double pos)
	{
		pos += offset;
    	pos *= Math.pow(10, 3);
    	pos = (int)pos;
    	pos /= Math.pow(10, 3);
    	return pos;
	}
	
	public boolean atSetpoint()
    {
//    	System.out.println(Math.abs(wheelMotor.getPosition()-wheelMotor.getSetpoint()));
    	if(Math.abs(wheelMotor.getPosition()-wheelMotor.getSetpoint()) <= 0.01)
    		return true;
    	return false;
    }
	
	public double getPosition()
	{
		return wheelMotor.getPosition()+offset;
	}
	
	public double getPositionWithoutOffset()
	{
		return wheelMotor.getPosition();
	}
	
	public String toString()
	{
		String rc = "";
		rc += canId+ ": "+getPosition();
		return rc;
	}

	public void printNeededOffsets() 
	{
//		wheelMotor.setPosition(0.5);
		double num = wheelMotor.getPosition()-0.5;
		System.out.println(canId + " offset needed " + num );
		System.out.println("\t "+getPosition());
	}
	
	@SuppressWarnings("null")
	public void enableTurning(boolean enable)
	{
		if(enable)
		{
			wheelMotor.enableControl();
		}
		else
		{
			System.out.println("Disabled "+canId);
			wheelMotor.disableControl();
		}
		double[] leftPos = null;
		double[] rightPos = null;
		double angle = 0;
		int speed = 0;
		SmartDashboard.putNumber("Front Left", leftPos[0]);
		SmartDashboard.putNumber("Rear Left", leftPos[1]);

		SmartDashboard.putNumber("Front Right", rightPos[0]);
		SmartDashboard.putNumber("Rear Right", rightPos[1]);
		
		leftPos[0] -= (int)leftPos[0]; if(leftPos[0] <0)leftPos[0]+=1;
		leftPos[1] -= (int)leftPos[1]; if(leftPos[1] <0)leftPos[1]+=1;
		rightPos[0] -= (int)rightPos[0]; if(rightPos[0] <0)rightPos[0]+=1;
		rightPos[1] -= (int)rightPos[1]; if(rightPos[1] <0)rightPos[1]+=1;
		
		double sumCurAngle =0;

		sumCurAngle += angle-leftPos[0];
		sumCurAngle += angle-leftPos[1];
		sumCurAngle += angle-rightPos[0];
		sumCurAngle += angle-rightPos[1];

		double sumPrevHalfAngle = 0;
		double prevHalfAngle = angle-0.5;
		double sumNextHalfAngle = 0;
		double nextHalfAngle = angle+0.5;
		
		sumCurAngle = 0 + Math.abs(angle-leftPos[0]);
		sumCurAngle += Math.abs(angle-leftPos[1]);
		sumCurAngle += Math.abs(angle-rightPos[0]);
		sumCurAngle += Math.abs(angle-rightPos[1]);
		
		sumNextHalfAngle += Math.abs(nextHalfAngle-leftPos[0]);
		sumNextHalfAngle += Math.abs(nextHalfAngle-leftPos[1]);
		sumNextHalfAngle += Math.abs(nextHalfAngle-rightPos[0]);
		sumNextHalfAngle += Math.abs(nextHalfAngle-rightPos[1]);
	
		sumPrevHalfAngle += Math.abs(prevHalfAngle-leftPos[0]);
		sumPrevHalfAngle += Math.abs(prevHalfAngle-leftPos[1]);
		sumPrevHalfAngle += Math.abs(prevHalfAngle-rightPos[0]);
		sumPrevHalfAngle += Math.abs(prevHalfAngle-rightPos[1]);
		
		System.out.println("Next: "+nextHalfAngle);
		System.out.println("Sum: "+sumNextHalfAngle);
		System.out.println("Prev: "+prevHalfAngle);
		System.out.println("Sum: "+sumPrevHalfAngle);
		System.out.println("Curr: "+angle);
		

		if(sumNextHalfAngle < sumCurAngle && sumNextHalfAngle < sumPrevHalfAngle)
		{
			System.out.println("going to next");
			angle = nextHalfAngle;
			speed = -speed;
		}	
		else if(sumPrevHalfAngle < sumCurAngle && sumPrevHalfAngle < sumNextHalfAngle)
		{
			System.out.println("going to prev");

			angle = prevHalfAngle;
			speed = -speed;
		}
	
		if(angle >= 1)
		{
			angle-=1;
		}
//		else if(angle < 0)
//		{
//			angle +=1;
//		}
//		else if(angle )
//			angle -= 0.001;
//		if()
		System.out.println("Crabbing at: "+angle+"/"+speed);
		if(angle >= 0.5)
		{
			speed = -speed;
		}
		else
		{
			
		}
	}
}