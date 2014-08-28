
package com.techhounds;

import com.techhounds.commands.CommandBase;
import com.techhounds.commands.DebugDriveWithEncoder;
import com.techhounds.commands.StopTrain;
import com.techhounds.commands.Train;
import com.techhounds.commands.auton.AutonChooser;
import com.techhounds.commands.driving.DisableDriveWithGyro;
import com.techhounds.commands.driving.DriveWithEncoder;
import com.techhounds.commands.driving.DriveWithEncoderAndGyro;
import com.techhounds.commands.driving.DriveWithGyro;
import com.techhounds.commands.driving.RotateToAngle;
import com.techhounds.commands.driving.SetDriveScale;
import com.techhounds.commands.kicker.SetKickerWheels;
import com.techhounds.commands.pneumatics.CollectorSequence;
import com.techhounds.commands.pneumatics.SetCollector;
import com.techhounds.commands.pneumatics.SetJoshPoppers;
import com.techhounds.commands.pneumatics.SetPopper;
import com.techhounds.commands.pneumatics.SetPopperOnly;
import com.techhounds.commands.pneumatics.SetStoppers;
import com.techhounds.commands.shooter.Fire;
import com.techhounds.commands.shooter.FireReady;
import com.techhounds.commands.shooter.OperatorFire;
import com.techhounds.commands.shooter.SetShooterRPS;
import com.techhounds.commands.shooter.StopFire;
import com.techhounds.commands.shooter.StopShooter;
import com.techhounds.commands.shooter.TweakShooterRPS;
import com.techhounds.subsystems.CollectorSubsystem;
import com.techhounds.subsystems.DriveSubsystem;
import com.techhounds.subsystems.PopperSubsystem;
import com.techhounds.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    private static OI instance;
    private final ControllerMap driverGamepad;
    private final ControllerMap tweakerGamepad;
    
    private SendableChooser autonChoice;
    
    private boolean isInitialized;
    
    private final double GAMEPAD_DEADZONE = 0.05;
    
    // Driver Gamepad
    private final int DRIVER_TOGGLE_POPPER = ControllerMap.BACK;
    private final int DRIVER_COLLECTOR_OUT = ControllerMap.LT;
    private final int DRIVER_COLLECTOR_IN = ControllerMap.LB;
    private final int DRIVER_BALL_OUT = ControllerMap.RT;
    private final int DRIVER_BALL_IN = ControllerMap.RB;
    private final int DRIVER_FIRE = ControllerMap.START;
    private final int DRIVER_BACK_PLUS = ControllerMap.X;
    private final int DRIVER_BACK_MINUS = ControllerMap.A;
    private final int DRIVER_FRONT_PLUS = ControllerMap.Y;
    private final int DRIVER_FRONT_MINUS = ControllerMap.B;
    private final int DRIVER_SHOOTER_ON = ControllerMap.UP;
    private final int DRIVER_SHOOTER_OFF = ControllerMap.DOWN;
    
    // Operator / Tweaker Gamepad
    private final int TWEAKER_COLLECT = ControllerMap.LB;
    private final int TWEAKER_HUMAN_COLLECT = ControllerMap.RB;
    private final int TWEAKER_SHOOTER_ON = ControllerMap.LT;
    private final int TWEAKER_FIRE = ControllerMap.RT;
    private final int TWEKAER_ADJUST_RPS = ControllerMap.START; 
    private final int TWEAKER_LOW_POWER = ControllerMap.X;
    private final int TWEAKER_POPPER_IN = ControllerMap.A;
    private final int TWEAKER_POPPER_IN_ALT = ControllerMap.BACK;
    private final int TWEAKER_SHOOTER_OFF = ControllerMap.B;
    private final int TWEAKER_BALL_OUT = ControllerMap.UP;
    private final int TWEAKER_BALL_IN = ControllerMap.DOWN;
    private final int TWEAKER_TRAIN = ControllerMap.Y;
    
    private OI() {
        driverGamepad = new ControllerMap(new Joystick(RobotMap.Controller.DRIVER), ControllerMap.LOGITECH);
        tweakerGamepad = new ControllerMap(new Joystick(RobotMap.Controller.TWEAKER), ControllerMap.LOGITECH);
        
        autonChoice = createChoices("Auton Choices", AutonChooser.AUTON_CHOICES);
    }
    
    public static OI getInstance() {
        if(instance == null)
            instance = new OI();
        return instance;
    }
    
    public void initialize(){
        if (isInitialized)
            return;
        
        initDriver();
        initTweaker();
        initDashboard();
        
        isInitialized = true;
    }
    
    public void initDriver(){
        
        /* Controls
        * Left Joystick --> Forward / Backward (Magnitude)
        * Right Joystick --> Rotation
        * 
        */
        
        // Shooter On (DPAD UP)
        Button shooterOn = driverGamepad.createDPadButton(ControllerMap.UP);
        shooterOn.whenPressed(new SetShooterRPS());
        
        // Shooter Off (DPAD DOWN)
        Button shooterOff = driverGamepad.createDPadButton(ControllerMap.DOWN);
        shooterOff.whenPressed(new StopShooter());
        
        // Fires the Ball (BUTTON START)
        Button fire = driverGamepad.createButton(DRIVER_FIRE);
        fire.whenPressed(new Fire());
        fire.whileHeld(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        fire.whenReleased(new StopFire());
        
        // Increment Back Shooter Wheels (BUTTON X)
        Button shooterBackIncr = driverGamepad.createButton(DRIVER_BACK_PLUS);
        shooterBackIncr.whenPressed(new SetShooterRPS(0.0, RobotConstants.Shooting.BACK_INCR, true));
        
        // Decrement Back Shooter Wheels (BUTTON A)
        Button shooterBackDecr = driverGamepad.createButton(DRIVER_BACK_MINUS);
        shooterBackDecr.whenPressed(new SetShooterRPS(0.0, RobotConstants.Shooting.BACK_DECR, true));
        
        // Increment Front Shooter Wheels (BUTTON Y)
        Button shooterFrontIncr = driverGamepad.createButton(DRIVER_FRONT_PLUS);
        shooterFrontIncr.whenPressed(new SetShooterRPS(RobotConstants.Shooting.FRONT_INCR, 0.0, true));
        
        // Decrement Front Shooter Wheels (BUTTON B)
        Button shooterFrontDecr = driverGamepad.createButton(DRIVER_FRONT_MINUS);
        shooterFrontDecr.whenPressed(new SetShooterRPS(RobotConstants.Shooting.FRONT_DECR, 0.0, true));
        
        // Move Ball Out (BUTTON RT)
        Button ballOut = driverGamepad.createButton(DRIVER_BALL_OUT);
        ballOut.whileHeld(new SetKickerWheels(RobotConstants.Kicker.OUT_SPEED));
        ballOut.whenReleased(new SetKickerWheels(RobotConstants.Kicker.STOP));
        
        // Move Ball In (BUTTON RB)
        Button ballIn = driverGamepad.createButton(DRIVER_BALL_IN);
        ballIn.whileHeld(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        ballIn.whenReleased(new SetKickerWheels(RobotConstants.Kicker.STOP));
        
        // Move Collector In (BUTTON LB)
        Button collectorIn = driverGamepad.createButton(DRIVER_COLLECTOR_IN);
        collectorIn.whenPressed(new SetCollector(CollectorSubsystem.UP));
        
        // Move Collector Out (BUTTON LT)
        Button collectorOut = driverGamepad.createButton(DRIVER_COLLECTOR_OUT);
        collectorOut.whenPressed(new SetCollector(CollectorSubsystem.COLLECTING));
        
        // Toggle Popper (BUTTON BACK)
        Button togglePopper = driverGamepad.createButton(DRIVER_TOGGLE_POPPER);
        togglePopper.whenPressed(new SetPopper());
    }
    
    public void initTweaker(){
        
        /* Controls
        * DPAD-UP --> Shooter Wheels In
        * DPAD-DOWN --> Shooter Wheels Out
        * 
        * Left Joystick --> Forward / Backward (Magnitude)
        * Right Joystick --> Rotation
        */
        
        Button train = tweakerGamepad.createButton(TWEAKER_TRAIN);
        train.whenPressed(new Train());
        train.whenReleased(new StopTrain());
        
        // Kicker Wheels In
        Button kickWheelIn = tweakerGamepad.createDPadButton(TWEAKER_BALL_IN);
        kickWheelIn.whenPressed(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        kickWheelIn.whenReleased(new SetKickerWheels(RobotConstants.Kicker.STOP));

        // Kicker Wheels Out
        Button kickWheelOut = tweakerGamepad.createDPadButton(TWEAKER_BALL_OUT);
        kickWheelOut.whenPressed(new SetKickerWheels(RobotConstants.Kicker.OUT_SPEED));
        kickWheelOut.whenReleased(new SetKickerWheels(RobotConstants.Kicker.STOP));
        
        // Turn on the shooter (BUTTON LT)
        Button startShooter = tweakerGamepad.createButton(TWEAKER_SHOOTER_ON);
        startShooter.whenPressed(new FireReady());
        //startShooter.whenPressed(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED, .25));
        
        // Collect the Ball from Floor (BUTTON LB)
        Button collect = tweakerGamepad.createButton(TWEAKER_COLLECT);
        collect.whenPressed(new SetCollector(CollectorSubsystem.COLLECTING));
        collect.whileHeld(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        collect.whenReleased(new SetCollector(CollectorSubsystem.UP));
        collect.whenReleased(new SetKickerWheels(RobotConstants.Kicker.STOP));
        
        // Collect the Ball from Human Player (BUTTON RB)
        Button collectHuman = tweakerGamepad.createButton(TWEAKER_HUMAN_COLLECT);
        collectHuman.whenPressed(new SetCollector(CollectorSubsystem.COLLECTING));
        collectHuman.whileHeld(new SetKickerWheels(RobotConstants.Kicker.OUT_SPEED));
        collectHuman.whenPressed(new SetPopperOnly(PopperSubsystem.IN));
        collectHuman.whenReleased(new SetCollector(CollectorSubsystem.UP));
        collectHuman.whenReleased(new SetKickerWheels(RobotConstants.Kicker.STOP));
        collectHuman.whenReleased(new SetJoshPoppers());
        
        // Fires the Ball (BUTTON RT)
        Button fire = tweakerGamepad.createButton(TWEAKER_FIRE);
        fire.whenPressed(new OperatorFire(1.0));
        //fire.whenPressed(new Fire());
        //fire.whileHeld(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED));
        //fire.whenReleased(new StopFire());
        
        // Tweaker Adjust RPS (BUTTON START)
        
        Button adjRPS = tweakerGamepad.createButton(TWEKAER_ADJUST_RPS);
        adjRPS.whileHeld(new TweakShooterRPS());
        
        // Set Low Power (BUTTON X)
        Button lowPower = tweakerGamepad.createButton(TWEAKER_LOW_POWER);
        //lowPower.whenPressed(new SetPopper(PopperSubsystem.OUT));
        //lowPower.whenPressed(new SetKickerWheels(RobotConstants.Kicker.IN_SPEED, .25));
        lowPower.whenPressed(new SetShooterRPS(RobotConstants.Shooting.LOW_POW_RPS_FRONT, RobotConstants.Shooting.LOW_POW_RPS_BACK, false));
        
        // Toggle Popper (BUTTON A)
        Button popperIn = tweakerGamepad.createButton(TWEAKER_POPPER_IN);
        popperIn.whenPressed(new SetPopper());
        
        // Toggle Popper Alternative (BUTTON BACK)
        Button popperIn_ALT = tweakerGamepad.createButton(TWEAKER_POPPER_IN_ALT);
        popperIn_ALT.whenPressed(new SetPopper());
        
        // Turn off Shooter (BUTTON B)
        Button stopShooter = tweakerGamepad.createButton(TWEAKER_SHOOTER_OFF);
        stopShooter.whenPressed(new StopShooter());
    }
    
    
    private Command createAutonSetup() {
        CommandGroup cg = new CommandGroup("Auton Setup");
        cg.addSequential(new SetStoppers(StopperSubsystem.OUT));
        cg.addSequential(new SetPopperOnly(PopperSubsystem.IN));
        cg.addSequential(new SetCollector(CollectorSubsystem.UP));
        cg.addSequential(new SetKickerWheels(0));
        cg.addSequential(new StopShooter());
        
        return cg;
    }
    
    public void initDashboard(){
        SmartDashboard.putData("Auton Setup", createAutonSetup());
       
        if (RobotConstants.Debug.ROTATION_BUTTONS){
            SmartDashboard.putData("Rotate to 90", new RotateToAngle(90));
            SmartDashboard.putData("Rotate to 270", new RotateToAngle(270));
            SmartDashboard.putData("Rotate to 0", new RotateToAngle(0));
        }
        
        if (RobotConstants.Debug.EXTRA_SMARTDASHBOARD_BUTTONS){  
            SmartDashboard.putData("Drive 2 Feet", new DriveWithEncoder(2.0));
            SmartDashboard.putData("Drive 4 Feet", new DriveWithEncoder(4.0));
            SmartDashboard.putData("Drive 10 Feet", new DriveWithEncoderAndGyro(10.0));
            SmartDashboard.putData("Drive Back 10 Feet", new DriveWithEncoderAndGyro(-10.0));
            
            SmartDashboard.putData("Drive Back and Forth", new DebugDriveWithEncoder());

            SmartDashboard.putData("Popper In", new SetPopperOnly(PopperSubsystem.IN));
            SmartDashboard.putData("Popper Out", new SetPopperOnly(PopperSubsystem.OUT));
            SmartDashboard.putData("Toggle Popper", new SetPopper());

            SmartDashboard.putData("Stopper In", new SetStoppers(StopperSubsystem.IN));
            SmartDashboard.putData("Stopper Out", new SetStoppers(StopperSubsystem.OUT));

            
            SmartDashboard.putData("Enable Gyro", new DriveWithGyro());
            SmartDashboard.putData("Disable Gyro", new DisableDriveWithGyro());

            SmartDashboard.putData("Drive Subsystem", DriveSubsystem.getInstance());
            
            SmartDashboard.putData("Collector Sequence", new CollectorSequence());
            SmartDashboard.putData("Colletor Subsystem", CollectorSubsystem.getInstance());
        }
    }
    public double getDriverRightXAxis(){
        return analogRange(driverGamepad.getRightStickX());
    }
    
    public double getDriverRightYAxis(){
        return analogRange(driverGamepad.getRightStickY());
    }
    
    public double getDriverLeftXAxis(){
        return analogRange(driverGamepad.getLeftStickX());
    }
    
    public double getDriverLeftYAxis(){
        return analogRange(driverGamepad.getLeftStickY());
    }
    
    public double getTweakerRightXAxis(){
        return analogRange(tweakerGamepad.getRightStickX());
    }
    
    public double getTweakerRightYAxis(){
        return analogRange(tweakerGamepad.getRightStickY());
    }
    
    public double getTweakerLeftXAxis(){
        return analogRange(tweakerGamepad.getLeftStickX());
    }
    
    public double getTweakerLeftYAxis(){
        return analogRange(tweakerGamepad.getLeftStickY());
    }
    
    public double analogRange(double val){
        if (Math.abs(val) < GAMEPAD_DEADZONE){
            if (val > 0)
                val = GAMEPAD_DEADZONE;
            else
                val = -GAMEPAD_DEADZONE;
                
        }else if (Math.abs(val) > 1){
            if (val > 0)
                val = 1;
            else
                val = -1;
        }
        return val;
    }
    
    public int getAutonChoice() {
         return ((Integer) this.autonChoice.getSelected()).intValue();
    }
    
    private SendableChooser createChoices(String label, String [] choices) {
        SendableChooser send = new SendableChooser();
        
        if(choices.length > 0) {
            send.addDefault(choices[0], new Integer(0));
            
            for(int i = 1; i < choices.length; i++) {
                send.addObject(choices[i], new Integer(i));
            }
            
            SmartDashboard.putData(label, send);
        }
        
        return send;
    }
}

