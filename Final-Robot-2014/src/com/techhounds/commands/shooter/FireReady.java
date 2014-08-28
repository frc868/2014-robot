/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.shooter;

import com.techhounds.RobotConstants;
import com.techhounds.commands.pneumatics.CollectorSequence;
import com.techhounds.commands.pneumatics.SetPopper;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.subsystems.PopperSubsystem;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * @author Atif Niyaz
 */
public class FireReady extends CommandGroup {
    
    public FireReady() {
        
        double backRPS =  RobotConstants.Shooting.BACK_RPS;
        double frontRPS = RobotConstants.Shooting.FRONT_RPS;
        
        addSequential(new SetStoppers(StopperSubsystem.OUT));
        addSequential(new SetShooterRPS(frontRPS, backRPS, false));
        addSequential(new WaitCommand(0.5));
        addSequential(new CollectorSequence());       
        //addSequential(new SetPopper(PopperSubsystem.OUT));
        
    }
}
