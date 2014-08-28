/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.driving;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.DriveSubsystem;

/**
 * @author Atif Niyaz
 */
public class RotateToAngle extends CommandBase {
    
    private DriveSubsystem drive;
    private double angle;
    
    public RotateToAngle(double angle) {
        drive = DriveSubsystem.getInstance();
        requires(drive);
        this.angle = angle;
    }

    protected void initialize() {
        drive.rotateToAngle(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drive.rotateInTolerance();
    }

    // Called once after isFinished returns true
    protected void end() {
        drive.disableRotateAngle();
        drive.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
