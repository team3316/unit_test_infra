package frc.robot.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.utils.Utils;

public class TestTimeDrive extends TestCommands {
    static Drivetrain m_drivetrain;
    static Victor m_leftMotor, m_rightMotor;

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
    public void testTimeDrive() {
        Command m_timeDriveCommand = new TimeDrive(m_drivetrain, 1.0, 0.5);
        Timer timer = (Timer) Utils.ReflectAndSpy(m_timeDriveCommand, "timer");
        when(timer.get()).thenReturn(0.0);
        
        runCommand(m_timeDriveCommand);
        assertEquals(Math.pow(0.5, 2), m_leftMotor.get(), 0.1);
        assertEquals(Math.pow(0.5, 2), m_rightMotor.get(), 0.1);

        assertFalse(m_timeDriveCommand.isFinished());

        when(timer.get()).thenReturn(0.5);
        Utils.runSchedualer(1);
        assertEquals(Math.pow(0.5, 2), m_leftMotor.get(), 0.1);
        assertEquals(Math.pow(0.5, 2), m_rightMotor.get(), 0.1);

        assertFalse(m_timeDriveCommand.isFinished());

        when(timer.get()).thenReturn(1.5);
        Utils.runSchedualer(1);
        assertEquals(0.0, m_leftMotor.get(), 0.0);
        assertEquals(0.0, m_rightMotor.get(), 0.0);

        assertTrue(m_timeDriveCommand.isFinished());
    }

}