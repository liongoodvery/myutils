package org.lion.utils.tools;

import gnu.getopt.Getopt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 17-11-20.
 */
public class TorrentSearch {
    private static final String URL_FORMAT = "https://www.torrentkitty.tv/search/%s/%s";
    private static final OkHttpClient client = new OkHttpClient();
    private static final List<String> LINES = new ArrayList<>();

    public static void main(String[] args) {
        new Getopt(TorrentSearch.class.getSimpleName(), args,"");
        for (int i = 7; i < 17; i++) {
            download("痴女", i);
        }
        try {
            Files.write(Paths.get("/tmp/abc.html"), LINES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void download(String blk, int page) {
        Request request = getRequest(blk, page);
        try {
            System.out.println("start download" + request.url());
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            LINES.add(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Request getRequest(String search, int page) {
        return new Request.Builder()
                .url(String.format(URL_FORMAT, search, page))
                .header("user-agent", "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1")
                .build();
    }
}
