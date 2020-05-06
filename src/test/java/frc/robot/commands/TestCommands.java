package frc.robot.commands;

import org.junit.BeforeClass;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.utils.MockedRobotState;

public abstract class TestCommands {
    @BeforeClass
    public static void initMockedRobotState() {
        MockedRobotState.getInstance();
    }

    @BeforeClass
    public static void initSubsystem() {
        // initialize any subsystems you are testing commands for
     }

    protected static void runDefaultCommand(Subsystem subsystem) {
        runCommand(subsystem.getDefaultCommand(), false);
    }

    protected static void runCommand(Command _cmd) {
        runCommand(_cmd, false);
    }

    protected static void runCommand(Command _cmd, boolean untilFinished) {
        MockedRobotState.getInstance().enableRobot();
        Scheduler.getInstance().enable();

        _cmd.start();

        if (!_cmd.isRunning())  // If cmd isn't running, push it to scheduler.
            Scheduler.getInstance().run();

        do {  // Run scheduler while command is running
            Scheduler.getInstance().run();
        } while (_cmd.isRunning() && untilFinished);

        Scheduler.getInstance().disable();
        MockedRobotState.getInstance().disableRobot();
    }
}