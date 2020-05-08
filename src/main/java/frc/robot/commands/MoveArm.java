/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

/**
 * An example command.  You can replace me with your own command.
 */
public class MoveArm extends CommandBase {
  Arm m_arm;
  
  public MoveArm(Arm arm) {
    m_arm = arm;
    addRequirements(m_arm);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_arm.setMotor(m_arm.getSpeedToTarget());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }
}
