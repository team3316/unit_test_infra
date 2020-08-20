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

    private DBugTalon _motor1, _motor2;
    private FlywheelStates _target;

    public Flywheel() {
        this._motor1 = new DBugTalon(4);
        this._motor2 = new DBugTalon(5);
        this._motor2.follow(this._motor1);
        this._target = FlywheelStates.NONE;

        this._motor1.setupPIDF(2, 0, 24, 0.1);
        this._motor1.setInverted(true);
        this._motor1.setSensorPhase(false);
        this._motor1.setDistancePerRevolution(4 / 256, 1024);
    }

    public void setState(FlywheelStates state) throws InvalidStateException {
        if (state == FlywheelStates.INTERMIDIET) throw new InvalidStateException();
        else if (state == this.getState() || state == this.getTarget()) return;
        this._motor1.set(ControlMode.Velocity, state._vel);
        this._target = state;
    }

    public FlywheelStates getState() {
        double vel = this._motor1.getVelocity();
        if (Utils.inRange(vel, FlywheelStates.NONE.getVel(), 10)) return FlywheelStates.NONE;
        else if (Utils.inRange(vel, FlywheelStates.LOWER_SHOOT.getVel(), 10)) return FlywheelStates.LOWER_SHOOT;
        else if (Utils.inRange(vel, FlywheelStates.SHOOT.getVel(), 10)) return FlywheelStates.SHOOT;
        else return FlywheelStates.INTERMIDIET;
    }

    public FlywheelStates getTarget() {
        return this._target;
    }

    public double getDemand() {
        return this._motor1.getDemand();
    }

}