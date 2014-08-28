/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.shooter;

import com.techhounds.RobotConstants;
import com.techhounds.commands.kicker.SetKickerWheels;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author TechHOUNDS
 */
public class OperatorFire extends CommandGroup {
    
    public OperatorFire(double time) {
        addSequential(new Fire());
        addSequential(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        addSequential(new WaitCommand(time));
        addSequential(new StopFire());
    }
}
