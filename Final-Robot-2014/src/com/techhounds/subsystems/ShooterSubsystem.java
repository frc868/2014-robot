package com.techhounds.subsystems;


import com.techhounds.OI;
import com.techhounds.Robot;
import com.techhounds.RobotConstants;
import com.techhounds.RobotMap;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Calvin
 */
public class ShooterSubsystem extends PIDSubsystem{

    public static ShooterSubsystem rightRear;
    public static ShooterSubsystem rightFront;
    public static ShooterSubsystem leftFront;
    public static ShooterSubsystem leftRear;
    private SpeedController motor;
    
    private boolean inverted;
    
    private double curPower = 0;
    private double curRPS = 0;
    private double prevPower = 0;
    private double prevRPS = 0;
    private int infiniteCount = 0;
    
    private static double P = 0.007;
    private static double I = 0.0;
    private static double D = 0.045;
    
    private static final double MIN_EXPECTED_RPS = 0;
    private static final double MAX_EXPECTED_RPS = 100;
    private static final double MIN_PERIOD = 1 / MAX_EXPECTED_RPS;
    private static final double PERCENT_TOLERANCE = 1; // original 0.6
    public static final double DEFAULT_FRONT_RPS = 67.5;
    public static final double DEFAULT_BACK_RPS = 67.5;
    
    Counter encoder;
    
    private String name;
    
    //PID tuning
    private double pidInitTime = 0;
    private double timeToTarget = 0;
    private boolean lastTargetSet = false;
    
    private double maxRPS = 0;
    private double minRPS = 0;
    private double maxRPSTime = 0;
    private double minRPSTime = 0;
    private double secPerTick;
    
    private double lastTweakY;
    
    private Timer timer;
    private boolean timerrunning;
    
    private ShooterSubsystem(int port, int counterPort, String name, boolean invert) {
        super("Shooter " + name, P, I, D);
        motor = Robot.createSpeedController(port, "Shooter", name);
        inverted = invert;
        encoder = new Counter(counterPort);
        encoder.start();
        this.name = name;
        timer = new Timer();
    }

    public static ShooterSubsystem getRightRear() {
        if (rightRear == null) {
            rightRear = new ShooterSubsystem(
                    RobotMap.Shooter.REAR_RIGHT, 
                    RobotMap.Shooter.ENCODER_REAR_RIGHT, 
                    "Right Rear", false);
            rightRear.setPercentTolerance(PERCENT_TOLERANCE);
            rightRear.setInputRange(MIN_EXPECTED_RPS, MAX_EXPECTED_RPS);
        }
        return rightRear;
    }

    public static ShooterSubsystem getLeftRear() {
        if (leftRear == null) {
            leftRear = new ShooterSubsystem(
                    RobotMap.Shooter.REAR_LEFT, 
                    RobotMap.Shooter.ENCODER_REAR_LEFT,
                    "Left Rear", true);
            leftRear.setPercentTolerance(PERCENT_TOLERANCE);
            leftRear.setInputRange(MIN_EXPECTED_RPS, MAX_EXPECTED_RPS);
        }
        
        return leftRear;
    }

    public static ShooterSubsystem getLeftFront() {
        if (leftFront == null) {
            leftFront = new ShooterSubsystem(
                    RobotMap.Shooter.FRONT_LEFT, 
                    RobotMap.Shooter.ENCODER_FRONT_LEFT, 
                    "Left Front", true);
            leftFront.setPercentTolerance(PERCENT_TOLERANCE);
            leftFront.setInputRange(MIN_EXPECTED_RPS, MAX_EXPECTED_RPS);
        }
        return leftFront;
    }

    public static ShooterSubsystem getRightFront() {
        if (rightFront == null) {
            rightFront = new ShooterSubsystem(
                    RobotMap.Shooter.FRONT_RIGHT, 
                    RobotMap.Shooter.ENCODER_FRONT_RIGHT, 
                    "Right Front", false);
            rightFront.setPercentTolerance(PERCENT_TOLERANCE);
            rightFront.setInputRange(MIN_EXPECTED_RPS, MAX_EXPECTED_RPS);
        }
        return rightFront;
    }
    
    public double getRPS(){
        prevRPS = curRPS;
        secPerTick = encoder.getPeriod();
        if (Double.isInfinite(secPerTick)){
            infiniteCount++;
            if (infiniteCount >= 5){
                curRPS = 0;
            }
        }else{
            infiniteCount = 0;
            if (secPerTick > MIN_PERIOD)
                curRPS = 1 / secPerTick;
            else
                curRPS = prevRPS;
        }
        prevRPS = curRPS;
        return curRPS;
    }
    
    public void setGoalRPS(double rps){
        setSetpoint(rps);
        timer.reset();
        timer.start();
        timerrunning = true;
        enable();
    }
    
    public double getGoalRPS(){
        return getSetpoint();
    }
    
    public int getCount(){
        return encoder.get();
    }
    
    public void setPower(double val){
        disable();
        
        if (val > 1)
            val = 1;
        if (val < 0)
            val = 0;
        if(inverted)
            val = -val;
        motor.set(val);
        curPower = val;
    }
    
    public double getPower(){
        if(!inverted){
            return motor.get();
        }else{
            return -motor.get();
        }
        
    }
    
    public void lockPower() {
        setPower(getPower());
    }
    
    public void stop(){
        disable();
        motor.set(0);
        motor.disable();
    }

    public void resetEncoder(){
        encoder.reset();
    }
    
    protected double returnPIDInput() {
        return getRPS();
    }

    protected void usePIDOutput(double output) {
        output += getPower();
        output = Robot.checkRange(output, -0.05, 1);
        
        if(!onTarget() && timerrunning) {
            timeToTarget = timer.get();
            //timer.stop();
            //timerrunning = false;
        }
        
        motor.set(inverted ? -output : output);
    }
    
    public void tweakShooterRPS() {
        
        double tweakY = OI.getInstance().getTweakerLeftYAxis();
        double diff = tweakY - lastTweakY;
        
        if(getPower() != 0) {
            lastTweakY = tweakY;
            setGoalRPS(getGoalRPS() + diff * RobotConstants.Shooting.TWEAK_RPS_MULTI);
        }
    }

    public void updateDashboard(){
        /*
        if (!lastTargetSet && getPIDController().isEnable()){
            pidInitTime = Timer.getFPGATimestamp();
        }
        if (onTarget() && timeToTarget == 0){
            timeToTarget = Timer.getFPGATimestamp() - pidInitTime;
        }
        lastTargetSet = getPIDController().isEnable();
        */
        
        /* if (getRPS() > maxRPS || maxRPSTime < Timer.getFPGATimestamp() - 1){
            maxRPS = getRPS();
            maxRPSTime = Timer.getFPGATimestamp();
        }
        if (getRPS() < minRPS || minRPSTime < Timer.getFPGATimestamp() - 1){
            minRPS = getRPS();
            minRPSTime = Timer.getFPGATimestamp();
        } */

        SmartDashboard.putNumber("Shooter " + name + " RPS", getRPS());
        SmartDashboard.putBoolean("Shooter " + name + " is in Tolerance", onTarget());        
        
        if (!RobotConstants.Debug.SHOOTER_DEBUG_INFO) return;
        SmartDashboard.putNumber("Shooter " + name + " Power", getPower());

    }
    
    protected void initDefaultCommand() {

    }
}