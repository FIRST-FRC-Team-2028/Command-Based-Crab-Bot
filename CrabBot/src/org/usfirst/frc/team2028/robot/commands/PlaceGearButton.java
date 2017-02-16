package org.usfirst.frc.team2028.robot.commands;

import org.usfirst.frc.team2028.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 */
public class PlaceGearButton extends Button {

    public boolean get() {
    	if(Robot.oneStick.getRawButton(12))
    	{
    		Robot.autoPlaceGear.setup();
    		return true;
    	}
        return false;
    }
}
