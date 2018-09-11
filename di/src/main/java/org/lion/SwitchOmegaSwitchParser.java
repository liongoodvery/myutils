package org.lion;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.lion.utils.Strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SwitchOmegaSwitchParser {
    public static void main(String[] args) {
        String url = "https://raw.githubusercontent.com/googlehosts/hosts/master/hosts-files/hosts";
        String format = "*.%s";
        String output = "/tmp/SwitchOmegaSwitchParser.out";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String[] lines = string.split("\n");
            ArrayList<String> result = new ArrayList<>();
            for (String line : lines) {
                if (line.startsWith("#")) continue;
                if (Strings.isEmpty(line.trim())) continue;
                if (line.contains("localhost")) continue;

                int index = line.lastIndexOf("\t");

                if (index != -1) {
                    String host = line.substring(index + 1);
                    if (host.split("\\.").length > 1) {
                        result.add(String.format(format, host));
                    }
                }
            }
            result.stream().forEach(System.out::println);
            System.out.println(result.size());
            Files.write(Paths.get(output), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
