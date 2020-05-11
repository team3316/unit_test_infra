package frc.robot.HumanIO;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.commands.MoveArmToTarget;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.NoTargetException;
import frc.utils.Utils;

public class TestJoysticks {

    @BeforeClass
    public static void init() {
        Robot.arm = new Arm();
        Robot.joysticks = new Joysticks();
        Robot.joysticks.initButtons();
    }

    @AfterClass
    public static void end() {
        Robot.arm.close();
    }

    @Test
    public void testFeed() throws NoTargetException {
        XboxController joystick = (XboxController) Utils.GetPrivate(Robot.joysticks, "_operatorJoystick");
        JoystickButton setFeeed = spy(new JoystickButton(joystick, 2));
    
        setFeeed.whenPressed(new MoveArmToTarget(ArmState.FEED));

        when(setFeeed.get()).thenReturn(true);

        Utils.runSchedualer(1);

        assertEquals(ArmState.FEED, Robot.arm.getTarget());

    }

}