package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


import frc.robot.Constants.ModuleConstants;

public final class Configs {
    public static final class MAXSwerveModule {
        
        public static SparkMaxConfig getDrivingConfig() {
            SparkMaxConfig drivingConfig = new SparkMaxConfig();
            
            // Use module constants to calculate conversion factors and feed forward gain.
            double drivingFactor = ModuleConstants.kWheelDiameterMeters * Math.PI
                    / ModuleConstants.kDrivingMotorReduction;
            double drivingVelocityFeedForward = 1 / ModuleConstants.kDriveWheelFreeSpeedRps;

            drivingConfig
                    .idleMode(IdleMode.kBrake)
                    .smartCurrentLimit(50);
            drivingConfig.encoder
                    .positionConversionFactor(drivingFactor) // meters
                    .velocityConversionFactor(drivingFactor / 60.0); // meters per second
            drivingConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                    // These are example gains you may need to them for your own robot!
                    .pid(0.04, 0, 0)
                    .velocityFF(drivingVelocityFeedForward)        // if driving fails, comment this
                    //.velocityFF(drivingVelocityFeedForward)   // and uncomment this (velovityFF is deprecated though)
                    .outputRange(-1, 1);
                    
            return drivingConfig;
        }

        public static SparkMaxConfig getTurningConfig() {
            SparkMaxConfig turningConfig = new SparkMaxConfig();
            double turningFactor = Math.PI;                 // might increase turning speed

            turningConfig
                    .idleMode(IdleMode.kBrake)
                    .smartCurrentLimit(20);
            turningConfig.absoluteEncoder
                    // Invert the turning encoder, since the output shaft rotates in the opposite
                    // direction of the steering motor in the MAXSwerve Module.
                    .inverted(true)
                    .positionConversionFactor(turningFactor) // radians
                    .velocityConversionFactor(turningFactor / 60.0); // radians per second
            turningConfig.closedLoop
                    .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                    // These are example gains you may need to them for your own robot!
                    .pid(1, 0, 0)
                    .outputRange(-1, 1)
                    // Enable PID wrap around for the turning motor.
                    .positionWrappingEnabled(true)
                    .positionWrappingInputRange(-Math.PI, turningFactor);
                    
            return turningConfig;
        }
    }
}