package org.usfirst.frc.team2028.robot.component;

import org.usfirst.frc.team2028.robot.Parameters.CanId;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

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
		
    	pos +=offset;
    	pos *= Math.pow(10, 3);
    	pos = (int)pos;
    	pos /= Math.pow(10, 3);    	
    	wheelMotor.set(pos);
    	return pos;		
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
		double num = wheelMotor.getPosition()-0.5;
		System.out.println(canId + " offset needed " + num );
		System.out.println("\t "+getPosition());
	}
	
	public void enableTurning(boolean enable)
	{
		if(enable)
		{
			wheelMotor.enableControl();
		}
		else
		{
			wheelMotor.disableControl();
		}
	}
}
