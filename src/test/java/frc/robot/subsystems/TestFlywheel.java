package frc.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.junit.BeforeClass;
import org.junit.Test;

import frc.robot.Util.InvalidStateException;
import frc.robot.subsystems.Flywheel.FlywheelStates;
import frc.utils.Utils;

public class TestFlywheel {
    
    static Flywheel _flywheel;
    static WPI_TalonSRX _motor;

    @BeforeClass
    public static void init() {
        _flywheel = new Flywheel();
        _motor = (WPI_TalonSRX) Utils.ReflectAndSpy(_flywheel, "_motor");
    }

    @Test
    public void testSpeedChange() {
        when(_motor.getSelectedSensorVelocity()).thenReturn((int) (FlywheelStates.SHOOT.getVel() - 5));

        assertEquals(_flywheel.getState(), FlywheelStates.SHOOT);

        when(_motor.getSelectedSensorVelocity()).thenReturn((int) (FlywheelStates.LOWER_SHOOT.getVel() + 7));

        assertEquals(_flywheel.getState(), FlywheelStates.LOWER_SHOOT);

        when(_motor.getSelectedSensorVelocity()).thenReturn((int) (FlywheelStates.LOWER_SHOOT.getVel() + 12));

        assertEquals(_flywheel.getState(), FlywheelStates.INTERMIDIET);
    }

    @Test
    public void testSetSpeed() {
        boolean sucsec = true;
        try {
            _flywheel.setState(FlywheelStates.INTERMIDIET);
            sucsec = false;
        } catch (InvalidStateException e) { }
        try {
            when(_motor.getSelectedSensorVelocity()).thenReturn((int) (FlywheelStates.SHOOT.getVel()));
            _flywheel.setState(FlywheelStates.SHOOT);
            sucsec = false;
        } catch (InvalidStateException e) { }
        try {
            _flywheel.setState(FlywheelStates.LOWER_SHOOT);
            _flywheel.setState(FlywheelStates.LOWER_SHOOT);
            sucsec = false;
        } catch (InvalidStateException e) { }
        assertTrue(sucsec);
    }

    @Test
    public void testAtTarget() throws InvalidStateException {
        _flywheel.setState(FlywheelStates.LOWER_SHOOT);

        assertEquals(FlywheelStates.LOWER_SHOOT, _flywheel.getTarget());
    }

}