package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class Shoot extends Command {
    private final ShooterSubsystem m_shooter;

    public Shoot(ShooterSubsystem shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override 
    public void initialize() {
        m_shooter.startShootingSystem(1.0);
    }

    @Override 
    public void end(boolean interrupted) {
        m_shooter.stopShootingSystem();
    }
}
