/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.shooter;

import com.techhounds.RobotConstants;
import com.techhounds.commands.kicker.SetKickerWheels;
import com.techhounds.commands.pneumatics.ForceCompressorState;
import com.techhounds.commands.pneumatics.SetPopper;
import com.techhounds.commands.pneumatics.SetPopperOnly;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.subsystems.PopperSubsystem;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Atif Niyaz
 */
public class StopFire extends CommandGroup {
    
    public StopFire() {
        
        addParallel(new StopShooter());
        addParallel(new SetKickerWheels(RobotConstants.Kicker.STOP));
        addParallel(new SetPopperOnly(PopperSubsystem.IN));
        addParallel(new SetStoppers(StopperSubsystem.OUT));
        addSequential(new LockShooterPower(false));
        addSequential(new ForceCompressorState(false));
    }
}
