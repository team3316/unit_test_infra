package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Arm.ArmState;

public class MoveArmToTarget extends CommandBase {
 
    ArmState wantedState;

    public MoveArmToTarget(ArmState wanted) {
        this.wantedState = wanted;
        addRequirements(Robot.arm);
    }

    @Override
    public void initialize() {
        Robot.arm.setTarget(this.wantedState);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}