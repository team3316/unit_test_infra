package frc.robot.commands.commandGroups;

import frc.robot.commands.DBugCommandGroup;
import frc.robot.commands.MoveArmToTarget;
import frc.robot.commands.SetIntakeRollers;
import frc.robot.subsystems.Arm.ArmState;

public class Intake extends DBugCommandGroup {

    public Intake() {
        addWaitParallel(() -> new MoveArmToTarget(ArmState.FEED),
            () -> new SetIntakeRollers(true));
        add(() -> new MoveArmToTarget(ArmState.UPPER));
    }
}