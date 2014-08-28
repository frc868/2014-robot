/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.shooter;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.ShooterSubsystem;

/**
 *
 * @author Atif_2
 */
public class WaitForShooter extends CommandBase {
    
    ShooterSubsystem frontRight;
    ShooterSubsystem frontLeft;
    ShooterSubsystem backRight;
    ShooterSubsystem backLeft;
    
    public WaitForShooter() {
        frontRight = ShooterSubsystem.getRightFront();
        frontLeft = ShooterSubsystem.getLeftFront();
        backRight = ShooterSubsystem.getRightRear();
        backLeft = ShooterSubsystem.getLeftRear();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (frontRight.onTarget() && frontLeft.onTarget() && backRight.onTarget() && backLeft.onTarget())
                || timeSinceInitialized() >= 2;
    }

    // Called once after isFinished returns true
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
