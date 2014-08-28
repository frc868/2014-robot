/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.pneumatics;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.PopperSubsystem;

/**
 * @author Atif Niyaz
 */
public class SetPopperOnly extends CommandBase {
    
    private PopperSubsystem pop;
    private boolean position;
    private boolean toggling;
    
    public SetPopperOnly() {
        pop = PopperSubsystem.getInstance();
        requires(pop);
        toggling = true;
    }
    
    public SetPopperOnly(boolean position) {
        this();
        this.position = position;
        toggling = false;
    }

    protected void initialize() {
        if(toggling)
            position = !pop.getPopperPosition();
    }

    protected void execute() {
        pop.setPopperPosition(position);
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
