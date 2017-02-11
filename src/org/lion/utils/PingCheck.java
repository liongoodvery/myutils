package org.lion.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lion on 2/11/17.
 */
public class PingCheck {
    public static void main(String[] args) throws IOException, InterruptedException {
        String prefix = "192.168.1.";
        final int threadNum=10;
        final int timeout=200;
        Set<Integer> set = new ConcurrentSkipListSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < 255; ++i) {
            int finalI = i;
            executorService.execute(() -> {
                InetAddress ip = null;
                try {
                    ip = InetAddress.getByName(prefix + finalI);
                    boolean reachable = ip.isReachable(timeout);
                    if (reachable) {
                        set.add(finalI);
                    }
                } catch (IOException e) {
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(255*timeout/ threadNum*2, TimeUnit.MILLISECONDS);


        StringBuilder sb = new StringBuilder();
        for (Integer integer : set) {
            sb.append(integer).append("-");
        }
        String ips = "";
        if (sb.length() > 1) {
            ips = sb.toString();
            ips = ips.substring(0, ips.length() - 1);
        }

        byte[] encode = Base64.getEncoder().encode(ips.getBytes());
        String urlparam = URLEncoder.encode(new String(encode));
        new URL("http://localhost:10998/api/ping?ips=" + urlparam).openConnection().getInputStream();

    }
}
