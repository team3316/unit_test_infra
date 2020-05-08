package frc.robot.commands;

import static org.junit.Assert.assertEquals;

import java.util.function.DoubleSupplier;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.utils.Utils;

public class TestTankDrive extends TestCommands {
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

    class MockSupplier implements DoubleSupplier {
        public double value = 0.0;

        @Override
        public double getAsDouble() {
            return value;
        }
    }
    
    @Test
    public void testTankDrive() {
        MockSupplier leftSupplier = new MockSupplier();
        MockSupplier rightSupplier = new MockSupplier();

        Command m_tankDriveCommand = new TankDrive(m_drivetrain, leftSupplier, rightSupplier);
        
        // Case 1: Don't move
        leftSupplier.value = 0.0;
        rightSupplier.value = 0.0;
        runCommand(m_tankDriveCommand);

        assertEquals(0.0, m_leftMotor.get(), 0);
        assertEquals(0.0, m_rightMotor.get(), 0);
        
        // Case 2: Turn right in place
        leftSupplier.value = 1.0;
        rightSupplier.value = -1.0;
        runCommand(m_tankDriveCommand);

        assertEquals(1.0, m_leftMotor.get(), 0);
        assertEquals(-1.0, m_rightMotor.get(), 0);
        
        // Case 3: Turn left and back
        leftSupplier.value = 0.0;
        rightSupplier.value = -1.0;
        runCommand(m_tankDriveCommand);

        assertEquals(0.0, m_leftMotor.get(), 0);
        assertEquals(-1.0, m_rightMotor.get(), 0);
        
        // Case 4: Drive straight
        leftSupplier.value = 1.0;
        rightSupplier.value = 1.0;
        runCommand(m_tankDriveCommand);

        assertEquals(1.0, m_leftMotor.get(), 0);
        assertEquals(1.0, m_rightMotor.get(), 0);
    }

}