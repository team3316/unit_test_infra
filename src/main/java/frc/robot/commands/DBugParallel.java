package frc.robot.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * DBugCommandGroup
 */
public abstract class DBugParallel extends DBugCommand {
    protected List<Supplier<DBugCommand>> commandSupppliers;
    protected List<DBugCommand> commands;
    protected boolean isFinished = false;

    /**
     * Constructor - will be utilised in most sub-classes
     * 
     * @param args - commands to initialize in this parrallel group
     */
    public DBugParallel(List<Supplier<DBugCommand>> cmds) {
        if (cmds.size() <= 0) {
            throw new IllegalArgumentException("Tried to initialize an empty parallel");
        } else {
            commands = new LinkedList<>();
            commandSupppliers = List.copyOf(cmds);
        }
    }

    /**
     * @return The command group's representation of the commands that shall run -
     *         in the hierarchy
     */
    public List<Supplier<DBugCommand>> getStorage() {
        return this.commandSupppliers;
    }

    /**
     * starts the sequence
     */
    @Override
    public synchronized void init() {
        this._start();
    }

    /**
     * runs the parallel sequence of command that should run
     */
    protected void _start() {
        for (Supplier<DBugCommand> sup : commandSupppliers) {
            DBugCommand cmd = sup.get();
            cmd.schedule();
            commands.add(cmd);
        }
    }

    /**
     * @return whether the command's end condition has been reached.
     */
    @Override
    public final boolean endCondition() {
        return isFinished;
    }

    /**
     * runs before the <code>end(boolean interrupted)</code> function.
     * 
     * @param interrupted - whether the command was interrupted or ended naturally.
     */
    @Override
    protected void fin(boolean interrupted) {
        commands.stream().filter(cmd -> cmd.isScheduled()).forEach(cmd -> cmd.cancel());
    }

    @Override
    public String toString() {
        String res = this.getClass().getName() + ":\n\t"
                + commandSupppliers.stream().map(supp -> supp.get().toString()).collect(Collectors.joining("\n\t"));

        return res;
    }

    /**
     * Chack if any of the commands has been cancelled - if so, cancel the sequence
     */
    @Override
    public void execute() {
        if (commands.stream().anyMatch((cmd) -> cmd.wasCancelled())) {
            this.cancel();
        }
    }
}