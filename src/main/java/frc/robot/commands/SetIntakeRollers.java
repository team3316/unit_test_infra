package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetIntakeRollers extends CommandBase {

    boolean state;

    public SetIntakeRollers(boolean wantedState) {
        this.state = wantedState;
    }

    @Override
    public void initialize() {
        Robot.intakeRollers = this.state;
    }

    @Override
    public void execute() { }

    @Override
    public boolean isFinished() {
        return true;
    }
}