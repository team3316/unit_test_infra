package frc.robot.commands.commandGroups;

import frc.robot.commands.DBugCommandGroup;
import frc.robot.commands.DBugCommandWrapper;
import frc.robot.commands.MoveArmToTarget;
import frc.robot.commands.SetIntakeRollers;
import frc.robot.subsystems.Arm.ArmState;

public class Intake extends DBugCommandGroup {

    public Intake() {
        addWaitParallel(() -> new DBugCommandWrapper(new MoveArmToTarget(ArmState.FEED)),
            () -> new DBugCommandWrapper(new SetIntakeRollers(true)));
        add(() -> new DBugCommandWrapper(new MoveArmToTarget(ArmState.UPPER)));
    }
}