package org.usfirst.frc.team2028.robot.Triggers;

import org.usfirst.frc.team2028.robot.Parameters;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrabTrigger extends Trigger {

	Joystick stick;
	Command command;
	public CrabTrigger(Joystick stick, Command command)
	{
		this.stick = stick;
		this.command = command;
		whileActive(command);
	}
	
    public boolean get() {
        return !stick.getRawButton(Parameters.Buttons.SWEARVE.getID()) && !stick.getRawButton(Parameters.Buttons.SPIN_ON_AXIS.getID());
    }
}
