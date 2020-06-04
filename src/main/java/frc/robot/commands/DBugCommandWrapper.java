package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A command class which gets a command as an argumet ang creates a DBugCommand from it
 */
public class DBugCommandWrapper extends DBugCommand {

  CommandBase command;

  /**
   * Initialise a command wrapper.
   * Note: this is the command that will be executed.
   * The wrapped command will NOT be scheduled
   * @param command - the command to wrap
   */
  public DBugCommandWrapper(CommandBase command) {
    this.command = command;
  }

  public void init () {
    command.initialize();
  };

  public void execute() {
    command.execute();
  };

  @Override
  public void cancel() {
    super.cancel();
  }

  @Override
  public void end(boolean interrupted) {
    command.end(interrupted);
    fin(interrupted);
  }

  @Override
  protected void fin(boolean interrupted) {
    
  }

  @Override
  public boolean endCondition() {
    return command.isFinished();
  }
}
