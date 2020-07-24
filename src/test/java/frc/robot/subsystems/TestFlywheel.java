package frc.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.junit.BeforeClass;
import org.junit.Test;

import frc.robot.Util.DBugTalon;
import frc.robot.Util.InvalidStateException;
import frc.robot.subsystems.Flywheel.FlywheelStates;
import frc.utils.Utils;

public class TestFlywheel {
    
    static Flywheel _flywheel;
    static DBugTalon _motor;

    @BeforeClass
    public static void init() {
        _flywheel = new Flywheel();
        _motor = (DBugTalon) Utils.ReflectAndSpy(_flywheel, "_motor");
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

    @Test (expected = InvalidStateException.class)
    public void testSetSpeedException() throws InvalidStateException {
      _flywheel.setState(FlywheelStates.INTERMIDIET);
    }

    @Test
    public void testAtTarget() throws InvalidStateException {
        _flywheel.setState(FlywheelStates.LOWER_SHOOT);

        assertEquals(FlywheelStates.LOWER_SHOOT, _flywheel.getTarget());
    }

    @Test
    public void testSetVelocity() throws InvalidStateException {
      _flywheel.setState(FlywheelStates.SHOOT);
      assertEquals(_flywheel.getVelocity(), FlywheelStates.SHOOT.getVel(), 0.01);
    }

    @Test
    public void setDistanceTest() {
      _motor.set(ControlMode.Position, 245);
      assertEquals(_motor.getDistance(), 245, 0.1);
    }

}