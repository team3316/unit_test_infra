/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * An example command.  You can replace me with your own command.
 */
public class TimeDrive extends CommandBase {
  Timer timer = new Timer();
  Drivetrain m_drivetrain;
  double driveTime, driveSpeed;

  public TimeDrive(Drivetrain drivetrain, double time, double speed) {
    m_drivetrain = drivetrain;
    driveTime = time;
    driveSpeed = speed;
    addRequirements(m_drivetrain);
  }

  @Override
  public void initialize() {
    timer.start();
    m_drivetrain.tankDrive(driveSpeed, driveSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.tankDrive(0.0, 0.0);
    timer.stop();
    timer.reset();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return timer.get() > driveTime;
  }
}
