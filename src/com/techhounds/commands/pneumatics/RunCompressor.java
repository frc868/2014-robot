/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.pneumatics;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.CompressorSubsystem;

/**
 * @author Atif Niyaz
 */
public class RunCompressor extends CommandBase {
    
    private CompressorSubsystem compressor;
    
    public RunCompressor() {
        compressor = CompressorSubsystem.getInstance();
        requires(compressor);
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       if(compressor.isMaxPressure() 
               //|| CompressorSubsystem.getIsDrivingFast() 
               || CompressorSubsystem.getIsShooting())
           compressor.stop();
       else
           compressor.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { return false; }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
