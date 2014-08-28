/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.commands.auton;

import cheesy.CheesyVisionServer;
import com.techhounds.Robot;
import com.techhounds.RobotConstants;
import com.techhounds.commands.CommandBase;

/**
 *
 * @author Andrew
 */

public class CheesyVisionDelay extends CommandBase {
    
   CheesyVisionServer server;

   public static final int ANY_SIDE = 0;        
   public static final int RIGHT_SIDE = 1;        
   public static final int LEFT_SIDE = 2;        
   public static final int BOTH_SIDES = 3;
   public static final int NO_SIDES = 4;
   
   private int side;

   private static boolean left = false;
   private static boolean right = false;
   
   private double timeout;
   
    public CheesyVisionDelay() {
        this(ANY_SIDE, RobotConstants.Auton.CHEEZY_TIMEOUT_ONE_BALL);
    }
    
    public CheesyVisionDelay(int side) {
        this(side, RobotConstants.Auton.CHEEZY_TIMEOUT_ONE_BALL);
    }   
    
    public CheesyVisionDelay(double timeout) {
        this(ANY_SIDE, timeout);
    }
   
    public CheesyVisionDelay(int side, double timeout) {
        
        this.side = side;
        server = Robot.getCheesyServerInstance();
        this.timeout = timeout;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        left = server.getLeftStatus();
        right = server.getRightStatus();
        
        if(timeSinceInitialized() >= timeout)
            return true;
        
        if (side == ANY_SIDE) {           
            return right || left ;
        } else if (side == RIGHT_SIDE){
            return right;
        } else if (side == LEFT_SIDE){
            return left;
        } else if (side == BOTH_SIDES) {
            return left && right;
        }
        System.out.println("CHEESY VISION DELAY: SIDE VARIABLE IS NOT " 
                         + "CORRECTLY ASSIGNED: SIDE = " + side);
        return false || timeSinceInitialized() >= timeout;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    /**
     * Gets the last side that was detected when the command was running 
     */    
    public static int getLastSide() {
        if (right && left) {
            return BOTH_SIDES;
        } else if (right) {
            return RIGHT_SIDE;
        } else if (left) {
            return LEFT_SIDE;
        } return NO_SIDES;
    }
}
