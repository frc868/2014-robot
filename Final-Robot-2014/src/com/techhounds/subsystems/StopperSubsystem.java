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
public class StopperSubsystem extends Subsystem {
    
    public static StopperSubsystem instance;
    
    public Solenoid stop;
    
    public static boolean IN = true;
    public static boolean OUT = false;
    
    private StopperSubsystem () {
        stop = new Solenoid(RobotMap.Pneumatics.STOPPER);
    }
    
    public void setStopperPosition(boolean position) {
        stop.set(position);
    }
    
    public boolean getStopperPosition() {
        return stop.get();
    }
    
    public static StopperSubsystem getInstance() {
        if(instance == null)
            instance = new StopperSubsystem();
        return instance;
    }
    
    public void updateSmartDashboard() {
        
        // Update the Position of the Stopper
        SmartDashboard.putBoolean("Stopper Out", !getStopperPosition());
    }
    
    public void initDefaultCommand() {
        
    }
}
