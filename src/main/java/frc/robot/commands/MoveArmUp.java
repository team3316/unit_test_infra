/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.NoTargetException;

/**
 * An example command.  You can replace me with your own command.
 */
public class MoveArmUp extends CommandBase {
  Arm m_arm;

  public MoveArmUp(Arm arm) {
    m_arm = arm;
  }
  
  @Override
  public void initialize() {
    ArmState target;

    try {
      target = m_arm.getTarget();
    } catch (NoTargetException e) {
      return;
    }

    switch (target) {
    case LOWER:
      m_arm.setTarget(ArmState.FEED);
      break;
    case FEED:
      m_arm.setTarget(ArmState.UPPER);
      break;
    default:
      // Don't move arm
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return true;
  }
}
