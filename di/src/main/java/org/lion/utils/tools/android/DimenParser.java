package org.lion.utils.tools.android;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.IntSupplier;

/**
 * Created by lion on 17-5-26.
 */
public class DimenParser {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run() throws IOException {
        String outRoot = "/home/lion/tmp";
        double[] ratios = {1.0, 1.5};
        String[] uns = {"sp", "dp"};
        for (String un : uns) {
            for (double ratio : ratios) {
                StringBuilder sb = format(ratio, un, new MyIntSupplier(un));
                String outFile = String.format("dimen_%s_%s", ratio + "", un);
                Files.write(Paths.get(outRoot, outFile), sb.toString().getBytes(), StandardOpenOption.CREATE);
            }
        }
    }

    private static StringBuilder format(double ratio, String un, IntSupplier intSupplier) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<resources>");
        String format = "<dimen name=\"dimen_%s_%d\">%.2f%s</dimen>";
        for (int i = 0; i < intSupplier.getAsInt(); i++) {
            sb.append(String.format(format, un, i, ratio * i, un));
        }
        sb.append("</resources>");
        return sb;
    }

    private static class MyIntSupplier implements IntSupplier {
        String un;

        public MyIntSupplier(String un) {
            this.un = un;
        }

        @Override
        public int getAsInt() {
            return un.equals("sp") ? 50 : 500;
        }
    }

}
