package frc.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.utils.Utils;


public class TestArm {
    static Arm m_arm;
    static WPI_VictorSPX m_motor;
    static AnalogPotentiometer m_pot;
    static DigitalInput m_upperLimitSwitch;
    static DigitalInput m_lowerLimitSwitch;

    @BeforeClass
    public static void initSubsystem() {
        m_arm = new Arm();
        m_motor = (WPI_VictorSPX) Utils.GetPrivate(m_arm, "motor");
        m_pot = (AnalogPotentiometer) Utils.ReflectAndSpy(m_arm, "pot");
        m_upperLimitSwitch = (DigitalInput) Utils.ReflectAndSpy(m_arm, "upperLimitSwitch");
        m_lowerLimitSwitch = (DigitalInput) Utils.ReflectAndSpy(m_arm, "lowerLimitSwitch");
    }

    @AfterClass
    public static void closeSubsystem() {
        m_arm.close();
    }

    @Test
    public void testAtTarget() {
        when(m_pot.get()).thenReturn(Arm.ArmState.LOWER.getPosition());

        m_arm.setTarget(Arm.ArmState.LOWER);

        try {
			assertTrue(m_arm.atTarget());
		} catch (Arm.NoTargetException e) {
			fail();
        }
        
        when(m_pot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());

        m_arm.setTarget(Arm.ArmState.LOWER);

        try {
			assertFalse(m_arm.atTarget());
		} catch (Arm.NoTargetException e) {
			fail();
		}
    }
    
    @Test(expected = Arm.NoTargetException.class)
    public void testNoTarget() throws Arm.NoTargetException {
        m_arm.setTarget(null);
        m_arm.atTarget();
    }

    @Test
    public void testSpeedToTarget() {
        when(m_pot.get()).thenReturn(Arm.ArmState.LOWER.getPosition());

        m_arm.setTarget(Arm.ArmState.UPPER);
        assertEquals(1.0, m_arm.getSpeedToTarget(), 0);
        
        when(m_pot.get()).thenReturn(Arm.ArmState.UPPER.getPosition());

        m_arm.setTarget(Arm.ArmState.LOWER);
        assertEquals(-1.0, m_arm.getSpeedToTarget(), 0);
        
        when(m_pot.get()).thenReturn(Arm.ArmState.FEED.getPosition());

        m_arm.setTarget(Arm.ArmState.FEED);
        assertEquals(0, m_arm.getSpeedToTarget(), 0);

        m_arm.setTarget(null);
        assertEquals(0, m_arm.getSpeedToTarget(), 0);
    }

    @Test
    public void testSetMotor() {
        when(m_upperLimitSwitch.get()).thenReturn(false);
        when(m_lowerLimitSwitch.get()).thenReturn(false);
        m_arm.setMotor(0.0);
        assertEquals(0.0, m_motor.get(), 0);

        when(m_upperLimitSwitch.get()).thenReturn(false);
        when(m_lowerLimitSwitch.get()).thenReturn(false);
        m_arm.setMotor(1.0);
        assertEquals(1.0, m_motor.get(), 0);

        when(m_upperLimitSwitch.get()).thenReturn(false);
        when(m_lowerLimitSwitch.get()).thenReturn(false);
        m_arm.setMotor(-1.0);
        assertEquals(-1.0, m_motor.get(), 0);

        when(m_upperLimitSwitch.get()).thenReturn(true);
        when(m_lowerLimitSwitch.get()).thenReturn(false);
        m_arm.setMotor(1.0);
        assertEquals(0.0, m_motor.get(), 0);

        when(m_upperLimitSwitch.get()).thenReturn(false);
        when(m_lowerLimitSwitch.get()).thenReturn(true);
        m_arm.setMotor(-1.0);
        assertEquals(0.0, m_motor.get(), 0);

        when(m_upperLimitSwitch.get()).thenReturn(true);
        when(m_lowerLimitSwitch.get()).thenReturn(false);
        m_arm.setMotor(-1.0);
        assertEquals(-1.0, m_motor.get(), 0);

        when(m_upperLimitSwitch.get()).thenReturn(false);
        when(m_lowerLimitSwitch.get()).thenReturn(true);
        m_arm.setMotor(1.0);
        assertEquals(1.0, m_motor.get(), 0);
    }
}