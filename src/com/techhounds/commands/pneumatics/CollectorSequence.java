/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.pneumatics;

import com.techhounds.RobotConstants;
import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.PopperSubsystem;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Atif Niyaz
 */
public class CollectorSequence extends CommandBase {
    
    private double initTime;
    private boolean finished = false;
    
    private static double popperDelay = RobotConstants.Pneumatics.POPPER_DELAY;
    private static double armDelay = RobotConstants.Pneumatics.COLLECTOR_DELAY;
    
    private CollectorSubsystem collect;
    private PopperSubsystem popper;
    
    public CollectorSequence() {
        collect = CollectorSubsystem.getInstance();
        popper = PopperSubsystem.getInstance();
       
        requires(collect);
        requires(popper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        finished = false;
        initTime = Timer.getFPGATimestamp();
        
        if(popper.getPopperPosition() == PopperSubsystem.OUT && collect.getCurrentPosition() == CollectorSubsystem.UP)
            popper.setPopperPosition(PopperSubsystem.IN);

        
        collect.setPosition(CollectorSubsystem.COLLECTING);
    }

    protected void execute() {
        
        if (Timer.getFPGATimestamp() >= initTime + armDelay + popperDelay){
            collect.setPosition(CollectorSubsystem.UP);
            finished = true;
        }else if (Timer.getFPGATimestamp() >= initTime + popperDelay){
            popper.setPopperPosition(PopperSubsystem.OUT);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
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
