package frc.robot.HumanIO;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.NoTargetException;

public class TestJoysticks {

    @BeforeClass
    public static void init() {
        Robot.arm = new Arm();
    }

    @AfterClass
    public static void end() {
        Robot.arm.close();
    }

    @Test
    public void testFeed() throws NoTargetException {
        JoystickUtil.getInstance().runButton(Button.kB);

        assertEquals(ArmState.FEED, Robot.arm.getTarget());
    }



}