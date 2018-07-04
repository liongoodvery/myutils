package org.lion.utils.tools;

import org.lion.utils.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class AutoRunFileChange {

    public static void main(String[] args) {

        final ExecutorService service = Executors.newCachedThreadPool();
        final Config c = new SystemConfig();
        System.out.println(c);
        Long lastTime = 0L;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10240);

        service.execute(new ReaderTask(c, blockingQueue));

        while (true) {


            try {
                String take = blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long currentTimeMillis = System.currentTimeMillis();
            long diff = currentTimeMillis - lastTime;
            if (diff < c.watcherTime) {
                System.out.println("diff=" + diff + "ms");
                continue;
            }
            lastTime = System.currentTimeMillis();
            service.execute(new Worker(c));
        }
    }

    private static class ReaderTask implements Runnable {
        final Config config;
        private BlockingQueue<String> files;

        private ReaderTask(Config config, BlockingQueue<String> files) {
            this.config = config;
            this.files = files;
        }

        public void run() {
            try {
                doRead();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean exclude(String string) {
            List<String> exclude = config.watcherExclude;
            for (String s : exclude) {
                if (string.contains(s)) {
                    return true;
                }
            }
            return false;
        }

        private void doRead() {
            try (BufferedReader r = new BufferedReader(new FileReader(config.watcherFile))) {
                String fileName = null;
                while (true) {
                    fileName = r.readLine();
                    if (fileName == null) {
                        throw new RuntimeException("can not read from " + config.watcherFile);
                    }
                    for (String s : config.watcherFilter) {
                        if (fileName.contains(s) && !exclude(fileName)) {
                            System.out.println("read: " + fileName);
                            files.put(fileName);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.exit(-1);
                e.printStackTrace();
            }
        }
    }

    private static class Worker implements Runnable {
        final Config config;

        private Worker(Config config) {
            this.config = config;
        }

        @Override
        public void run() {
            try {
                int ret = new ProcessBuilder()
                        .command(config.command)
                        .directory(config.userDir)
                        .inheritIO()
                        .start()
                        .waitFor();
                if (ret == 0) {
                    System.out.println("exec " + Arrays.toString(config.command) + "success");
                }
            } catch (InterruptedException e) {
                System.out.println("exec " + config.command + "timeout");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("exec " + config.command + "failed");
                e.printStackTrace();
            }
        }
    }


    private static class Config {
        File userDir;
        File watcherFile;
        Long watcherTime;
        List<String> watcherFilter;
        List<String> watcherExclude;
        String command[];

        @Override
        public String toString() {
            return "Config{" +
                    "userDir=" + userDir +
                    ", watcherFile=" + watcherFile +
                    ", watcherTime=" + watcherTime +
                    ", watcherFilter=" + watcherFilter +
                    ", watcherExclude=" + watcherExclude +
                    ", command=" + Arrays.toString(command) +
                    '}';
        }
    }

    private static class SystemConfig extends Config {
        public SystemConfig() {
            String userDirStr = System.getProperty("user.dir");
            String watcherFileStr = System.getProperty("user.watcher.file");
            String watcherTimeStr = System.getProperty("user.watcher.time");
            String watcherFilterStr = System.getProperty("user.watcher.filter");
            String watcherScript = System.getProperty("user.watcher.script");
            String watcherExcludeStr = System.getProperty("user.watcher.exclude");

            Objects.requireNonNull(userDirStr);
            Objects.requireNonNull(watcherFileStr);
            Objects.requireNonNull(watcherFilterStr);
            Objects.requireNonNull(watcherExcludeStr);

            this.userDir = new File(userDirStr);
            this.watcherFile = new File(watcherFileStr);

            this.watcherTime = Strings.isEmpty(watcherTimeStr) ? 3000L : Long.valueOf(watcherTimeStr);
            this.watcherFilter = new ArrayList<>();
            watcherFilter.addAll(Arrays.asList(watcherFilterStr.split(",")));
            this.command = watcherScript.split(" ");

            this.watcherExclude = new ArrayList<>();
            watcherExclude.addAll(Arrays.asList(watcherExcludeStr.split(",")));
        }
    }

    private static class DebugConfig extends Config {
        public DebugConfig() {
            this.userDir = new File("/home/lion/IdeaProjects/myutils");
            this.watcherFile = new File("/tmp/watcher");
            System.out.println(this.watcherFile);
            this.watcherFilter = new ArrayList<String>() {{
                add(".java");
                add(".sh");
            }};
            this.watcherTime = 3000L;
            FileGenerator.start(this);
            this.command = new String[]{"ls", "-l"};
        }
    }

    private static class FileGenerator implements Runnable {
        final Config config;

        private FileGenerator(Config config) {
            this.config = config;
        }

        public static void start(Config config) {
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleWithFixedDelay(new FileGenerator(config), 500, 750, TimeUnit.MILLISECONDS);
        }

        @Override
        public void run() {
            File file = new File(config.userDir, "Test.java");
            try (PrintStream printStream = new PrintStream(file)) {
                printStream.append("time:" + System.currentTimeMillis());
                printStream.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
