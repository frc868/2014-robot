package com.techhounds;

/**
 * @author FRC 868
 */
public class RobotMap {
 
    public static class Controller {
        
        public static final int DRIVER = 1;
        public static final int TWEAKER = 2;
    }
    
    public static class Drive {
        
        public static final int LEFT_MOTORS = 1;
        public static final int RIGHT_MOTORS = 2;
        
        public static final int ENCODER_L_A = 3;
        public static final int ENCODER_L_B = 4;
        public static final int ENCODER_R_A = 1;
        public static final int ENCODER_R_B = 2;
    }
    
    public static class Shooter {
        
        public static final int FRONT_LEFT = 6;
        public static final int FRONT_RIGHT = 3;
        public static final int REAR_LEFT = 4;
        public static final int REAR_RIGHT = 5;
        
        public static final int ENCODER_FRONT_LEFT = 7;
        public static final int ENCODER_FRONT_RIGHT = 9;
        public static final int ENCODER_REAR_LEFT = 6;
        public static final int ENCODER_REAR_RIGHT = 8;
    }
    
    public static class Pneumatics {
        
        public static final int COLLECTOR_1 = 2;
        public static final int COLLECTOR_2 = 3;
        public static final int POPPER = 1;
        public static final int STOPPER = 5;
    }
    
    public static class Compressor {
        
        public static final int COMPRESSOR_SENSOR = 5;
        public static final int COMPRESSOR_RELAY = 1;
    }
    
    public static class Gyro {
        
        public static final int GYRO = 1;
    }
    
    public static class Kicker {
        
        public static final int KICKER = 7;
    }
    
    public static class Camera {
        
        public static final int CAMERA_LEDS = 2;
    }
}
