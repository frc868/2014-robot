/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.pneumatics;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.CollectorSubsystem;

/**
 *
 * @author Evan
 */
public class SetCollector extends CommandBase {
    
    private CollectorSubsystem collect;
    
    private boolean position;
    private boolean toggling;
    
    public SetCollector() {
        collect = CollectorSubsystem.getInstance();
        requires(collect);
        toggling = true;
    }
    
    public SetCollector(boolean position){
        this();
        this.position = position;
        toggling = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if (toggling)
            position = !collect.getCurrentPosition();
        collect.setPosition(position);
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
