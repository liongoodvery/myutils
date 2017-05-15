package org.lion.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by more on 2016-05-08 21:37:37.
 */
public class PrinterTest {
    public static void main(String[] args) throws InterruptedException {

        List<Integer> list = new ArrayList<>(1 << 10);
        final Printer printer = new Printer(list);
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < (1 << 8); i++) {
                    try {
                        printer.print1();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < (1 << 8); i++) {
                    try {
                        printer.print2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < (1 << 8); i++) {
                    try {
                        printer.print3();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < (1 << 8); i++) {
                    try {
                        printer.print4();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        TimeUnit.SECONDS.sleep(5);
        for (int i = 0; i < list.size(); ++i) {
            assert (list.get(i) == (i) % 4);

        }
    }
}
