/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.auton.options;

import com.techhounds.RobotConstants;
import com.techhounds.commands.auton.AutonDriveNoPID;
import com.techhounds.commands.auton.CheesyVisionDelay;
import com.techhounds.commands.driving.RotateToAngle;
import com.techhounds.commands.kicker.SetKickerWheels;
import com.techhounds.commands.pneumatics.CollectorSequence;
import com.techhounds.commands.pneumatics.SetCollector;
import com.techhounds.commands.pneumatics.SetPopper;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.FireReady;
import com.techhounds.commands.shooter.StopShooter;
import com.techhounds.commands.shooter.WaitForShooter;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.GyroSubsystem;
import com.techhounds.subsystems.PopperSubsystem;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 * @author Atif_2
 */
public class TwoBallAutonCheezy extends CommandGroup {
    
    public TwoBallAutonCheezy() {
        
        double initialAngle = GyroSubsystem.getInstance().getAngle();
        
        addParallel(new MoveIntoZone());                    
        addParallel(new FireReady());        
        addParallel(new WaitForShooter());                  
        
        addSequential(new WaitForChildren());              
        addSequential(new CheesyVisionDelay(RobotConstants.Auton.CHEEZY_TIMEOUT_TWO_BALL)); // Cheezy Vision
        
        addParallel(new Fire());                            
        addParallel(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED, 0.25, true)); 
        
        addSequential(new WaitForChildren());
        
        addParallel(new AutonDriveNoPID(1, -10));                
        addParallel(new SetCollector(CollectorSubsystem.COLLECTING));
        addParallel(new SetStoppers(StopperSubsystem.OUT));
        addParallel(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        
        addSequential(new WaitForChildren());
        
        addParallel(new SetKickerWheels(RobotConstants.Kicker.STOP));
        addParallel(new AutonDriveNoPID(-1, 10.5)); 
        addParallel(new CollectorSequence());
        
        addSequential(new WaitForChildren());
        addSequential(new CheesyVisionDelay(RobotConstants.Auton.CHEEZY_TIMEOUT_TWO_BALL)); // Cheezy Vision
        
        addParallel(new Fire());
        addParallel(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED, 0.25));
        
        addSequential(new WaitForChildren());
        
        addParallel(new StopShooter());
        addParallel(new SetPopper(PopperSubsystem.IN));
        addParallel(new SetStoppers(StopperSubsystem.OUT));
        
        
    }
}
