package frc.utils;

import static org.mockito.Mockito.when;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.wpilibj.DriverStation;

public class MockedRobotState {
    private static MockedRobotState instance;
    private ControlWord mockedControlWord;

    public static MockedRobotState getInstance() {
        if (instance == null)
            instance = new MockedRobotState();

        return instance;
    }

    public MockedRobotState() {
        mockedControlWord = (ControlWord) Utils.ReflectAndSpy(DriverStation.getInstance(), "m_controlWordCache");
        when(mockedControlWord.getDSAttached()).thenReturn(true);
    }

    public void enableRobot() {
        when(mockedControlWord.getEnabled()).thenReturn(true);
    }

    public void disableRobot() {
        when(mockedControlWord.getEnabled()).thenReturn(false);
    }

    public void setAutonomous() {
        when(mockedControlWord.getAutonomous()).thenReturn(true);
        when(mockedControlWord.getTest()).thenReturn(false);
    }

    public void setTeleop() {
        when(mockedControlWord.getAutonomous()).thenReturn(false);
        when(mockedControlWord.getTest()).thenReturn(false);
    }

    public void setTest() {
        when(mockedControlWord.getAutonomous()).thenReturn(false);
        when(mockedControlWord.getTest()).thenReturn(true);
    }
}