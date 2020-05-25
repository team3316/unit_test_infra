package frc.robot.HumanIO;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.MoveArmToTarget;
import frc.robot.subsystems.Arm.ArmState;

public class Joysticks {
  private XboxController _operatorJoystick;

  public Joysticks () {
    this._operatorJoystick = new XboxController(3);
  }

  public double getLeftXboxY() {
    return this._operatorJoystick.getY(Hand.kLeft);
  }

  public double getRightXboxY() {
    return this._operatorJoystick.getY(Hand.kRight);
  }

  public double getLeftXboxTrigger() {
    return this._operatorJoystick.getTriggerAxis(Hand.kLeft);
  }

  public double getRightXboxTrigger() {
    return this._operatorJoystick.getTriggerAxis(Hand.kRight);
  }

  public void initButtons() {
    JoystickButton setFeeed = new JoystickButton(this._operatorJoystick, Button.kB.value);
    setFeeed.whenPressed(new MoveArmToTarget(ArmState.FEED));
  }
}