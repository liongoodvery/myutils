package org.lion.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by lion on 2/11/17.
 */
public class PingCheck {
    static final int threadNum = 10;
    static final int timeout = 200;
    static final String host;
    static final String port;
    static final String path;

    static {
        host = ResourceBundle.getBundle("ping").getString("host");
        port = ResourceBundle.getBundle("ping").getString("port");
        path = ResourceBundle.getBundle("ping").getString("path");
    }

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException, ExecutionException {
        String prefix = "192.168.1.";
        Set<Integer> set = new TreeSet<>();
        List<Future<Boolean>> futures = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < 255; ++i) {
            futures.add(service.submit(new Worker(prefix, i + "")));
        }
        service.shutdown();
        service.awaitTermination(255 * timeout / threadNum * 2, TimeUnit.MILLISECONDS);
        for (int i = 0; i < futures.size(); i++) {
            Future<Boolean> future = futures.get(i);
            if (future.get(1, TimeUnit.MINUTES)) {
                set.add(i);
            }
        }


        String parent = new String(Base64.getEncoder().encode(prefix.getBytes()));
        String children = "";
        if (set.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Integer integer : set) {
                sb.append(integer).append("\u0000");
            }
            System.out.println(sb.toString().replaceAll("\\u0000", "-"));
            String ips = "";
            if (sb.length() > 1) {
                ips = sb.toString();
                ips = ips.substring(0, ips.length() - 1);
            }
            children = new String(Base64.getEncoder().encode(ips.getBytes()));
        }


        InputStream inputStream = new URL(String.format("http://%s:%s/%s/api/report/host?parent=%s&children=%s", host, port, path, parent, children)).openConnection().getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(reader.readLine());
    }


    public static class Worker implements Callable<Boolean> {
        private String prefix;
        private String order;

        public Worker(String prefix, String order) {
            this.prefix = prefix;
            this.order = order;
        }

        @Override
        public Boolean call() throws Exception {
            InetAddress ip = null;
            try {
                ip = InetAddress.getByName(prefix + order);
                return ip.isReachable(timeout);
            } catch (IOException e) {
            }
            return false;
        }
    }
}

