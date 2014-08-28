/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands;

import com.techhounds.RobotConstants;
import com.techhounds.commands.kicker.SetKickerWheels;
import com.techhounds.commands.pneumatics.SetCollector;
import com.techhounds.commands.pneumatics.SetPopper;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.commands.shooter.SetShooterPower;
import com.techhounds.commands.shooter.SetShooterRPS;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.KickerSubsystem;
import com.techhounds.subsystems.PopperSubsystem;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author TechHOUNDS
 */
public class Train extends CommandGroup {
    
    public Train() {
        //addSequential(new SetCollector(CollectorSubsystem.UP));
        //addSequential(new SetPopper(PopperSubsystem.OUT));
        addSequential(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        addSequential(new SetStoppers(StopperSubsystem.IN));
        addSequential(new SetShooterRPS(RobotConstants.Shooting.LOW_POW_RPS_FRONT, RobotConstants.Shooting.LOW_POW_RPS_BACK, false));
    }
}
