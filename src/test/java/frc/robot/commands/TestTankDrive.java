package frc.robot.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import frc.utils.Utils;

public class TestTankDrive extends TestCommands {
    @BeforeClass
    public static void initSubsystem() {
        if (Robot.m_drivetrain == null)
            Robot.m_drivetrain = new Drivetrain();
        if (Robot.m_oi == null)
            Robot.m_oi = new OI();
    }

    @Test
    public void testTankDrive() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Joystick leftStick = (Joystick) Utils.ReflectAndSpy(Robot.m_oi, "leftStick");
        Joystick rightStick = (Joystick) Utils.ReflectAndSpy(Robot.m_oi, "rightStick");
        Victor leftMotor = (Victor) Utils.GetPrivate(Robot.m_drivetrain, "leftMotor");
        Victor rightMotor = (Victor) Utils.GetPrivate(Robot.m_drivetrain, "rightMotor");
        
        // Case 1: Don't move
        when(leftStick.getY()).thenReturn(0.0);
        when(rightStick.getY()).thenReturn(0.0);
        runDefaultCommand(Robot.m_drivetrain);

        assertEquals(0.0, leftMotor.get(), 0);
        assertEquals(0.0, rightMotor.get(), 0);
        
        // Case 2: Turn right in place
        when(leftStick.getY()).thenReturn(1.0);
        when(rightStick.getY()).thenReturn(-1.0);
        runDefaultCommand(Robot.m_drivetrain);

        assertEquals(1.0, leftMotor.get(), 0);
        assertEquals(-1.0, rightMotor.get(), 0);
        
        // Case 3: Turn left and back
        when(leftStick.getY()).thenReturn(0.0);
        when(rightStick.getY()).thenReturn(-1.0);
        runDefaultCommand(Robot.m_drivetrain);

        assertEquals(0.0, leftMotor.get(), 0);
        assertEquals(-1.0, rightMotor.get(), 0);
        
        // Case 4: Drive straight
        when(leftStick.getY()).thenReturn(1.0);
        when(rightStick.getY()).thenReturn(1.0);
        runDefaultCommand(Robot.m_drivetrain);

        assertEquals(1.0, leftMotor.get(), 0);
        assertEquals(1.0, rightMotor.get(), 0);
    }

}