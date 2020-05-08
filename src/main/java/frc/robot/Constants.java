/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Constants {
  public static final class AutonConstants {
    public static final double kDriveTime = 2.0; // seconds
    public static final double kDriveSpeed = 0.5;
  }
  
  public static final class DrivetrainConstants {
    public static final int kLeftMotorPort = 1;  // PWM
    public static final int kRightMotorPort = 2; // PWM
  }

  public static final class ArmConstants {
    public static final int kMotorPort = 3;            // PWM
    public static final int kPotentiometerPort = 1;    // AIO
    public static final int kUpperLimitSwitchPort = 1; // DIO
    public static final int kLowerLimitSwitchPort = 2; // DIO

    public static final double kFullRange = 270;
    public static final double kLowerPosition = 10;
    public static final double kFeedPosition = 30;
    public static final double kUpperPosition = 120;
    public static final double kPositionError = 5;
  }

  public static final class OIConstants {
    public static final int kDriverLeftJoystickPort = 1;
    public static final int kDriverRightJoystickPort = 2;
    public static final int kDriverGamepadPort = 3;
  }
}
