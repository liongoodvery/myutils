package org.lion.utils.tools;

import org.lion.utils.Strings;

import java.io.IOException;

/**
 * Created by lion on 17-10-9.
 */
public class Underline2Camel {
    public static void main(String[] args) {
        byte[] buffer = new byte[1024];

        try {
            int len;
            String x = "";
            if (args.length == 0) {
                if ((len = System.in.read(buffer)) != -1) {
                    x = new String(buffer, 0, len - 1);
                }
            } else {
                x = args[0];
            }
            System.out.println(Strings.underLineToCamel("", x, false));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
