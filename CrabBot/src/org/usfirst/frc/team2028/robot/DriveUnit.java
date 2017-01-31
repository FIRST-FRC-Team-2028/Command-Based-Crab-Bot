package org.usfirst.frc.team2028.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class DriveUnit
{
	int frontCanId=0,rearCanId =0;
	CANTalon frontMotor,rearMotor;
	public DriveUnit(int frontID,int rearID)
	{
		frontMotor = new CANTalon(frontID);
		frontMotor.changeControlMode(TalonControlMode.PercentVbus);
		frontMotor.enableBrakeMode(false);
		frontMotor.enable();
		rearMotor = new CANTalon(rearID);
		rearMotor.changeControlMode(TalonControlMode.PercentVbus);
		rearMotor.enableBrakeMode(false);
		rearMotor.enable();
	}
	
	public void set(double volts)
	{
		frontMotor.set(volts);
		rearMotor.set(volts);
	}
}
