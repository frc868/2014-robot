/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.auton.options;

import com.techhounds.RobotConstants;
import com.techhounds.commands.auton.AutonDriveNoPID;
import com.techhounds.commands.auton.CheesyVisionDelay;
import com.techhounds.commands.driving.DisableDriveWithGyro;
import com.techhounds.commands.driving.DriveWithEncoderAndGyro;
import com.techhounds.commands.driving.DriveWithGyro;
import com.techhounds.commands.kicker.SetKickerWheels;
import com.techhounds.commands.pneumatics.SetPopper;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.FireReady;
import com.techhounds.commands.shooter.StopShooter;
import com.techhounds.commands.shooter.WaitForShooter;
import com.techhounds.subsystems.PopperSubsystem;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 * @author Atif Niyaz and Andrew
 */
public class OneBallAutonCheezy extends CommandGroup {
    
    public OneBallAutonCheezy() {
        
        addParallel(new FireReady());
        addParallel(new DriveWithGyro());
        addParallel(new DriveWithEncoderAndGyro(RobotConstants.Auton.DISTANCE));
        //addParallel(new FireReady());
        addParallel(new CheesyVisionDelay());
        addParallel(new WaitForShooter());
        addSequential(new WaitForChildren());
        addSequential(new Fire());
        addSequential(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED, RobotConstants.Auton.WAIT_AFTER_FIRE, true));
        addSequential(new StopShooter());
        addParallel(new SetPopper(PopperSubsystem.IN));
        addParallel(new SetStoppers(StopperSubsystem.OUT));
        addSequential(new DisableDriveWithGyro());
    }
}
