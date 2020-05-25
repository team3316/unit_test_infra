package frc.robot.commands;

import org.junit.After;
import org.junit.BeforeClass;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.utils.MockedRobotState;

public abstract class TestCommands {
    @BeforeClass
    public static void initMockedRobotState() {
        MockedRobotState.getInstance();
    }

    @After
    public void clearScheduler() {
        CommandScheduler.getInstance().cancelAll();
    }

    protected static void runCommand(Command _cmd) {
        runCommand(_cmd, false);
    }

    protected static void runCommand(Command _cmd, boolean untilFinished) {
        MockedRobotState.getInstance().enableRobot();
        CommandScheduler.getInstance().enable();

        if (!_cmd.isScheduled())
            _cmd.schedule();  // If cmd isn't running, push it to scheduler.
            
        do {  // Run scheduler while command is running
            CommandScheduler.getInstance().run();
        } while (!_cmd.isFinished() && untilFinished);

        CommandScheduler.getInstance().disable();
        MockedRobotState.getInstance().disableRobot();
    }

    protected static void runCommandGroup(DBugCommandGroup _cmd, boolean untilFinished) {
        MockedRobotState.getInstance().enableRobot();
        CommandScheduler.getInstance().enable();

        if (!_cmd.isScheduled()) {
            _cmd.schedule();
        }

        do {  // Run scheduler while command is running
            CommandScheduler.getInstance().run();
        } while (!_cmd.isFinished() && untilFinished);

        CommandScheduler.getInstance().disable();
        MockedRobotState.getInstance().disableRobot();
    }
}