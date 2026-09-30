package frc.robot.subsystems;

import frc.robot.handlers.*;

import com.studica.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import frc.robot.Constants.CANConstants;
import frc.robot.Constants.SwerveConstants;;

public class Drive {
    private final MAXSwerveModule m_frontLeft = new MAXSwerveModule(
        CANConstants.kFrontLeftDrivingCanId,
        CANConstants.kFrontLeftTurningCanId,
        SwerveConstants.kFrontLeftChassisAngularOffset);

    private final MAXSwerveModule m_frontRight = new MAXSwerveModule(
        CANConstants.kFrontRightDrivingCanId,
        CANConstants.kFrontRightTurningCanId,
        SwerveConstants.kFrontRightChassisAngularOffset);

    private final MAXSwerveModule m_rearLeft = new MAXSwerveModule(
        CANConstants.kRearLeftDrivingCanId,
        CANConstants.kRearLeftTurningCanId,
        SwerveConstants.kBackLeftChassisAngularOffset);

    private final MAXSwerveModule m_rearRight = new MAXSwerveModule(
        CANConstants.kRearRightDrivingCanId,
        CANConstants.kRearRightTurningCanId,
        SwerveConstants.kBackRightChassisAngularOffset);

    // Odometry class for tracking robot pose
}
