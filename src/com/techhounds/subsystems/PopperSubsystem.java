/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.subsystems;

import com.techhounds.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Atif Niyaz
 */
public class PopperSubsystem extends Subsystem {
   
    private static PopperSubsystem instance;
    
    public static boolean IN = false;
    public static boolean OUT = true;
    
    private Solenoid sole;
    
    private PopperSubsystem() {
        
        sole = new Solenoid(RobotMap.Pneumatics.POPPER);
    }
    
    public static PopperSubsystem getInstance() {
        if(instance == null)
            instance = new PopperSubsystem();
        return instance;
    }
    
    public void setPopperPosition(boolean place) {
        sole.set(place);
    }
    
    public boolean getPopperPosition() {
        return sole.get();
    }
    
    public void updateSmartDashboard() {
        
        // Update Position of Popper
        SmartDashboard.putBoolean("Popper In", !getPopperPosition());
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
