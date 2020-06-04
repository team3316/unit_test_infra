package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * A command class which gets a command as an argumet ang creates a DBugCommand from it
 */
public class DBugCommandWrapper extends DBugCommand {

  Command command;

  /**
   * Initialise a command wrapper.
   * Note: this is the command that will be executed.
   * The wrapped command will NOT be scheduled
   * @param m_moveUpCommand - the command to wrap
   */
  public DBugCommandWrapper(Command m_moveUpCommand) {
    this.command = m_moveUpCommand;
    addRequirements(m_moveUpCommand.getRequirements().toArray(new Subsystem[0]));
  }

  @Override
  public void init () {
    command.initialize();
  };

  @Override
  public void execute() {
    command.execute();
  };


  @Override
  public void end(boolean interrupted) {
    fin(interrupted);
  }
  
  @Override
  protected void fin(boolean interrupted) {
    command.end(interrupted);
  }

  @Override
  public boolean endCondition() {
    return command.isFinished();
  }
}
