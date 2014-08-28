/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.driving;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;

/**
 * Drives the robot forward or backward a specific number of feet.
 * 
 * @author Atif Niyaz
 */
public class DriveWithEncoderAndGyro extends CommandBase {
    
    private final DriveSubsystem drive;
    private final double distance;
   
    /**
     * Construct an instance of the command with a specific number of feet to drive when executed.
     * 
     * @param distance How many feet to drive (positive values are forward
     * and negative values are backwards).
     */
    public DriveWithEncoderAndGyro(double distance) {
        drive = DriveSubsystem.getInstance();
        requires(drive);
        
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drive.driveWithGyro();
        drive.driveWithEncoder(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (drive.driveDistanceInTolerance() 
                //&& drive.gryoAngleInTolerance()
                ) ||
                timeSinceInitialized() >= 3;
                //|| timeSinceInitialized() >= 3;
    }

    // Called once after isFinished returns true
    protected void end() {
        drive.stopDriveWithEncoder();
        drive.disableGyro();
        drive.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
