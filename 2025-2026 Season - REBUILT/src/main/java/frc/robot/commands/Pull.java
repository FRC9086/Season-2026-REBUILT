package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class Pull extends Command {
    private final ShooterSubsystem m_shooter;
    private final boolean m_reverse;

    public Pull(ShooterSubsystem shooter, boolean reverse) {
        m_shooter = shooter;
        m_reverse = reverse;
        addRequirements(m_shooter);
    }

    @Override 
    public void initialize() {
        m_shooter.pullMotor(m_reverse ? -1.0 : 1.0);
    }

    @Override 
    public void end(boolean interrupted) {
        m_shooter.stopPull();
    }
}