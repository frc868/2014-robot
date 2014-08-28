/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.kicker;

import com.techhounds.RobotConstants;
import com.techhounds.commands.CommandBase;
import com.techhounds.subsystems.KickerSubsystem;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Evan
 */
public class SetKickerWheels extends CommandBase {
    
    private KickerSubsystem kicker;
    
    private double power;
    private double delay;
    
    private boolean stopAtEnd;
    
    public SetKickerWheels(double power) {
        kicker = KickerSubsystem.getInstance();
        requires(kicker);
        this.power = power;
        delay = 0;
        stopAtEnd = false;
    }
    
    public SetKickerWheels(double power, double delay) {
        this(power);
        this.delay = delay;
        this.stopAtEnd = false;
        
    }
    
    public SetKickerWheels(double power, double delay, boolean stopAtEnd) {
        this(power, delay);
        this.stopAtEnd = stopAtEnd;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        kicker.startMotor(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() >= delay || !stopAtEnd;
    }

    // Called once after isFinished returns true
    protected void end() {
        if(stopAtEnd)
            kicker.stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
