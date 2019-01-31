package org.lion.utils;

import okhttp3.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpRe {
    public static void main(String[] args) {
        new Thread(() -> run()).start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void run() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();

        String cookie = "__cfduid=dd5ef5fe2aa60dfcd7783129cd88d3e061542972015; aff=15645; login=far6633%40163.com; xfss=vm70gqhlwouj02yo; ref_url=https%3A%2F%2Favcensdownload.pro%2Fvideo79888.html; current_file_id=3560359; cf_clearance=f5946b51370d76f5c3985c47cb00ecb07fd7e744-1543033382-1800-150; lang=english";

        String url = "https://takefile.link/e9hrb0si7aiu/_FHD_ipx230.mp4";

        Request request = new Request.Builder()
                .url(url)
                .header("cookie", cookie)
                .header("origin", "https://takefile.link")
                .header("referer", url)
                .header("upgrade-insecure-requests", 1 + "")
                .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")

                .post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), "op=download2&id=e9hrb0si7aiu&rand=&referer=https%3A%2F%2Favcensdownload.pro%2Fvideo79888.html&method_free=&method_premium=1"))
                .build();


        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Request request1 = response.request();
                printHeader(request1.headers());
                System.out.println("=====================");
                printHeader(response.headers());
                String string = response.body().string();
                Files.write(Paths.get("/tmp/a.html"), string.getBytes());
            }
        });

    }

    private static void printHeader(Headers headers) {
        for (String name : headers.names()) {
            System.out.println(name + ":" + headers.get(name));
        }
    }
}
