package org.usfirst.frc.team2028.robot.component;

import org.usfirst.frc.team2028.robot.Parameters.CanId;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class Wheel 
{
	/**
	 * Gets CanTalon Info
	 */
	private CANTalon wheelMotor;
	
	/**
	 * Gets the individual offsets
	 */
	private double offset = 0;
	
	/**
	 * declares the f
	 */
	private double f = 0;
	
	/**
	 * declares the p
	 */
	private double p = 9;
	
	/**
	 * declares the i
	 */
	private double i = 0.0002;
	
	/**
	 * declares the d
	 */
	private double d = 0.0;
	
	/**
	 * allows to access the identity of the current canid
	 */
	private int canId = 0;
	
	/**
	 * does nothing currently
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
	 * @param pos - This parameter is used for determining the new setpoint to go to.
	 * 
	 * @return The new steering setpoint.
	 */
	public double setPosition(double pos)					
	{			
		double startingpos = getPosition();					
		double distance = 0;	
		
    	startingpos = roundOffToFourDigits(startingpos);
    	pos = roundOffToFourDigits(pos);
    	
    	System.out.println("pos" + pos);
    	System.out.println("startingpos" + startingpos);
    	
    			if (startingpos > pos)
    			{
    				distance = startingpos - pos;						
    			}
    			else if (startingpos < pos)
    			{
    				distance = pos - startingpos;
    			}
    			else
    			{
    				distance = 0;}
    			
    			if (distance >= 1)
    			{
    				int distance2 = (int) distance;
    				distance = distance - distance2;
    			}
    			
    			if(distance > 0.5)
    			{
    				pos = startingpos - (1 - distance);
    			}
    			else if (distance <= 0.5 && startingpos > 1)
    			{
    				startingpos -= distance;
    			}
    			else if (distance <= 0.5)
    			{
    				startingpos += distance;
    			}
    			
    			System.out.println ("pos" + pos);
    	    	System.out.println ("startingpos" + startingpos);
    			
    	pos += offset;	
    	pos = roundOffToFourDigits(pos);
    	wheelMotor.set(pos);								
    	return pos;	
    	//current is that 1=-1
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