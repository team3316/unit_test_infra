package frc.robot.subsystems;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.Victor;
import frc.utils.Utils;

public class TestDrivetrain {
    static Victor m_leftMotor, m_rightMotor;
    static Drivetrain m_drivetrain;

    @BeforeClass
    public static void initSubsystem() {
        m_drivetrain = new Drivetrain();
        m_leftMotor = (Victor) Utils.GetPrivate(m_drivetrain, "leftMotor");
        m_rightMotor = (Victor) Utils.GetPrivate(m_drivetrain, "rightMotor");
    }

    @AfterClass
    public static void closeSubsystem() {
        m_drivetrain.close();
    }

    @Test
    public void testTankDrive() {
        m_drivetrain.tankDrive(1.0, -1.0);
        assertEquals(1.0, m_leftMotor.get(), 0);
        assertEquals(-1.0, m_rightMotor.get(), 0);

        m_drivetrain.tankDrive(-4.0, 0.0);
        assertEquals(-1.0, m_leftMotor.get(), 0);
        assertEquals(0.0, m_rightMotor.get(), 0);

        m_drivetrain.tankDrive(0.0, 0.2);
        assertEquals(0.0, m_leftMotor.get(), 0);
        assertEquals(Math.pow(0.2, 2), m_rightMotor.get(), 0.01); // Squared inputs
    }

}