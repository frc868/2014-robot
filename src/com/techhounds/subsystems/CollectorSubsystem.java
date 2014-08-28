package com.techhounds.subsystems;

import com.techhounds.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** 
 * @author Atif Niyaz
 */
public class CollectorSubsystem extends Subsystem {
    
    private static CollectorSubsystem instance;
    
    private Solenoid sole1;
    private Solenoid sole2;
    
    public static boolean UP = true;
    public static boolean COLLECTING = false;
    
    private CollectorSubsystem() {
        super("Collector Subsystem");
        sole1 = new Solenoid(RobotMap.Pneumatics.COLLECTOR_1);
        sole2 = new Solenoid(RobotMap.Pneumatics.COLLECTOR_2);
    }

    public static CollectorSubsystem getInstance() {
        if(instance == null)
            instance = new CollectorSubsystem();
        return instance;
    }
    
    public void toggleCollector() {
        setPosition(!getCurrentPosition());
    }
    
    public boolean getCurrentPosition() {
        return sole1.get();
    }
    
    public void setPosition(boolean in) {
        sole1.set(in);
        sole2.set(!in);
    }
    
    public void initDefaultCommand() {
        
    }
    
    public void updateSmartDashboard() {

        SmartDashboard.putBoolean("Collector Collecting", getCurrentPosition());
    }
}
