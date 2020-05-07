package frc.robot.subsystems;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.Robot;
import frc.utils.Utils;


public class TestDrivetrain {
    static Victor motor;
    static AnalogPotentiometer pot;
    static DigitalInput upperLimitSwitch, lowerLimitSwitch;

    @BeforeClass
    public static void beforeClass() {
        motor = (Victor) Utils.GetPrivate(Robot.m_arm, "motor");
        pot = (AnalogPotentiometer) Utils.ReflectAndSpy(Robot.m_arm, "pot");
        upperLimitSwitch = (DigitalInput) Utils.ReflectAndSpy(Robot.m_arm, "upperLimitSwitch");
        lowerLimitSwitch = (DigitalInput) Utils.ReflectAndSpy(Robot.m_arm, "lowerLimitSwitch");
    }

    @Test
    public void testAtTarget() {
        when(pot.get()).thenReturn(Arm.ArmState.LOWER.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.LOWER);

        try {
			assertTrue(Robot.m_arm.atTarget());
		} catch (Arm.NoTargetException e) {
			fail();
        }
        
        when(pot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.LOWER);

        try {
			assertFalse(Robot.m_arm.atTarget());
		} catch (Arm.NoTargetException e) {
			fail();
		}
    }
    
    @Test(expected = Arm.NoTargetException.class)
    public void testNoTarget() throws Arm.NoTargetException {
        Robot.m_arm.setTarget(null);
        Robot.m_arm.atTarget();
    }

    @Test
    public void testSpeedToTarget() {
        when(pot.get()).thenReturn(Arm.ArmState.LOWER.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.UPPER);
        assertEquals(1.0, Robot.m_arm.getSpeedToTarget(), 0);
        
        when(pot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.LOWER);
        assertEquals(-1.0, Robot.m_arm.getSpeedToTarget(), 0);
        
        when(pot.get()).thenReturn(Arm.ArmState.FEED.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.FEED);
        assertEquals(0, Robot.m_arm.getSpeedToTarget(), 0);

        Robot.m_arm.setTarget(null);
        assertEquals(0, Robot.m_arm.getSpeedToTarget(), 0);
    }

    @Test
    public void testSetMotor() {
        when(lowerLimitSwitch.get()).thenReturn(false);
        when(upperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(0.0);
        assertEquals(0.0, motor.get(), 0);

        when(lowerLimitSwitch.get()).thenReturn(false);
        when(upperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(1.0);
        assertEquals(1.0, motor.get(), 0);

        when(lowerLimitSwitch.get()).thenReturn(false);
        when(upperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(-1.0);
        assertEquals(-1.0, motor.get(), 0);

        when(lowerLimitSwitch.get()).thenReturn(true);
        when(upperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(1.0);
        assertEquals(1.0, motor.get(), 0);

        when(lowerLimitSwitch.get()).thenReturn(false);
        when(upperLimitSwitch.get()).thenReturn(true);
        Robot.m_arm.setMotor(-1.0);
        assertEquals(-1.0, motor.get(), 0);

        when(lowerLimitSwitch.get()).thenReturn(true);
        when(upperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(-1.0);
        assertEquals(0.0, motor.get(), 0);

        when(lowerLimitSwitch.get()).thenReturn(false);
        when(upperLimitSwitch.get()).thenReturn(true);
        Robot.m_arm.setMotor(1.0);
        assertEquals(0.0, motor.get(), 0);
    }
}