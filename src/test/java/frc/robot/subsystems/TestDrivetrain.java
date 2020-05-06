package frc.robot.subsystems;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.Robot;


public class TestDrivetrain {
    static Victor mockedMotor;
    static AnalogPotentiometer mockedPot;
    static DigitalInput mockedUpperLimitSwitch, mockedLowerLimitSwitch;

    @BeforeClass
    public static void beforeClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
       
        Field motorField = Arm.class.getDeclaredField("motor");
        motorField.setAccessible(true);
        Field potField = Arm.class.getDeclaredField("pot");
        potField.setAccessible(true);
        Field upperLimitSwitchField = Arm.class.getDeclaredField("upperLimitSwitch");
        upperLimitSwitchField.setAccessible(true);
        Field lowerLimitSwitchField = Arm.class.getDeclaredField("lowerLimitSwitch");
        lowerLimitSwitchField.setAccessible(true);

        mockedMotor = mock(Victor.class);
        motorField.set(Robot.m_arm, mockedMotor);
        mockedPot = mock(AnalogPotentiometer.class);
        potField.set(Robot.m_arm, mockedPot);
        mockedUpperLimitSwitch = mock(DigitalInput.class);
        upperLimitSwitchField.set(Robot.m_arm, mockedUpperLimitSwitch);
        mockedLowerLimitSwitch = mock(DigitalInput.class);
        lowerLimitSwitchField.set(Robot.m_arm, mockedLowerLimitSwitch);
        
    }

    @Test
    public void testAtTarget() {
        when(mockedPot.get()).thenReturn(Arm.ArmState.LOWER.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.LOWER);

        try {
			assertTrue(Robot.m_arm.atTarget());
		} catch (Arm.NoTargetException e) {
			fail();
        }
        
        when(mockedPot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());

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
        when(mockedPot.get()).thenReturn(Arm.ArmState.LOWER.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.UPPER);
        assertEquals(1.0, Robot.m_arm.getSpeedToTarget(), 0);
        
        when(mockedPot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.LOWER);
        assertEquals(-1.0, Robot.m_arm.getSpeedToTarget(), 0);
        
        when(mockedPot.get()).thenReturn(Arm.ArmState.FEED.getPosition());

        Robot.m_arm.setTarget(Arm.ArmState.FEED);
        assertEquals(0, Robot.m_arm.getSpeedToTarget(), 0);

        Robot.m_arm.setTarget(null);
        assertEquals(0, Robot.m_arm.getSpeedToTarget(), 0);
    }

    @Test
    public void testSetMotor() {
        when(mockedLowerLimitSwitch.get()).thenReturn(false);
        when(mockedUpperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(0.0);
        verify(mockedMotor, times(1)).set(0.0);

        when(mockedLowerLimitSwitch.get()).thenReturn(false);
        when(mockedUpperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(1.0);
        verify(mockedMotor, times(1)).set(1.0);

        when(mockedLowerLimitSwitch.get()).thenReturn(false);
        when(mockedUpperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(-1.0);
        verify(mockedMotor, times(1)).set(-1.0);

        when(mockedLowerLimitSwitch.get()).thenReturn(true);
        when(mockedUpperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(1.0);
        verify(mockedMotor, times(2)).set(1.0);

        when(mockedLowerLimitSwitch.get()).thenReturn(false);
        when(mockedUpperLimitSwitch.get()).thenReturn(true);
        Robot.m_arm.setMotor(-1.0);
        verify(mockedMotor, times(2)).set(-1.0);

        when(mockedLowerLimitSwitch.get()).thenReturn(true);
        when(mockedUpperLimitSwitch.get()).thenReturn(false);
        Robot.m_arm.setMotor(-1.0);
        verify(mockedMotor, times(2)).set(0.0);

        when(mockedLowerLimitSwitch.get()).thenReturn(false);
        when(mockedUpperLimitSwitch.get()).thenReturn(true);
        Robot.m_arm.setMotor(1.0);
        verify(mockedMotor, times(3)).set(0.0);
    }
}