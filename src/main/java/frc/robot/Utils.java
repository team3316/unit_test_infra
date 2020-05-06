package frc.robot;

public class Utils {
    public static boolean inRange(double value, double target, double error) {
        return Math.abs(value - target) <= error;
    }
}