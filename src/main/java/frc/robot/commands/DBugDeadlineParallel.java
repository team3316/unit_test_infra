package frc.robot.commands;

import java.util.List;
import java.util.function.Supplier;

/**
 * DBugCommandGroup
 */
public class DBugDeadlineParallel extends DBugParallel {
    
    private DBugCommand deadline;
    
    public DBugDeadlineParallel(List<Supplier<DBugCommand>> cmds) {
        super(cmds);
    }
    
    /**
     * runs the next parallel sequence of commands that should run
     * Modified since we need to save the parallel
     */
    protected void _start() {
        for (int i = 0; i < commandSupppliers.size(); i++) {
            Supplier<DBugCommand> supp = commandSupppliers.get(i);
            
            DBugCommand cmd = supp.get();
            commands.add(cmd);
            
            // If this is the first command in the list - make it the deadline
            if (i == 0) {
                deadline = cmd;
            }
        }
    }

    @Override
    public void execute() {
        super.execute();

        if (!this.wasCancelled()) {
            if (deadline.hasFinished()) {
                this.isFinished = true;
            }
        }
    }
}