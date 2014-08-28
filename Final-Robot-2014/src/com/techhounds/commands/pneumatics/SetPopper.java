package com.techhounds.commands.pneumatics;

import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.PopperSubsystem;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Atif Niyaz
 */
public class SetPopper extends CommandBase {
    
    private CollectorSubsystem collect;
    private PopperSubsystem popper;
    private boolean position;
    private boolean movingArm;
    private boolean toggling;
    
    private double initTime;
    
    public SetPopper() {
        collect = CollectorSubsystem.getInstance();
        popper = PopperSubsystem.getInstance();
        
        requires(collect);
        requires(popper);
        
        movingArm = false;
        toggling = true;
    }
    
    public SetPopper(boolean position) {
        this();
        this.position = position;
        toggling = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        initTime = Timer.getFPGATimestamp();
        
        // If position is in ---> false
        // If position is out ---> true
        
        if(toggling) // If we are toggling
            position = !popper.getPopperPosition();
        
        movingArm = false;
        
        if (collect.getCurrentPosition() == CollectorSubsystem.COLLECTING){ // If the collector is out
            popper.setPopperPosition(position);     // Popper can be set any position
        }else{ // If the collector is not out
            if (popper.getPopperPosition() == PopperSubsystem.OUT){ // If the popper is out
                popper.setPopperPosition(position); // Keep it out or put it in
            }else{                                                   // If the popper is in
                movingArm = true;// The colletor will move
            }
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (movingArm){ // If the collector can move
            System.out.println("--------------4");
            (new CollectorSequence()).start();
        }
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
