package frc.robot.HumanIO;

import static org.mockito.Mockito.when;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Robot;
import frc.utils.Utils;

public class JoystickUtil {
    
    private XboxController _joystick;

    private static JoystickUtil _instance;

    private JoystickUtil(String controllerName) {
        Robot.joysticks = new Joysticks();

        XboxController joystick = (XboxController) Utils.ReflectAndSpy(Robot.joysticks, controllerName);
        this._joystick = joystick;

        Robot.joysticks.initButtons();
    }

    protected static JoystickUtil getInstance() {
        if (_instance == null) {
            _instance = new JoystickUtil("_operatorJoystick");
        }
        return _instance;
    }

    protected void runButton(Button button) {
        runButton(button, 1);
    }

    protected void runButton(Button button, int ticks) {
        when(this._joystick.getRawButton(button.value)).thenReturn(true);

        Utils.runSchedualer(ticks);
    }

}