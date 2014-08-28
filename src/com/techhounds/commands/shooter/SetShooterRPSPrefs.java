/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.shooter;

import com.techhounds.RobotConstants;
import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.ShooterSubsystem;

/**
 * @author Andrew Bass
 */
public class SetShooterRPSPrefs extends CommandBase {
    
    private ShooterSubsystem frontLeftShooter;
    private ShooterSubsystem frontRightShooter;
    private ShooterSubsystem backLeftShooter;
    private ShooterSubsystem backRightShooter;  
    
    private double frontRPS, rearRPS;
    private boolean isAdjustment;
    
    public SetShooterRPSPrefs() {
        this(RobotConstants.Shooting.FRONT_RPS, RobotConstants.Shooting.BACK_RPS, false);
    }
    
    public SetShooterRPSPrefs(double frontRPS, double rearRPS, boolean isAdjustment) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.frontRPS = frontRPS;
        this.rearRPS = rearRPS;
        this.isAdjustment = isAdjustment;
        
        frontLeftShooter = ShooterSubsystem.getLeftFront();
        frontRightShooter = ShooterSubsystem.getRightFront();
        backRightShooter = ShooterSubsystem.getRightRear();
        backLeftShooter = ShooterSubsystem.getLeftRear();
        
        requires(frontLeftShooter);
        requires(frontRightShooter);
        requires(backRightShooter);
        requires(backLeftShooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        double flRPS = 0;
        double frRPS = 0;
        double blRPS = 0;
        double brRPS = 0;
        
        if (isAdjustment){
            flRPS = frontLeftShooter.getGoalRPS();
            frRPS = frontRightShooter.getGoalRPS();
            blRPS = backLeftShooter.getGoalRPS();
            brRPS = backRightShooter.getGoalRPS();
        }
        
        frontLeftShooter.setGoalRPS(frontRPS + flRPS);
        frontRightShooter.setGoalRPS(frontRPS + frRPS);
        backLeftShooter.setGoalRPS(rearRPS + blRPS);
        backRightShooter.setGoalRPS(rearRPS + brRPS);
        
        if(frontRPS + flRPS <= 0.0) {
            frontLeftShooter.stop();
        }
            
        if(frontRPS + frRPS <= 0.0) {
            frontRightShooter.stop();
        }
            
        if(rearRPS + blRPS <= 0.0) {
            backLeftShooter.stop();
        }
            
        if(rearRPS + brRPS <= 0.0) {
            backRightShooter.stop();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true; // run only once
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
