/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.shooter;

import com.techhounds.RobotConstants;
import com.techhounds.commands.kicker.SetKickerWheels;
import com.techhounds.commands.pneumatics.ForceCompressorState;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Atif Niyaz
 */
public class Fire extends CommandGroup {
    
    public Fire() {
        
        addSequential(new LockShooterPower(true));
        addSequential(new ForceCompressorState(true));
        addSequential(new SetStoppers(StopperSubsystem.IN));
    }   
}
