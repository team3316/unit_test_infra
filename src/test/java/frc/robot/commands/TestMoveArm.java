package frc.robot.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.commands.commandGroups.Intake;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.NoTargetException;
import frc.utils.Utils;

public class TestMoveArm extends TestCommands {
    static Arm m_arm;
    static Command m_moveUpCommand;
    static Command m_moveDownCommand;
    static Command m_moveCommand;

    @BeforeClass
    public static void initSubsystem() {
        m_arm = new Arm();
        Robot.arm =  m_arm;
        m_moveUpCommand = new MoveArmUp(m_arm);
        m_moveDownCommand = new MoveArmDown(m_arm);
        m_moveCommand = new MoveArm(m_arm);
    }

    @AfterClass
    public static void closeSubsystem() {
        m_arm.close();
    }

    @Test
    public void testMoveArmUp() throws NoTargetException {
        m_arm.setTarget(ArmState.FEED);
        runCommand(m_moveUpCommand);
        assertEquals(ArmState.UPPER, m_arm.getTarget());
        runCommand(m_moveUpCommand);
        assertEquals(ArmState.UPPER, m_arm.getTarget());
    }

    @Test
    public void cmdGroup() {
        System.out.println("here");
        Intake cmd = new Intake();
        TestCommands.runCommandGroup(cmd, true);
        while (!cmd.isFinished()) { }
        assertEquals(false, true);
    }

    @Test
    public void testMoveArmDown() throws NoTargetException {
        m_arm.setTarget(ArmState.FEED);
        runCommand(m_moveDownCommand);
        assertEquals(ArmState.LOWER, m_arm.getTarget());
        runCommand(m_moveDownCommand);
        assertEquals(ArmState.LOWER, m_arm.getTarget());
    }

    @Test(expected = NoTargetException.class)
    public void testMoveArmNull() throws NoTargetException {
        m_arm.setTarget(null);
        runCommand(m_moveUpCommand);
        runCommand(m_moveDownCommand);
        m_arm.getTarget();
    }

    @Test
    public void testMoveArm() {
        Victor motor = (Victor) Utils.GetPrivate(m_arm, "motor");
        AnalogPotentiometer mockedPot = (AnalogPotentiometer) Utils.ReflectAndSpy(m_arm, "pot");
        DigitalInput mockedUpperLimitSwitch = (DigitalInput) Utils.ReflectAndSpy(m_arm, "upperLimitSwitch");

        when(mockedPot.get()).thenReturn(Arm.ArmState.FEED.getPosition());
        when(mockedUpperLimitSwitch.get()).thenReturn(false);
        m_arm.setTarget(ArmState.UPPER);

        runCommand(m_moveCommand);

        assertEquals(1.0, motor.get(), 0);

        when(mockedPot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());
        runCommand(m_moveCommand);

        assertEquals(0.0, motor.get(), 0);
    }
}