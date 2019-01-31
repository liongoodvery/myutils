package org.lion.tools;

import java.io.IOException;
import java.util.ArrayList;

public class BashCommand {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("no command");
            System.exit(1);
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("/bin/bash");
        list.add("-c");
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }

        list.add(sb.toString());

        try {
            int code = new ProcessBuilder()
                    .command(list)
                    .inheritIO()
                    .start()
                    .waitFor();
            System.exit(code);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
