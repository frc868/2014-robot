/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.driving;

import com.techhounds.Robot;
import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.DriveSubsystem;

/**
 *
 * @author Atif Niyaz
 */
public class SetDriveScale extends CommandBase {
    
    private boolean isDriver;
    private double magnitude;
    private double rotation;
    
    private DriveSubsystem drive;
    
    public static final boolean DRIVER = true;
    public static final boolean TWEAKER = false;
    
    public SetDriveScale(double magnitude, double rotation, boolean isDriver) {
        
        drive = DriveSubsystem.getInstance();
        
        this.isDriver = isDriver;
        this.magnitude = Robot.checkRange(magnitude, 0.0, 1.0);
        this.rotation = Robot.checkRange(rotation, 0.0, 1.0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        if(isDriver) {
            drive.setDriverScale(magnitude, rotation);
        } else {
            drive.setTweakerScale(magnitude, rotation);
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
