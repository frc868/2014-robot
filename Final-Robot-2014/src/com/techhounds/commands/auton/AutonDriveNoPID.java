/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.auton;

import com.techhounds.RobotConstants;
import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.DriveSubsystem;

/**
 *
 * @author Atif Niyaz
 */
public class AutonDriveNoPID extends CommandBase {
    
    private DriveSubsystem drive;
    
    private double power;
    private double distance;
    
    public AutonDriveNoPID() {
        
        drive = DriveSubsystem.getInstance();
        requires(drive);
        
        power = RobotConstants.Auton.SPEED;
        distance = RobotConstants.Auton.DISTANCE;
    }
    
    public AutonDriveNoPID(double power, double distance) {
        this();
        this.power = power;
        this.distance = distance < 0.0 ? -distance : distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drive.resetEncoders();
        
        drive.driveArcade(power, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drive.getAvgDistance()) >= distance || timeSinceInitialized() > 3;
    }

    // Called once after isFinished returns true
    protected void end() {
        drive.driveArcade(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
