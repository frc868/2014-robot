/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.techhounds;


import cheesy.CheesyVisionServer;
import com.techhounds.commands.CommandBase;
import com.techhounds.commands.UpdateDashboard;
import com.techhounds.commands.auton.AutonChooser;
import com.techhounds.commands.driving.DisableDriveWithGyro;
import com.techhounds.commands.pneumatics.ForceCompressorState;
import com.techhounds.commands.shooter.LockShooterPower;
import com.techhounds.commands.shooter.SetShooterPower;
import com.techhounds.commands.shooter.StopShooter;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.CompressorSubsystem;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.GyroSubsystem;
import com.techhounds.subsystems.KickerSubsystem;
import com.techhounds.subsystems.PopperSubsystem;
import com.techhounds.subsystems.ShooterSubsystem;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static boolean finalRobot;
    private static CheesyVisionServer serverInstance;
    
    private Command auton;
    
    public static SpeedController createSpeedController(int port, String group, String name){
        if(finalRobot){
            Talon talon = new Talon(port);
            LiveWindow.addActuator(group, name, talon);
            return talon;
        }else{
            Victor victor = new Victor(port);
            LiveWindow.addActuator(group, name, victor);
            return victor;
        }
    }
    
    public static double checkRange(double speed, double min, double max) {
        if(speed > max) {
            return max;
        } else if(speed < min) {
            return min;
        } else
            return speed;
    }
    
    public static CheesyVisionServer getCheesyServerInstance(){
        if (serverInstance == null){
            serverInstance = CheesyVisionServer.getInstance();
            serverInstance.start();
            
            System.out.println("*******\n"
                                + "CHEESYVISION SERVER STARTED\n"
                                + "*******\n\n");
        }
        
        return serverInstance;
    }
    
    public static boolean isFinal(){
        return true;//Preferences.getInstance().getBoolean("RobotFinal", true);
    }
    
    public void robotInit() {
        
        // Initialize all subsystems
        initSubsystems();
        OI.getInstance().initialize();
        (new UpdateDashboard()).start();

        // Initialize the CommandBase
        CommandBase.init();
        finalRobot = Robot.isFinal();
        System.out.println("*******\n"
                            + "TEAM 868 CAN ROBOT NOW!\n"
                            + "*******");
    }

    public void autonomousInit() {

        // Schedule the Auton Commands
        auton = AutonChooser.getSelected();
        
        CompressorSubsystem.setIsDrivingFast(false);
        CompressorSubsystem.setIsShooting(true);
        
        auton.start();
        
        System.out.println("*******\n"
                            + "TEAM 868 CAN AUTON NOW!\n"
                            + "*******");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
	
        if(auton != null)
            auton.cancel();
        
        (new DisableDriveWithGyro()).start();
        (new ForceCompressorState(false)).start();
        (new LockShooterPower(false)).start();
        (new StopShooter()).start();
        (new DisableDriveWithGyro()).start();
        
        System.out.println("*******\n"
                            + "TEAM 868 CAN TELEOP NOW!\n"
                            + "*******");
    }
    
    private void initSubsystems(){
        DriveSubsystem.getInstance();
        ShooterSubsystem.getLeftFront();
        ShooterSubsystem.getLeftRear();
        ShooterSubsystem.getRightFront();
        ShooterSubsystem.getRightRear();
        CollectorSubsystem.getInstance();
        PopperSubsystem.getInstance();
        CompressorSubsystem.getInstance();
        StopperSubsystem.getInstance();
        KickerSubsystem.getInstance();
        GyroSubsystem.getInstance();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
