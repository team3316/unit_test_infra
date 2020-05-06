/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.MoveArmDown;
import frc.robot.commands.MoveArmUp;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  public Joystick leftStick;
  public Joystick rightStick;

  public XboxController gamepad;
  
  JoystickButton armUpButton, armDownButton;

  public OI () {
    leftStick = new Joystick(0);
    rightStick = new Joystick(1);
    gamepad = new XboxController(2);
    armUpButton = new JoystickButton(gamepad, 1);
    armUpButton.whenPressed(new MoveArmUp());
    armDownButton = new JoystickButton(gamepad, 2);
    armDownButton.whenPressed(new MoveArmDown());
  }
}
