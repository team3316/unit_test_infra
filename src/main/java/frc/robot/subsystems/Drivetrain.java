/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drivetrain extends SubsystemBase {
  Victor leftMotor;
  Victor rightMotor;
  DifferentialDrive mDrive;

  public Drivetrain() {
    leftMotor = new Victor(DrivetrainConstants.kLeftMotorPort);
    rightMotor = new Victor(DrivetrainConstants.kRightMotorPort);
    rightMotor.setInverted(true);
    mDrive = new DifferentialDrive(leftMotor, rightMotor);
    mDrive.setRightSideInverted(true);
  }

  public void close() {
    leftMotor.close();
    rightMotor.close();
    mDrive.close();
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    mDrive.tankDrive(leftSpeed, rightSpeed);
  }
}
