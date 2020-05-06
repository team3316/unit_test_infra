package frc.robot;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TestUtils {
    @Test
    public void testInRange() {
        assertTrue(Utils.inRange(0.5, 1.0, 0.5));
        assertTrue(Utils.inRange(1.0, 1.0, 0.5));
        assertTrue(Utils.inRange(1.5, 1.0, 0.5));
        assertFalse(Utils.inRange(0.2, 1.0, 0.5));
        assertFalse(Utils.inRange(1.7, 1.0, 0.5));
    }
}