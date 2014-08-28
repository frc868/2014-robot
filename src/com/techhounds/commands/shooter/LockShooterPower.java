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
public class LockShooterPower extends CommandBase {
    
    private boolean state;
    
    private ShooterSubsystem frontLeft;
    private ShooterSubsystem frontRight;
    private ShooterSubsystem backLeft;
    private ShooterSubsystem backRight;
    
    public LockShooterPower(boolean param) {
        state = param;
        
        frontLeft = ShooterSubsystem.getLeftFront();
        frontRight = ShooterSubsystem.getRightFront();
        backLeft = ShooterSubsystem.getLeftRear();
        backRight = ShooterSubsystem.getRightRear();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        if(state) {
            frontLeft.lockPower();
            frontRight.lockPower();
            backLeft.lockPower();
            backRight.lockPower();
        } else {
            frontLeft.enable();
            frontRight.enable();
            backLeft.enable();
            backRight.enable();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
