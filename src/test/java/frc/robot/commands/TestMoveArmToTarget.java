package frc.robot.commands;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.NoTargetException;

public class TestMoveArmToTarget {

    CommandBase cmd;

    @BeforeClass
    public static void init() {
        Robot.arm = new Arm();
    }

    @AfterClass
    public static void end() {
        Robot.arm.close();
    }

    @Test
    public void testMoveArmToTarget() throws NoTargetException {
        this.cmd = new MoveArmToTarget(ArmState.FEED);
        TestCommands.runCommand(this.cmd);
        assertEquals(ArmState.FEED, Robot.arm.getTarget());

        this.cmd = new MoveArmToTarget(ArmState.LOWER);
        TestCommands.runCommand(this.cmd);
        assertEquals(ArmState.LOWER, Robot.arm.getTarget());

        
        this.cmd = new MoveArmToTarget(ArmState.UPPER);
        TestCommands.runCommand(this.cmd);
        assertEquals(ArmState.UPPER, Robot.arm.getTarget());
    }

}