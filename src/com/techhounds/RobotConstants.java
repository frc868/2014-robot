/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.techhounds;

import edu.wpi.first.wpilibj.Preferences;

/**
 * @author Atif Niyaz
 */
public class RobotConstants {
    
    public static class Debug {
        
        // Subsystem Debug Info
        public static final boolean DRIVE_DEBUG_INFO = true;
        public static final boolean SHOOTER_DEBUG_INFO = false;
        
        // Command Buttons
        public static final boolean ROTATION_BUTTONS = false;
        public static final boolean EXTRA_SMARTDASHBOARD_BUTTONS = true;
    }
    
    public static class Shooting {
        
        public static final double BACK_RPS = 67.5;
        public static final double FRONT_RPS = 67.5;
        
        public static final double FRONT_INCR = 5;
        public static final double BACK_INCR = 5;
        
        public static final double FRONT_DECR = -5;
        public static final double BACK_DECR = -5;
        
        public static final double TWEAK_RPS_MULTI = 7.0;
        
        public static final double LOW_POW_RPS_BACK = 28;
        public static final double LOW_POW_RPS_FRONT = 28;
    }
    
    public static class Kicker {
        
        public static final double IN_SPEED = -1.0;
        public static final double OUT_SPEED = 1.0;
        
        public static final double STOP = 0.0;
    }
    
    public static class Driving {
        
        public static final double TWEAKER_MAG_MULT_50 = 0.9;
        public static final double TWEAKER_ROT_MULT_50 = 0.9;
        
        public static final double TWEAKER_MAG_MULT_100 = 0.9;
        public static final double TWEAKER_ROT_MULT_100 = 0.9;
    }
    
    public static class Auton {
        
        public static final double DISTANCE = 10.5;
        public static final double SECOND_BALL_DISTANCE = 12.25;
        public static final double SPEED = -1.0;
        
        public static final double CHEEZY_TIMEOUT_ONE_BALL = 6.5;
        public static final double CHEEZY_TIMEOUT_TWO_BALL = 2.0;
        public static final double WAIT_AFTER_FIRE = 1.5;
    }
    
    public static class Pneumatics {
        
        public static final double POPPER_DELAY = .43;//.18
        public static final double COLLECTOR_DELAY = .3;
    }
}
