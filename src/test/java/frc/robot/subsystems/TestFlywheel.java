package frc.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import frc.robot.Util.DBugSparkMax;
import frc.robot.Util.DBugTalon;
import frc.robot.Util.InvalidStateException;
import frc.robot.subsystems.Flywheel.FlywheelStates;
import frc.utils.Utils;

public class TestFlywheel {
    
    static Flywheel _flywheel;
    static DBugTalon _motor1, _motor2;
    static DBugSparkMax _testMotor;

    @BeforeClass
    public static void init() {
        _flywheel = new Flywheel();
        _motor1 = (DBugTalon) Utils.ReflectAndSpy(_flywheel, "_motor1");
        _motor2 = (DBugTalon) Utils.ReflectAndSpy(_flywheel, "_motor2");
        _testMotor = new DBugSparkMax(24);
    }

    @Test
    public void testSetState() throws InvalidStateException {
      _flywheel.setState(FlywheelStates.LOWER_SHOOT);
      assertEquals(FlywheelStates.LOWER_SHOOT, _flywheel.getState());
    }
    @Test (expected = InvalidStateException.class)
    public void testSetSpeedException() throws InvalidStateException {
      _flywheel.setState(FlywheelStates.INTERMIDIET);
    }

    @Test
    public void testAtTarget() throws InvalidStateException {
        _flywheel.setState(FlywheelStates.SHOOT);

        assertEquals(FlywheelStates.SHOOT, _flywheel.getTarget());
    }

    @Test
    public void testSetVelocity() throws InvalidStateException {
      _flywheel.setState(FlywheelStates.SHOOT);
      assertEquals(_flywheel.get(), FlywheelStates.SHOOT.getVel(), 0.01);
    }

    @Test
    public void setDistanceTest() {
      _motor1.set(ControlMode.Position, 245);
      assertEquals(_motor1.getDistance(), 245, 0.1);
    }

    @Test
    public void testSpeedChange() {
        when(_motor1.get()).thenReturn((double) (FlywheelStates.SHOOT.getVel() - 5));

        assertEquals(_flywheel.getState(), FlywheelStates.SHOOT);

        when(_motor1.get()).thenReturn((double) (FlywheelStates.LOWER_SHOOT.getVel() + 7));

        assertEquals(_flywheel.getState(), FlywheelStates.LOWER_SHOOT);

        when(_motor1.get()).thenReturn((double) (FlywheelStates.LOWER_SHOOT.getVel() + 12));

        assertEquals(_flywheel.getState(), FlywheelStates.INTERMIDIET);

        Mockito.reset(_motor1);
    }

    @Test
    public void testSetDistance() {
      _testMotor.set(ControlMode.Position, 254);
      assertEquals(254, _testMotor.get(), 0.01);
    }

    @Test
    public void testSetVelocitySpark() {
      _testMotor.set(ControlMode.Velocity, 3316);
      assertEquals(3316, _testMotor.get(), 0.01);
    }

    @Test
    public void testSetPrecent() {
      _testMotor.set(ControlMode.Position, 0.7);
      assertEquals(0.7, _testMotor.get(), 0.01);
    }

    @Test
    public void testFollow() {
      DBugSparkMax _follower = new DBugSparkMax(254);
      _follower.follow(_testMotor);
      _testMotor.set(ControlMode.PercentOutput, 0.254);
      assertEquals(0.254, _follower.get(), 0.01);
      _follower.close();
    }
}