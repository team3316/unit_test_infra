package frc.robot.commands;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;

/**
 * DBugCommandGroup
 */
public abstract class DBugCommandGroup extends DBugCommand {
    private Queue<Supplier<DBugCommand>> queue = new ArrayDeque<>();
    private Queue<Supplier<DBugCommand>> queueStorage = new ArrayDeque<>();
    private DBugCommand head;
    private boolean headHasFinished;

    private boolean canAdd = true;
    private boolean isFinished;

    /**
     * Constructor - override when writing command groups
     */
    public DBugCommandGroup() {
    };

    /**
     * This method returns the initial group of methods that should run, including those that were already ran
     * @return The command group's representation of the commands that need to run -
     *         in the hierarchy
     */
    public Queue<Supplier<DBugCommand>> getStorage() {
        return this.queueStorage;
    }

    /**
     * initializes the command sequence
     */
    @Override
    public synchronized void init() {
        canAdd = false;
        isFinished = false;
        queueStorage = new ArrayDeque<>(queue);
    }

    /**
     * The command group's execute method - runs periodically until the sequence is finished
     * handles execution of commands
     */
    @Override
    public void execute() {
        if (head == null) {
            this._runNextSequential();
        } else {
            headHasFinished = headHasFinished || head.isFinished(); 
            if (head.wasCancelled()) {
                this.cancel();
            } else if (headHasFinished) {
                this._runNextSequential();
            }
        }
    }

    /**
     * Runs the next command that should run
     */
    private void _runNextSequential() {
        Supplier<DBugCommand> sup_head = queue.poll();

        if (sup_head != null) {
            head = sup_head.get();
            head.schedule();
            headHasFinished = false;
        } else {
            this.isFinished = true;
        }
    }

    /**
     * Adds a command to the sequence.
     * @param cmd - a new CommandBase to run sequentially - Passed as a supllier returning a CommandBase instant.
     *
     */
    protected final synchronized void add(Supplier<DBugCommand> cmd) {

        if (canAdd) {
            queueStorage.add(cmd);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Tried to add sequential after initializing command group ");
            sb.append(this.getName());
            sb.append(" [");
            sb.append(cmd.get().toString());
            sb.append("]");
        }
    }

    /**
     * @param cmds - appends a sequence of CommandBases to run. Given as suppliers,
     *             each returning a new CommandBase instance. These will run
     *             in parallel until all are finished (or otherwise interrupted).
     */
    @SafeVarargs
    protected final synchronized void addWaitParallel(Supplier<DBugCommand>... cmds) {
        if (cmds.length <= 0) {
            throw new IllegalArgumentException("Tried to initialize an empty parallel");
        }
        this.add(() -> new DBugWaitParallel(List.of(cmds)));
    }

    
    /**
     * @param cmds - appends a sequence of CommandBases to run. Given as suppliers,
     *             each returning a new CommandBase instance. These will run
     *             in parallel until one of them is finished (or otherwise interrupted).
     *
     */
    @SafeVarargs
    protected final synchronized void addRaceParallel(Supplier<DBugCommand>... cmds) {
        if (cmds.length <= 0) {
            throw new IllegalArgumentException("Tried to initialize an empty parallel");
        }
        this.add(() -> new DBugRaceParallel(List.of(cmds)));
    }

    /**
     * @param cmds - appends a sequence of CommandBases to run. Given as suppliers,
     *             each returning a new CommandBase instance. These will run
     *             in parallel until one is finished (or otherwise interrupted).
     *
     */
    @SafeVarargs
    protected final synchronized void addDeadlineParallel(Supplier<DBugCommand> deadline, Supplier<DBugCommand>... cmds) {
        List<Supplier<DBugCommand>> list = Arrays.asList(cmds);
        list.add(0, deadline);
        this.add(() -> new DBugDeadlineParallel(list));
    }

    /**
     * runs before the <code>end(boolean interrupted)</code> function.
     * @param interrupted - whether the command was interrupted or ended naturally.
     */
    @Override
    public final void fin(boolean interrupted) {
        if (interrupted) {
            this.head.cancel();
        }

        queue = new ArrayDeque<>();
    }

    /**
     * This method indicates whether the command is finished or not
     * @return whether the command's end condition has been reached.
     */
    @Override
    public final boolean endCondition() {
        return isFinished;
    }
}