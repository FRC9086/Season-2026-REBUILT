// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.CANConstants;

import com.lumynlabs.domain.config.LumynDeviceConfig.Network;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private final CommandScheduler m_cmdScheduler = CommandScheduler.getInstance();
  private final Timer m_timer = new Timer();
  private final RobotContainer m_robotContainer;

  // Create PDH
  PowerDistribution m_pdh = new PowerDistribution(CANConstants.kPDHCanId, ModuleType.kRev);

  public static boolean teleop = false;
  private Boolean scheduledAuto = false;

  public Robot() {

    // Creating a network table for dashboard and more
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");

    // start camera server
    CameraServer.startAutomaticCapture();

    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    teleop = false;
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand(false);
    m_timer.restart();

    /*if (m_autonomousCommand != null) {
      // m_autonomousCommand.schedule();
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }*/
  }

  @Override
  public void autonomousPeriodic() {
    // Run auto movement & shooting
    if (!scheduledAuto) {
      m_cmdScheduler.schedule(m_autonomousCommand);
      scheduledAuto = true;
    }

    // Repeat climb command for 1.5 seconds (end of auto)
    if (m_timer.get() > 15.0 && m_timer.get() < 16.1 ) {
      Command climbCommand = m_robotContainer.getAutonomousCommand(true);
      m_cmdScheduler.schedule(climbCommand);
    }
  }

  @Override
  public void autonomousExit() {
    m_timer.stop();
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    teleop = true;
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}