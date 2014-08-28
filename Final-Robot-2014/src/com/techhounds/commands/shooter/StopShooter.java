/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.techhounds.commands.shooter;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.ShooterSubsystem;

/**
 * @author Calvin 
 */
public class StopShooter extends CommandBase {
    private final ShooterSubsystem frontRight;
    private final ShooterSubsystem frontLeft;
    private final ShooterSubsystem backRight;
    private final ShooterSubsystem backLeft;
    
    public StopShooter(){
       
        frontRight = ShooterSubsystem.getRightFront();
        frontLeft = ShooterSubsystem.getLeftFront();
        backRight = ShooterSubsystem.getRightRear();
        backLeft = ShooterSubsystem.getLeftRear();
        
        requires(frontRight);
        requires(frontLeft);
        requires(backRight);
        requires(backLeft);
    }

    protected void initialize() {
        frontRight.stop();
        frontLeft.stop();
        backRight.stop();
        backLeft.stop();
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
}
