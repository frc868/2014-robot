/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.auton.options;

import com.techhounds.RobotConstants;
import com.techhounds.commands.driving.DriveWithEncoder;
import com.techhounds.commands.driving.DriveWithEncoderAndGyro;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Atif Niyaz
 */
public class MoveIntoZone extends CommandGroup {
    
    public MoveIntoZone() {
        
        addSequential(new DriveWithEncoderAndGyro(RobotConstants.Auton.DISTANCE));
    }
}
