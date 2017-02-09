package org.usfirst.frc.team2028.robot.component;

import org.usfirst.frc.team2028.robot.Parameters.CanId;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class Wheel 
{
	/**
	 * 
	 */
	private CANTalon wheelMotor;
	
	/**
	 * 
	 */
	private double offset = 0;
	
	/**
	 * 
	 */
	private double f = 0;
	
	/**
	 * 
	 */
	private double p = 9;
	
	/**
	 * 
	 */
	private double i = 0.0002;
	
	/**
	 * 
	 */
	private double d = 0.0;
	
	/**
	 * 
	 */
	private int canId = 0;
	
	/**
	 * 
	 */
	private double constRev = 0;
	
	/**
	 * Constructor
	 * 
	 * @param canId
	 * @param argOffset
	 */
	public Wheel(CanId canId, double argOffset)
	{
		wheelMotor = new CANTalon(canId.getId());
		wheelMotor.changeControlMode(TalonControlMode.Position);
		wheelMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		wheelMotor.setF(f);
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
		return wheelMotor.getPosition() < getOffsetPosition(0.5);
	}
	
	/**
	 * Code determines shortest distance and has the robot go that way
	 * 
	 * @param pos - This parameter is used for
	 * 
	 * @return The new steering setpoint.
	 */
	public double setPosition(double pos)					
	{
		//begining of test code 
		double startingpos = getPosition();					
		double distance=0;
		
		if(startingpos<0)
		{
			distance=Math.abs(startingpos)+pos;
		}
		else if(startingpos>1)
		{
			distance=startingpos+pos;
		}
		else if(startingpos>pos)
		{
			distance=startingpos-pos;						
		}
		else if(startingpos<pos)
		{
			distance=pos-startingpos;
		}
		else
		{
			distance=.5;
		}
		
		
		if(startingpos<0)
		{
			pos=distance-1;
		}
		else if (startingpos>1)
		{
			pos=distance;
		}
		else if (Math.abs(distance)<0.5)
		{  
			pos=startingpos+distance;
		}
		else if(Math.abs(distance)>0.5&&startingpos<pos)
		{
			pos=startingpos-(1.0-distance);					
		}
		else if(Math.abs(distance)>0.5&&startingpos>pos)
		{
			pos=startingpos+(1.0-distance);				
		}
		else
		{
			pos=startingpos+distance;
		}
		
		//end of test code
		//problem 1 back wheels spin consistantly
		
    	pos += offset;										
    	startingpos = roundOffToFourDigits(startingpos);
    	wheelMotor.set(startingpos);								
    	return pos;		
	}
	
	public void disableControl()
	{
		wheelMotor.disableControl();
	}
	
	public double getOffsetPosition(double pos)
	{
		pos += offset;
		return roundOffToFourDigits(pos);
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
			System.out.println("Disabled "+canId);
			wheelMotor.disableControl();
		}
	}
	
	private double roundOffToFourDigits(double pos) {
    	pos *= 10000.0;
    	pos = (int)pos;
    	pos /= 10000.0;
    	return pos;
	}
}
