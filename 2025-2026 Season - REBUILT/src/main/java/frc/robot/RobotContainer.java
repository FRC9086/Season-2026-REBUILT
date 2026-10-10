// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.Expunge;
import frc.robot.commands.MoveArmToPosition;
import frc.robot.commands.Pull;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.AutoSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto;

public class RobotContainer {
  // Subsystems
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  // Controller
  // Driver is port 1 and Operator is port 0 under usb connections in Driver Station      // This can be changed in Constants.java
  private final XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  private final XboxController m_operatorController = new XboxController(OIConstants.kOperatorControllerPort);

  public RobotContainer() {
    configureBindings();
    configureDriveCommand();
  }

  private void configureDriveCommand() {
    driveSubsystem.setDefaultCommand(
      new RunCommand(
        () -> {
          double leftX = -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband);
          double leftY = -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband);
          double rightX = -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband);

          // Test individual motors
          // Boolean[] buttons = {m_driverController.getXButton(), m_driverController.getYButton(), m_driverController.getAButton(), m_driverController.getBButton()};
          
          driveSubsystem.drive(leftX, leftY, rightX, true, false);
        },
        driveSubsystem
        )
    );

    intakeSubsystem.setDefaultCommand(
      new RunCommand(() -> {
        double pullerSpool = m_operatorController.getRightTriggerAxis() * brev();

        intakeSubsystem.setSpeed(pullerSpool);
      }, intakeSubsystem));

    shooterSubsystem.setDefaultCommand(
      new RunCommand(() -> {
        double shooterSpool = m_operatorController.getLeftTriggerAxis() * brev();
        //double pullTrigger = m_driverController.getRightTriggerAxis() == 1 ? 1 : 0;

        shooterSubsystem.startShootingSystem(shooterSpool);
        //shooterSubsystem.pullMotor(pullTrigger);

      }, shooterSubsystem)
    );

    // climbSubsystem.setDefaultCommand(
    //   new RunCommand(() -> {
    //     //System.out.println(m_driverController.getPOV());
    //     //if (m_driverController.getPOV() == 180)
    //     //  climbSubsystem.forwardMotor.set(1);
    //     //else if (m_driverController.getPOV() == 0)
    //     //  climbSubsystem.forwardMotor.set(-1);
    //     double out = 0;
    //     if (dpad_down())
    //       out++;
    //     if (dpad_up())
    //       out--;
    //     // climbSubsystem.forwardMotor.set(out);
    //     climbSubsystem.backwardMotor.set(out);
    //   }, climbSubsystem)
    // );
  }

  private boolean dpad_down() {
    return m_driverController.getPOV() == 180;
  }

  private boolean dpad_up() {
    return m_driverController.getPOV() == 0;
  }

  private int brev() {
    return m_operatorController.getBButton() ? -1 : 1;
  }

  private boolean isLeftDown() {
    return m_operatorController.getLeftTriggerAxis() != 0;
  }

  private void configureBindings() {
    //new Trigger(this::isSpooling)
    // .whileTrue(
    //    new Shoot(shooterSubsystem, m_driverController)
    //  );

    //new Trigger(m_driverController::getBButton)
    //.whileTrue(
    //  new Expunge(shooterSubsystem)
    //);

    // new Trigger(this::dpad_up)
    //   .whileTrue(
    //     new ClimbMovement(climbSubsystem, true)
    //   );
    
    // new Trigger(this::dpad_down)
    //   .whileTrue(
    //     new ClimbMovement(climbSubsystem, false)
    //   );

    new Trigger(m_operatorController::getLeftBumperButton)
      .whileTrue(
        new Pull(shooterSubsystem, m_operatorController)
      );

    new Trigger(m_operatorController::getRightBumperButton)
      .whileTrue(
        new MoveArmToPosition(intakeSubsystem, m_operatorController)
      );
  }

  public Command getAutonomousCommand(Boolean climb) {
    Command autoCommand = new Auto(driveSubsystem, shooterSubsystem, intakeSubsystem);
    return autoCommand;
  }
}