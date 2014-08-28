/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techhounds.subsystems;

import com.techhounds.RobotMap;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Atif Niyaz
 */
public class GyroSubsystem extends Subsystem implements PIDSource {
    
    private static GyroSubsystem instance;
    private Gyro gyro;
    
    private GyroSubsystem() {
        super("Gyro Subsystem");
        gyro = new Gyro(RobotMap.Gyro.GYRO);
    }
    
    public static GyroSubsystem getInstance() {
        if(instance == null)
            instance = new GyroSubsystem();
        return instance;
    }
    
    /**
     * Get the current heading in degrees.
     * 
     * @return The angle read from the Gyro normalized to the range of [0, 360].
     */
    public double getAngle() {
        double angle = getRawAngle() > 0 ? getRawAngle() % 360 : getRawAngle() % 360 + 360;
        
        if(angle == 360)
            angle = 0;
        
        return angle;
    }
    
    public double getRawAngle() {
        return gyro.getAngle();
    }
    
    public void gyroReset() {
        gyro.reset();
    }
    
    public void updateSmartDashboard() {
        
        // Gyrometer
        SmartDashboard.putData("Gyrometer", gyro);
        
        // Gyro Angle
        SmartDashboard.putNumber("Gyro Angle", getAngle());
    }
    
    public double pidGet() {
        return getRawAngle();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
