/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * An example command.  You can replace me with your own command.
 */
public class TankDrive extends CommandBase {
  Drivetrain m_drivetrain;
  DoubleSupplier leftSupplier, rightSupplier;

  public TankDrive(Drivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
    m_drivetrain = drivetrain;
    leftSupplier = left;
    rightSupplier = right;
    addRequirements(m_drivetrain);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_drivetrain.tankDrive(leftSupplier.getAsDouble(), rightSupplier.getAsDouble());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }
}
