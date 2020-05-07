package frc.robot.commands;

import org.junit.AfterClass;
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

    @AfterClass
    public static void cleanScheduler() {
        Scheduler.getInstance().removeAll();
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

        if (!_cmd.isRunning())
            _cmd.start();  // If cmd isn't running, push it to scheduler.
            Scheduler.getInstance().run();

        do {  // Run scheduler while command is running
            Scheduler.getInstance().run();
        } while (_cmd.isRunning() && untilFinished);

        Scheduler.getInstance().disable();
        MockedRobotState.getInstance().disableRobot();
    }
}