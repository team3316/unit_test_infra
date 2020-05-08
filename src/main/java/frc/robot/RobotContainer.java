/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveArmDown;
import frc.robot.commands.MoveArmUp;
import frc.robot.commands.TankDrive;
import frc.robot.commands.TimeDrive;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Arm.ArmState;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Arm m_arm = new Arm();

  // The autonomous routines

  // A simple auto routine that drives forward for a specified time, and then stops.
  private final Command m_simpleAuto =
      new TimeDrive(m_drivetrain, AutonConstants.kDriveTime, AutonConstants.kDriveSpeed);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  // The driver's controllers
  private final Joystick m_leftJoystick = new Joystick(OIConstants.kDriverLeftJoystickPort);
  private final Joystick m_rightJoystick = new Joystick(OIConstants.kDriverRightJoystickPort);
  private final XboxController m_gamepad = new XboxController(OIConstants.kDriverGamepadPort);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_arm.setTarget(ArmState.FEED);
    m_arm.setDefaultCommand(new MoveArm(m_arm));

    m_drivetrain.setDefaultCommand(
      new TankDrive(
        m_drivetrain,
        () -> m_leftJoystick.getY(),
        () -> m_rightJoystick.getY()));

    // Add commands to the autonomous command chooser
    m_chooser.setDefaultOption("Simple Auto", m_simpleAuto);

    // Put the chooser on the dashboard
    Shuffleboard.getTab("Autonomous").add(m_chooser);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Move arm down when 'A' button is pressed.
    new JoystickButton(m_gamepad, Button.kA.value)
        .whenPressed(new MoveArmDown(m_arm));
    // Move arm up when 'B' button is pressed.
    new JoystickButton(m_gamepad, Button.kB.value)
        .whenPressed(new MoveArmUp(m_arm));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
