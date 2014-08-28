/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.pneumatics;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.CompressorSubsystem;

/**
 *
 * @author Atif_2
 */
public class ForceCompressorState extends CommandBase {
    
    private boolean state;
    
    public ForceCompressorState(boolean param) {
        state = param;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        CompressorSubsystem.setIsDrivingFast(state);
        CompressorSubsystem.setIsShooting(state);
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
    }
}
