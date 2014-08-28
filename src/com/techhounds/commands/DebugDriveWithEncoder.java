/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands;

import com.techhounds.RobotConstants;
import com.techhounds.commands.driving.DriveWithEncoder;
import com.techhounds.commands.driving.DriveWithEncoderAndGyro;
import com.techhounds.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author TechHOUNDS
 */
public class DebugDriveWithEncoder extends CommandGroup {
    
    public DebugDriveWithEncoder() {
        
        addSequential(new DriveWithEncoderAndGyro(RobotConstants.Auton.DISTANCE));
        addSequential(new DriveWithEncoderAndGyro(-RobotConstants.Auton.DISTANCE));
        addSequential(new DriveWithEncoderAndGyro(RobotConstants.Auton.DISTANCE));
        addSequential(new DriveWithEncoderAndGyro(-RobotConstants.Auton.DISTANCE));
        
    }
}
