/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils;
import frc.robot.Constants.ArmConstants;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Arm extends SubsystemBase {
  private WPI_VictorSPX motor;
  private AnalogPotentiometer pot;
  private DigitalInput upperLimitSwitch, lowerLimitSwitch;
  private ArmState targetState;
  
  public Arm() {
    motor = new WPI_VictorSPX(ArmConstants.kMotorPort);
    pot = new AnalogPotentiometer(ArmConstants.kPotentiometerPort, ArmConstants.kFullRange);
    upperLimitSwitch = new DigitalInput(ArmConstants.kUpperLimitSwitchPort);
    lowerLimitSwitch = new DigitalInput(ArmConstants.kLowerLimitSwitchPort);
  }

  public void close() {
    //motor.close(); so apperantly you dont need to close the object when you use the WPI one 
    pot.close();
    upperLimitSwitch.close();
    lowerLimitSwitch.close();
  }

  public enum ArmState {
    LOWER(ArmConstants.kLowerPosition), FEED(ArmConstants.kFeedPosition), UPPER(ArmConstants.kUpperPosition);

    private final double pos;

    ArmState(double pos) {
      this.pos = pos;
    }

    public double getPosition() {
      return this.pos;
    }

  }

  public class NoTargetException extends Exception {

    private static final long serialVersionUID = 1L;
  }

  public boolean atTarget() throws NoTargetException {
    if (targetState == null)
      throw new NoTargetException();

    double value = pot.get();
    double target = targetState.getPosition();
    return Utils.inRange(value, target, ArmConstants.kPositionError);
  }

  boolean atLimit(double speed) {
    return ((speed > 0) && upperLimitSwitch.get()) || ((speed < 0) && lowerLimitSwitch.get());
  }

  public double getSpeedToTarget() {
    try {
      if (!atTarget())
        return Math.signum(targetState.getPosition() - pot.get());
    } catch (NoTargetException e) { }

    return 0;
  }

  public void setMotor(double speed) {
    if (atLimit(speed)) {
      motor.set(0.0);
    } else {
      motor.set(speed);
    }
  }

  public void setTarget(ArmState state) {
    targetState = state;
  }

  public ArmState getTarget() throws NoTargetException {
    if (targetState == null)
      throw new NoTargetException();

    return targetState;
  }
}
