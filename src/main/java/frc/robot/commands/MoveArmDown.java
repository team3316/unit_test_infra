/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.NoTargetException;

/**
 * An example command.  You can replace me with your own command.
 */
public class MoveArmDown extends Command {

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    ArmState target;
    
    try {
      target = Robot.m_arm.getTarget();
    } catch (NoTargetException e) {
      return;
    }
    
    switch(target) {
      case FEED:
        Robot.m_arm.setTarget(ArmState.LOWER);
        break;
      case UPPER:
        Robot.m_arm.setTarget(ArmState.FEED);
        break;
      default:
        // Don't move arm
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }
}
