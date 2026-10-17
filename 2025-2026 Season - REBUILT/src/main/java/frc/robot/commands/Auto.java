package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;


public class Auto extends SequentialCommandGroup {

    /*
        Start Positions: (Left to right in field)
        0. Left trench
        1. Left bump
        2. Hub
        3. Right bump
        4. Right trench
     */
    public Auto (DriveSubsystem drive, ShooterSubsystem shooter, IntakeSubsystem intake) {
        // Boolean[] buttons = {false, false, false, false};
        
        // Different values for each starting position
        double xSpeed = (Constants.AutoConstants.startPos != 2) ? 1 : 0;
        int xDir = (Constants.AutoConstants.startPos < 2) ? 1 : -1;
        double timeout = (Constants.AutoConstants.startPos == 2) 
            ? 0 : (Constants.AutoConstants.startPos % 2 == 0) ? 1 : 2;

        addCommands(
            // Drive Backwards
            new RunCommand(() -> drive.zeroHeading()).withTimeout(0.1),
            new RunCommand(() -> drive.drive(-0.5, 0.0, 0.0, false, true), drive).withTimeout(2.5),
            new RunCommand(() -> drive.drive(0.0, 0.0, 0.0, false, true), drive).withTimeout(0.5),

            // Move to hub based on start position
            // new RunCommand(() -> drive.drive(xSpeed * xDir, 0.0, 0.0, false, true), drive).withTimeout(timeout),

            // Shooter
            new RunCommand(() -> shooter.startShootingSystem(0.9), shooter).withTimeout(4.0),
            new RunCommand(() -> shooter.pullMotor(0.9), shooter).withTimeout(2.0),

            new InstantCommand(() -> {
                shooter.stopShootingSystem();
                shooter.stopPull();
            }, shooter).withTimeout(0.3)
            /*
            // Turn to face ladder
            new RunCommand(() -> drive.drive(0.0, 0.0, 1.0, false, true), drive).withTimeout(2.65),
            new WaitCommand(0.1),

            // Strafe and insert ladder
            new RunCommand(() -> drive.drive(0.3, 0.3, 0.0, false, true), drive).withTimeout(1.2), // xSpeed: -0.2 - time: 0.25, 1
            new RunCommand(() -> drive.drive(0.0, 0.5, 0.0, false, true), drive).withTimeout(2.5), // 1.15, 0.85, 1.80
            //new RunCommand(() -> drive.drive(0.0, 0.0, 0.5, false), drive).withTimeout(0.5),
            // new RunCommand(() -> drive.drive(0.0, 0.5, 0.0, false), drive).withTimeout(0.65), // 1
            // new RunCommand(() -> drive.drive(0.0, 0.0, 0.0, false), drive).withTimeout(0.1),

            // Wait to climb
            new WaitCommand(2.0)
             */
        );
    }
}