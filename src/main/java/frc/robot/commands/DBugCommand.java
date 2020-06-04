package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An abstract command class which logs info about the command to the logger.
 */
public abstract class DBugCommand extends CommandBase {

  protected boolean wasCancelled = false;
  protected boolean hasFinished = false;

  @Override
  public final void initialize() {
    init();
  }

  public abstract void init ();

  @Override
  public abstract void execute();

  @Override
  public final boolean isFinished() {
    hasFinished = hasFinished || endCondition();
    return endCondition();
  }

  public abstract boolean endCondition();

  
  @Override
  public void cancel() {
    super.cancel();
    wasCancelled = true;
  }
  
  public final boolean wasCancelled() {
    return wasCancelled;
  };
  
  public final boolean hasFinished() {
    return hasFinished;
  }
  
  @Override
  public void end(boolean interrupted) {
    fin(interrupted);
  }

  protected abstract void fin (boolean interrupted);
}
