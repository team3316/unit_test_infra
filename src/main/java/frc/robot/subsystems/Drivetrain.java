/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.TankDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  Victor leftMotor;
  Victor rightMotor;
  DifferentialDrive mDrive;

  public Drivetrain() {
    leftMotor = new Victor(RobotMap.leftMotor);
    rightMotor = new Victor(RobotMap.rightMotor);
    rightMotor.setInverted(true);
    mDrive = new DifferentialDrive(leftMotor, rightMotor);
    mDrive.setRightSideInverted(true);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new TankDrive());
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    mDrive.tankDrive(leftSpeed, rightSpeed);
  }
}
