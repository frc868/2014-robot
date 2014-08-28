/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.techhounds.commands.shooter;

import com.techhounds.Robot;
import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.ShooterSubsystem;

/**
 * @author Calvin
 */
public class SetShooterPower extends CommandBase {
    
    private ShooterSubsystem frontLeftShooter;
    private ShooterSubsystem frontRightShooter;
    private ShooterSubsystem backLeftShooter;
    private ShooterSubsystem backRightShooter;
    
    
    private double frontPower;
    private double backPower;
    
    private boolean isAdjustment;
    
    public SetShooterPower(double frontPower, double backPower, boolean isAdjustment){
        
        this.frontPower = frontPower;
        this.backPower = backPower;
        this.isAdjustment = isAdjustment;
        
        frontLeftShooter = ShooterSubsystem.getLeftFront();
        frontRightShooter = ShooterSubsystem.getRightFront();
        backLeftShooter = ShooterSubsystem.getLeftRear();
        backRightShooter = ShooterSubsystem.getRightRear();
        
        requires(frontLeftShooter);
        requires(frontRightShooter);
        requires(backRightShooter);
        requires(backLeftShooter);  
    }
    protected void initialize() {
        
        if(isAdjustment){
            
            frontLeftShooter.setPower(Robot.checkRange(frontLeftShooter.getPower() + frontPower, 0.0, 1.0));
            frontRightShooter.setPower(Robot.checkRange(frontRightShooter.getPower() + frontPower, 0.0, 1.0));
            backLeftShooter.setPower(Robot.checkRange(backLeftShooter.getPower() + backPower, 0.0, 1.0));
            backRightShooter.setPower(Robot.checkRange(backRightShooter.getPower() + backPower, 0.0, 1.0));
            
        }else{
            
            frontLeftShooter.setPower(frontPower);
            frontRightShooter.setPower(frontPower);
            backLeftShooter.setPower(backPower);
            backRightShooter.setPower(backPower);
        }
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        end();
    }
    
}
