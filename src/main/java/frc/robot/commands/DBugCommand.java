package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An abstract command class which logs info about the command to the logger.
 */
public abstract class DBugCommand extends CommandBase {

  @Override
  public final void initialize() {
    init();
  }

  public abstract void init ();

  @Override
  public abstract void execute();

  @Override
  public abstract boolean isFinished();

  @Override
  public void end(boolean interrupted) {
    fin(interrupted);
  }

  protected abstract void fin (boolean interrupted);
}
