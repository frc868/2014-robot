package com.techhounds.subsystems;

import com.techhounds.MultiMotor;
import com.techhounds.OI;
import com.techhounds.Robot;
import com.techhounds.RobotConstants;
import com.techhounds.RobotMap;
import com.techhounds.commands.driving.DriveWithGamepad;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Atif Niyaz
 */
public class DriveSubsystem extends Subsystem {

    public static final boolean ADJUSTMENT = true;
    public static final boolean NOT_ADJUSTMENT = false;

    private static DriveSubsystem instance;

    private MultiMotor leftMotors;
    private MultiMotor rightMotors;

    private Encoder leftEncoder;
    private Encoder rightEncoder;

    private RobotDrive robotDrive;

    private boolean isArcadeDrive;

    private static double DRIVER_MAGNITUDE_MULTIPLIER = 1;
    private static double DRIVER_ROTATION_MULTIPLIER = 1;

    private static double TWEAKER_MAGNITUDE_MULTIPLIER = 0.9;
    private static double TWEAKER_ROTATION_MULTIPLIER = 0.9;

    private static final double Kp = 0.19;
    private static final double Ki = 0;
    private static final double Kd = 0.185;

    private static final double GYRO_Kp = 0.07;
    private static final double GYRO_Ki = 0;
    private static final double GYRO_Kd = 0.05;

    private final double FEET_PER_COUNT = 1 / 117.0625;

    private int leftInfiniteCount = 0;
    private int rightInfiniteCount = 0;

    private double targetDistance = 0;
    private double targetAngle = 0;
    private double drivePIDTolerance = 0;

    private double gyroAdj = 0;

    public GyroSubsystem gyro;

    private PIDController gyroPID;
    private PIDController drivePID;

    private DriveSubsystem() {
        super("DRIVE SUBSYSTEM");

        gyro = GyroSubsystem.getInstance();

        leftMotors = new MultiMotor(
                new SpeedController[]{
                    Robot.createSpeedController(RobotMap.Drive.LEFT_MOTORS, "DRIVE SUBSYSTEM", "LEFT MOTORS")},
                new boolean[]{false});
        rightMotors = new MultiMotor(
                new SpeedController[]{
                    Robot.createSpeedController(RobotMap.Drive.RIGHT_MOTORS, "DRIVE SUBSYSTEM", "RIGHT MOTORS")},
                new boolean[]{false});

        leftEncoder = new Encoder(RobotMap.Drive.ENCODER_L_A, RobotMap.Drive.ENCODER_L_B);
        rightEncoder = new Encoder(RobotMap.Drive.ENCODER_R_A, RobotMap.Drive.ENCODER_R_B);

        leftEncoder.reset();
        rightEncoder.reset();

        leftEncoder.setDistancePerPulse(FEET_PER_COUNT);
        rightEncoder.setDistancePerPulse(FEET_PER_COUNT);

        leftEncoder.start();
        rightEncoder.start();

        robotDrive = new RobotDrive(leftMotors, rightMotors);
        robotDrive.setSafetyEnabled(false);

        gyroPID = new PIDController(
                GYRO_Kp,
                GYRO_Ki,
                GYRO_Kd,
                gyro,
                new PIDOutput() {
                    public void pidWrite(double output) {
                        //setPower(getLeftPower() + output, getRightPower() - output);
                        gyroAdj = output / 4.0;
                    }
                });
        gyroPID.setOutputRange(-1, 1);
        gyroPID.setAbsoluteTolerance(5.0);

        SmartDashboard.putData("Gyro PID", gyroPID);

        drivePID = new PIDController(
                Kp,
                Ki,
                Kd,
                new PIDSource() {
                    public double pidGet() {
                        return getAvgDistance();
                    }
                },
                new PIDOutput() {
                    private double lastSteer = 0;
                    
                    public void pidWrite(double output) {
                        //double steer = Math.max(-0.4, Math.min(0.4, gyroAdj));
                        //SmartDashboard.putNumber("Angle Error", gyroPID.getSetpoint() - gyro.getRawAngle());
                        //SmartDashboard.putNumber("Steer", steer);
                        double steer = (getLeftDistance() - getRightDistance()) * 0.005;
                        SmartDashboard.putNumber("Steer", steer);
                        steer = 0;
                        setPower(output - steer, output + steer);
                        /*
                        if (output < 0) {
                            setPower(output + steer, output - steer);
                        } else {
                            setPower(output + steer, output - steer);
                        }
                        */
                    }
                }
        );
        drivePID.setOutputRange(
                -1, 1);
        drivePID.setAbsoluteTolerance(
                0.5);
        drivePIDTolerance = 0.5;

        SmartDashboard.putData(
                "Encoder PID", drivePID);

        isArcadeDrive = true;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithGamepad(this));
    }

    public static DriveSubsystem getInstance() {
        if (instance == null) {
            instance = new DriveSubsystem();
        }
        return instance;
    }

    public void driveTank() {
        double leftPower, rightPower;

        leftPower = OI.getInstance().getDriverLeftYAxis();
        rightPower = OI.getInstance().getDriverRightYAxis();

        driveTank(leftPower, rightPower);
    }

    public void driveArcade() {
        double magnitude, rotation;
        double tweakMagnitude, tweakRotation;

        magnitude = OI.getInstance().getDriverLeftYAxis();
        rotation = OI.getInstance().getDriverRightXAxis();

        magnitude *= DRIVER_MAGNITUDE_MULTIPLIER;
        rotation *= DRIVER_ROTATION_MULTIPLIER;

        /* Vincent's two full stick arcade drive 
         if (Math.abs(OI.getInstance().getTweakerLeftYAxis()) > Math.abs(OI.getInstance().getTweakerRightYAxis())){
         tweakMagnitude = OI.getInstance().getTweakerLeftYAxis();
         }else{
         tweakMagnitude = OI.getInstance().getTweakerRightYAxis();
         }
        
         if (Math.abs(OI.getInstance().getTweakerLeftXAxis()) > Math.abs(OI.getInstance().getTweakerRightXAxis())){
         tweakRotation = OI.getInstance().getTweakerLeftXAxis();
         }else{
         tweakRotation = OI.getInstance().getTweakerRightXAxis();
         }*/
        tweakMagnitude = OI.getInstance().getTweakerLeftYAxis();
        tweakRotation = OI.getInstance().getTweakerRightXAxis();

        tweakMagnitude *= TWEAKER_MAGNITUDE_MULTIPLIER;
        tweakRotation *= TWEAKER_ROTATION_MULTIPLIER;

        driveArcade(Robot.checkRange(magnitude + tweakMagnitude, -1, 1),
                Robot.checkRange(rotation + tweakRotation, -1, 1));
    }

    public void driveWithGamepad() {
        if (isArcadeDrive) {
            driveArcade();
        } else {
            driveTank();
        }

        CompressorSubsystem.setIsDrivingFast(false);
    }

    public void driveWithEncoder(double distance) {

        //disableRotateAngle();
        targetDistance = distance;

        resetEncoders();

        drivePID.setSetpoint(distance);
        drivePID.enable();
    }

    public void driveWithGyro() {
        gyroPID.setSetpoint(GyroSubsystem.getInstance().getRawAngle());
        gyroPID.enable();
    }

    public void disableGyro() {
        gyroPID.disable();
        gyroAdj = 0;
    }

    public boolean gryoAngleInTolerance(){
        return gyroPID.onTarget();
    }
    
    public boolean driveDistanceInTolerance() {
        //return drivePID.onTarget();
        return Math.abs(targetDistance - getLeftDistance()) <= drivePIDTolerance &&
                Math.abs(targetDistance - getRightDistance())<= drivePIDTolerance;
    }

    public void stopDriveWithEncoder() {
        drivePID.disable();
    }

    public void rotateToAngle(double angle) {

        stopDriveWithEncoder();

        this.targetAngle = angle;

        gyroPID.setSetpoint(angle);
        gyroPID.enable();
    }

    public boolean rotateInTolerance() {
        return gyroPID.onTarget();
    }

    public void disableRotateAngle() {
        gyroPID.disable();
    }

    public void toggleDriveMode() {
        isArcadeDrive = !isArcadeDrive;
    }

    public void driveTank(double leftMagnitude, double rightMagnitude) {
        robotDrive.tankDrive(leftMagnitude, rightMagnitude);
    }

    public void driveArcade(double magnitude, double rotation) {
        robotDrive.arcadeDrive(magnitude, rotation);
    }

    public double getTargetDistance() {
        return this.targetDistance;
    }

    public void stopMotors() {
        robotDrive.stopMotor();
    }

    public double getLeftPower() {
        return -leftMotors.get();
    }

    public double getRightPower() {
        return rightMotors.get();
    }

    public void setRightPower(double power) {
        rightMotors.set(power);
    }

    public void setLeftPower(double power) {
        leftMotors.set(-power);
    }

    public void setPower(double power) {
        setPower(power, power);
    }

    public void setPower(double left, double right) {
        setRightPower(right);
        setLeftPower(left);
    }

    public int getLeftEncoderCount() {
        return leftEncoder.get();
    }

    public int getRightEncoderCount() {
        return rightEncoder.get();
    }

    public int getAvgEncoderCount() {
        return (getRightEncoderCount() + getLeftEncoderCount()) / 2;
    }

    public double getLeftRPS() {
        double secPerTick = leftEncoder.getPeriod();
        double rps = 0;

        if (Double.isInfinite(secPerTick)) {
            leftInfiniteCount++;

            if (leftInfiniteCount >= 15) {
                return 0;
            }
        } else {
            rps = 1 / secPerTick;
            leftInfiniteCount = 0;
        }

        return rps;
    }

    public double getRightRPS() {
        double secPerTick = rightEncoder.getPeriod();
        double rps = 0;

        if (Double.isInfinite(secPerTick)) {
            rightInfiniteCount++;

            if (rightInfiniteCount >= 15) {
                return 0;
            }
        } else {
            rps = 1 / secPerTick;
            rightInfiniteCount = 0;
        }

        return rps;
    }

    public double getAvgRPS() {
        return (getRightRPS() + getLeftRPS()) / 2;
    }

    public double getLeftDistance() {
        return leftEncoder.getDistance();
    }

    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    public double getAvgDistance() {
        return (getRightDistance() + getLeftDistance()) / 2;
    }

    public boolean getLeftDirection() {
        return leftEncoder.getDirection();
    }

    public boolean getRightDirection() {
        return rightEncoder.getDirection();
    }

    public boolean isSameDirection() {
        return getLeftDirection() == getRightDirection();
    }

    public boolean isArcadeDrive() {
        return isArcadeDrive;
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public static void setTweakerScale(double magnitude, double rotation) {
        TWEAKER_MAGNITUDE_MULTIPLIER = magnitude;
        TWEAKER_ROTATION_MULTIPLIER = rotation;
    }

    public static void setDriverScale(double magnitude, double rotation) {
        DRIVER_MAGNITUDE_MULTIPLIER = magnitude;
        DRIVER_ROTATION_MULTIPLIER = rotation;
    }

    public void updateSmartDashboard() {
        
        if (!RobotConstants.Debug.DRIVE_DEBUG_INFO) return;
        
        SmartDashboard.putNumber("Power", (getLeftPower() + getRightPower()) / 2.0);
        SmartDashboard.putNumber("LeftPower", getLeftPower());
        SmartDashboard.putNumber("RightPower", getRightPower());

        SmartDashboard.putNumber("Right Distance", getRightDistance());
        SmartDashboard.putNumber("Left Distance", getLeftDistance());
        SmartDashboard.putNumber("Distance", getAvgDistance());
        SmartDashboard.putBoolean("Gyro PID Enabled", gyroPID.isEnable());

        SmartDashboard.putNumber("Adjustment value", gyroAdj);
    }
}
