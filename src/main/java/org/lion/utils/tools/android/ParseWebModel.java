package org.lion.utils.tools.android;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import org.lion.utils.Strings;

/**
 * Created by lion on 17-9-25.
 */
public class ParseWebModel {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        LongOpt[] long_options = {new LongOpt("key", LongOpt.REQUIRED_ARGUMENT, null, 'k')};
        Getopt model = new Getopt("ParseWebModel", args, "k:i:o:", long_options);
        int c;
        String key = null, input = null, output = null;

        while ((c = model.getopt()) != -1) {
            switch (c) {
                case 'k':
                    key = model.getOptarg();
                    System.out.println(sb.toString() + "===");
                    break;
                case 'i':
                    input = model.getOptarg();
                    break;

                case 'o':
                    output = model.getOptarg();
                    break;
                case '?':
                    break;
                default:
                    break;
            }
        }
        if (Strings.isEmpty(key)) {
            throw new RuntimeException("lack of key param");
        }

        if (Strings.isEmpty(input)) {
            input = System.getProperty("PROJECT_MODEL_INPUT");
        }

        if (Strings.isEmpty(input)) {
            throw new RuntimeException("input cannot be resolved");
        }

        if (Strings.isEmpty(output)) {
            output = System.getProperty("PROJECT_MODEL_OUTPUT");
        }

        if (Strings.isEmpty(output)) {
            throw new RuntimeException("input cannot be resolved");
        }
        System.out.println(key);
        System.out.println(input);
        System.out.println(output);
    }


}
