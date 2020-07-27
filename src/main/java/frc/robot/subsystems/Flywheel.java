package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils;
import frc.robot.Util.DBugTalon;
import frc.robot.Util.InvalidStateException;

public class Flywheel extends SubsystemBase {

    public enum FlywheelStates {
        
        NONE(0.0), SHOOT(100), LOWER_SHOOT(50), INTERMIDIET(0.0);

        private double _vel;

        private FlywheelStates(double vel) {
            this._vel = vel;
        }

        public double getVel() {
            return this._vel;
        }

    }

    private DBugTalon _motor;
    private FlywheelStates _target;

    public Flywheel() {
        this._motor = new DBugTalon(2);
        this._target = FlywheelStates.NONE;

        this._motor.setupPIDF(2, 0, 24, 0.1);
        this._motor.setInverted(true);
        this._motor.setSensorPhase(false);
        this._motor.setDistancePerRevolution(4 / 256, 1024);
    }

    public void setState(FlywheelStates state) throws InvalidStateException {
        if (state == FlywheelStates.INTERMIDIET) throw new InvalidStateException();
        else if (state == this.getState() || state == this.getTarget()) return;
        this._motor.set(ControlMode.Velocity, state._vel);
        this._target = state;
    }

    public FlywheelStates getState() {
        double vel = this._motor.getSelectedSensorVelocity();
        if (Utils.inRange(vel, FlywheelStates.NONE.getVel(), 10)) return FlywheelStates.NONE;
        else if (Utils.inRange(vel, FlywheelStates.LOWER_SHOOT.getVel(), 10)) return FlywheelStates.LOWER_SHOOT;
        else if (Utils.inRange(vel, FlywheelStates.SHOOT.getVel(), 10)) return FlywheelStates.SHOOT;
        else return FlywheelStates.INTERMIDIET;
    }

    public FlywheelStates getTarget() {
        return this._target;
    }

    /**
     * Set the velocity
     * @param vel
     *  Position change for every 100ms
     */
    public void setVelocity(double vel) {
        this._motor.set(ControlMode.Velocity, vel);
    }

    public double get() {
        return this._motor.get();
    }

}