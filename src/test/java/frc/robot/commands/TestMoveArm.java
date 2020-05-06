package frc.robot.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.NoTargetException;
import frc.utils.Utils;

public class TestMoveArm extends TestCommands {
    @BeforeClass
    public static void initSubsystem() {
        if (Robot.m_arm == null)
            Robot.m_arm = new Arm();
    }

    @Test
    public void testMoveArmUp() throws NoTargetException {
        Robot.m_arm.setTarget(ArmState.FEED);
        runCommand(new MoveArmUp());
        assertEquals(ArmState.UPPER, Robot.m_arm.getTarget());
        runCommand(new MoveArmUp());
        assertEquals(ArmState.UPPER, Robot.m_arm.getTarget());
    }

    @Test
    public void testMoveArmDown() throws NoTargetException {
        Robot.m_arm.setTarget(ArmState.FEED);
        runCommand(new MoveArmDown());
        assertEquals(Robot.m_arm.getTarget(), ArmState.LOWER);
        runCommand(new MoveArmDown());
        assertEquals(Robot.m_arm.getTarget(), ArmState.LOWER);
    }

    @Test(expected = NoTargetException.class)
    public void testMoveArmNull() throws NoTargetException {
        Robot.m_arm.setTarget(null);
        runCommand(new MoveArmUp());
        runCommand(new MoveArmDown());
        assertEquals(Robot.m_arm.getTarget(), null);
    }

    @Test
    public void testMoveArm() {
        Victor motor = (Victor) Utils.GetPrivate(Robot.m_arm, "motor");
        AnalogPotentiometer mockedPot = (AnalogPotentiometer) Utils.ReflectAndSpy(Robot.m_arm, "pot");
        DigitalInput mockedUpperLimitSwitch = (DigitalInput) Utils.ReflectAndSpy(Robot.m_arm, "upperLimitSwitch");

        when(mockedPot.get()).thenReturn(Arm.ArmState.FEED.getPosition());
        when(mockedUpperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setTarget(ArmState.UPPER);

        runDefaultCommand(Robot.m_arm);

        assertEquals(1.0, motor.get(), 0);

        when(mockedPot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());
        runDefaultCommand(Robot.m_arm);

        assertEquals(0.0, motor.get(), 0);
    }
}