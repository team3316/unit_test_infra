package frc.robot.commands;

import java.util.List;
import java.util.function.Supplier;

/**
 * DBugCommandGroup
 */
public class DBugRaceParallel extends DBugParallel {

    public DBugRaceParallel(List<Supplier<DBugCommand>> cmds) {
        super(cmds);
    }

    @Override
    public void execute() {
        super.execute();

        for (DBugCommand cmd : commands) {
            if (cmd.hasFinished()) {
                isFinished = true;
                break;
            }
        }
    }
}