package com.techhounds.subsystems;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.techhounds.Robot;
import com.techhounds.RobotMap;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Evan
 */
public class KickerSubsystem extends Subsystem {
    
    private static KickerSubsystem instance;
    
    private SpeedController motor;
    
    private KickerSubsystem(int port){
        motor = Robot.createSpeedController(port, "Kicker Subsystem", "Kicker Motor");
    }
    
    public static KickerSubsystem getInstance(){
        if (instance == null){
            instance = new KickerSubsystem(RobotMap.Kicker.KICKER);
        }
        return instance;
    }
    
    public double getMotor() {
        return motor.get();
    }

    public void setMotor(double power){
        
        if (power > 1)
            power = 1;
        if (power < -1)
            power = -1;
        
        motor.set(power);
    }
    
    public void startMotor(double power) {
        
        setMotor(power);
    }
    
    public void stopMotor(){
        motor.set(0);
    }
    
    public void updateSmartDashboard() {
        
        SmartDashboard.putBoolean("Kicker Wheels In", getMotor() > 0);
        SmartDashboard.putBoolean("Kicker Wheels Out", getMotor() < 0);

    }
    
    public void initDefaultCommand() {

    }
}
