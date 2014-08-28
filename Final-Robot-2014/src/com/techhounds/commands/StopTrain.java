/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands;

import com.techhounds.RobotConstants;
import com.techhounds.commands.kicker.SetKickerWheels;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.commands.shooter.StopShooter;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author TechHOUNDS
 */
public class StopTrain extends CommandGroup {
    
    public StopTrain() {
        addSequential(new SetStoppers(StopperSubsystem.OUT));
        addSequential(new StopShooter());
        addSequential(new SetKickerWheels(RobotConstants.Kicker.STOP));
    }
}
