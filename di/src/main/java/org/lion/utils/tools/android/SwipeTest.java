package org.lion.utils.tools.android;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lion on 17-11-10.
 */
public class SwipeTest {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
//        int count = Integer.valueOf(System.getProperty("count", "-1"));
//        if (count == -1) {
//            count = 10000;
//        }
//
//        int interval = Integer.valueOf(System.getProperty("interval", "200"));
        int count = 1000;
        int interval = 200;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                action();
                System.out.println("SwipeTest.run");
            }
        }, interval, interval, TimeUnit.MILLISECONDS);
    }

    public static void action() {
        String c1 = "adb shell input swipe  1000 500 200 500";
        String c2 = "adb shell input swipe  200 500 1000 500";

        String comm = (RANDOM.nextInt() % 3 == 0 ? c1 : c2);
        try {
            Runtime.getRuntime().exec("adb shell input swipe  1000 1500 1000 200").waitFor();
            Runtime.getRuntime().exec("adb shell input tap 500 1500").waitFor();
            Runtime.getRuntime().exec(comm).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
