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
public class TweakShooterRPS extends CommandBase {
    
    private ShooterSubsystem rightRear;
    private ShooterSubsystem rightFront;
    private ShooterSubsystem leftFront;
    private ShooterSubsystem leftRear;
    
    public TweakShooterRPS() {
        
        rightRear = ShooterSubsystem.getRightRear();
        rightFront = ShooterSubsystem.getRightFront();
        leftFront = ShooterSubsystem.getLeftFront();
        leftRear = ShooterSubsystem.getLeftRear();
        
        setInterruptible(true);
        
        requires(rightRear);
        requires(rightFront);
        requires(leftFront);
        requires(leftRear);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        rightRear.tweakShooterRPS();
        rightFront.tweakShooterRPS();
        leftFront.tweakShooterRPS();
        leftRear.tweakShooterRPS();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
