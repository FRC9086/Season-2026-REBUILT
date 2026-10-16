package frc.robot.commands;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends SequentialCommandGroup {
    public Shoot(ShooterSubsystem shooter, PS5Controller controller) {
        addCommands(
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new StartEndCommand(
                        () -> shooter.startShootingSystem(controller.getL2Axis()),
                        shooter::stopShootingSystem
                    ).withTimeout(1),
                    new WaitCommand(1)
                )
            )
        );
    }
}