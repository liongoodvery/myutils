package org.lion.utils;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClick {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(1120,750);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
