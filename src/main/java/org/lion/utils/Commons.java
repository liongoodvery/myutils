package org.lion.utils;

import org.lion.MathComparator;
import org.lion.NotTest;
import org.lion.utils.windows.Pair;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by more on 2016-04-18 19:52.
 * The class has a lot of useful tools for commons programming task
 */
public class Commons {
    private Commons() {
    }

    /**
     * Sleep for a random amount of time , less than 10 seconds , and print message
     */
    public static void sleep() {
        sleep(10, true);
    }

    /**
     * Sleep for a random amount of time , less than 10 seconds .
     *
     * @param isPrint true if print message
     */
    public static void sleep(boolean isPrint) {
        sleep(10, isPrint);
    }

    /**
     * Sleep for a random amount of time , less than maxSec seconds .
     *
     * @param maxSec the max seconds
     */
    public static void sleep(int maxSec) {
        sleep(maxSec, true);
    }

    /**
     * Sleep for a random amount of time.
     *
     * @param maxSec  the max seconds
     * @param isPrint true if print message
     */
    public static void sleep(int maxSec, boolean isPrint) {
        try {
            sleepInterrupted(maxSec, isPrint);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepSeconds(int time, boolean isPrint) {

        try {
            TimeUnit.SECONDS.sleep(time);
            if (isPrint) {
                Commons.log("TIME=%-15s , NAME=%-10s , SLEEP=%-5d\n",
                        getTimeString(), Thread.currentThread().getName(), time);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static String getTimeString() {
        return MyDateFormat.getInstance().format(new Date());
    }

    public static void sleepSeconds(int time) {
        sleepSeconds(time, true);
    }


    /**
     * Sleep a random amount of time , do not handle exception.
     *
     * @param maxSec  the max seconds
     * @param isPrint true if print message
     * @throws InterruptedException
     */
    public static void sleepInterrupted(int maxSec, boolean isPrint)
            throws InterruptedException {
        long st = (long) (Math.random() * maxSec);
        if (isPrint) {
            Commons.log("TIME=%-15s , NAME=%-10s , SLEEP=%-5d\n",
                    getTimeString(), Thread.currentThread().getName(), st);
        }
        TimeUnit.SECONDS.sleep(st);
    }

    /**
     * Sleep a random amount of time,less than 10s ,print message , do not handle exception.
     *
     * @throws InterruptedException
     */
    public static void sleepInterrupted() throws InterruptedException {
        sleepInterrupted(10, true);
    }

    /**
     * Sleep a random amount of time,less than maxSec seconds ,print message , do not handle exception.
     *
     * @param maxSec the max seconds
     * @throws InterruptedException
     */
    public static void sleepInterrupted(int maxSec) throws InterruptedException {
        sleepInterrupted(maxSec, true);
    }

    /**
     * Sleep a random amount of time,less than 10s , do not handle exception.
     *
     * @param isPrint true if print message
     * @throws InterruptedException
     */
    public static void sleepInterrupted(boolean isPrint)
            throws InterruptedException {
        sleepInterrupted(10, isPrint);
    }

    /**
     * Print formatted string to a PrintStream out
     *
     * @param out     the Given PrintStream
     * @param format  c-style format
     * @param objects argument of format
     */
    public static synchronized void log(PrintStream out, String format, Object... objects) {
        out.printf("%s : ", getTimeString());
        out.printf(format, objects);
        out.flush();
    }

    /**
     * print a String to a PrintStream , a line is added at the end.
     *
     * @param out the given PrintStream
     * @param str the printing String
     */
    public static synchronized void log(PrintStream out, String str) {
        out.printf("%s : ", getTimeString());
        out.println(str);
    }

    /**
     * Print formatted string to std out
     *
     * @param format  c-style format
     * @param objects argument of format
     */
    public static void log(String format, Object... objects) {
        log(System.out, format, objects);
    }

    /**
     * Print a String to std out
     *
     * @param str the printing String
     */
    public static void log(String str) {
        log(System.out, str);
    }

    /**
     * Print an Object to std out
     *
     * @param obj the printed Object
     */
    public static void log(Object obj) {
        log(System.out, obj.toString());
    }

    /**
     * Print a log info when a thread start
     *
     * @param extra the extra information
     */
    public static void threadStartLog(String extra) {
        Commons.log("Thread %s Starts %s\n", Thread.currentThread().getName(),
                extra);
    }

    /**
     * default log info
     */
    public static void threadStartLog() {
        threadStartLog("");
    }

    /**
     * Print a log info when a thread  end
     *
     * @param extra the extra information
     */
    public static void threadEndLog(String extra) {
        Commons.log("Thread %s Ends %s\n", Thread.currentThread().getName(),
                extra);
    }

    /**
     * default end log
     */
    public static void threadEndLog() {
        threadEndLog("");
    }

    public static void startThreads(Iterable<Thread> threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    /**
     * Wait for a set of threads terminated
     *
     * @param threads the group of threads
     */
    public static void joinThreads(Iterable<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Wait for a set of threads terminated
     *
     * @param threads the group of threads
     */
    public static void joinThreads(Thread[] threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Start a set of threads
     *
     * @param threads the group of threads
     */
    public static void startThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }

    }

    /**
     * Convert an int array to an Integer List
     *
     * @param arr an int array
     * @return an Integer List
     */
    public static List<Integer> array2List(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (Integer integer : arr) {
            list.add(integer);
        }
        return list;
    }

    /**
     * Convert a Double array to Double List
     *
     * @param arr a Double array
     * @return a Double List
     */
    public static List<Double> array2List(double[] arr) {
        List<Double> list = new ArrayList<>();
        for (Double integer : arr) {
            list.add(integer);
        }
        return list;
    }

    /**
     * Convert a Long array to Long List
     *
     * @param arr a Long array
     * @return a Long List
     */
    public static List<Long> array2List(long[] arr) {
        List<Long> list = new ArrayList<>();
        for (Long integer : arr) {
            list.add(integer);
        }
        return list;
    }

    /**
     * Convert an array to list
     *
     * @param obj the array
     * @param <T> the type of the array
     * @param cls the Class of the element
     * @return a list filled with the input array
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public static <T> List<T> array2List(Object obj, Class<T> cls) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < Array.getLength(obj); i++) {
            T t = (T) Array.get(obj, i);
            list.add(t);
        }

        return list;

    }

    /**
     * Convert an array to list
     *
     * @param obj the array
     * @param <T> the type of the array
     * @return a list filled with the input array
     */
    public static <T> List<T> array2List(Object obj) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < Array.getLength(obj); i++) {
            T t = (T) Array.get(obj, i);
            list.add(t);
        }

        return list;

    }

    /**
     * Calculate the time of a thread
     *
     * @param r the Runnable Object
     */
    public static void profile(Runnable r) {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(r);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Commons.log("Task %-20s Duration: %-10d\n", r.toString(), System.currentTimeMillis() - start);


    }

    /**
     * Calculate the time of a thread , using ThreadPool
     *
     * @param r the Runnable Object
     */
    public static void profilePool(Runnable r) {
        ExecutorService service = ProfileExecutor.newProfileExecutor();
        service.execute(r);
        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <T> void swap(T[] ta, int i, int j) {
        T tmp = ta[i];
        ta[i] = ta[j];
        ta[j] = tmp;
    }

    public static <T extends List<E>, E> void swap(T list, int i, int j) {
        E tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);

    }

    public static <T> void printArray(PrintStream out, T[] arr,
                                      CharSequence split, CharSequence start, CharSequence end) {
        out.println(array2String(arr, split, start, end));
    }

    public static <T> void printArray(T[] arr) {
        printArray(System.out, arr, ", ", "[", "]");
    }

    public static void printArray(Object obj) {
        printArray(obj, System.out);
    }

    public static void printArray(Object obj, PrintStream out) {
        Object[] arr = array2Objects(obj);
        printArray(out, arr, ", ", "[", "]");
    }

    public static void print(Object obj) {
        print(obj, System.out);
    }

    public static void print(Object obj, PrintStream out) {
        if (obj == null) {
            out.println("null");
            return;
        }
        if (obj.getClass().getName().startsWith("[")) {
            printArray(obj, out);
        } else if (obj instanceof List) {
            printList((List) obj, out);
        } else if (obj instanceof Map) {
            printMap((Map) obj, out);
        } else {
            out.println(obj.toString());
        }
    }

    public static <T> String array2String(T[] arr, CharSequence split, CharSequence start, CharSequence end) {
        if (arr == null) {
            return "null";
        }
        if (split == null) {
            split = "";
        }
        if (start == null) {
            start = "";
        }

        if (end == null) {
            end = "";
        }
        int iMax = arr.length - 1;
        if (iMax == -1) {
            return (start.toString() + end.toString());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        for (int i = 0; i < iMax; i++) {
            T t = arr[i];
            sb.append(t).append(split);
        }
        sb.append(arr[iMax]).append(end);
        return sb.toString();
    }

    public static <T> String array2String(T[] arr) {
        return array2String(arr, ", ", "[", "]");
    }


    public static String array2String(Object arr) {
        Object[] objects = array2Objects(arr);
        return array2String(objects);
    }

    public static Object[] array2Objects(Object obj) {
        if (obj == null) {
            return null;
        }
        int length = Array.getLength(obj);
        Object[] objects = new Object[length];
        for (int i = 0; i < length; i++) {
            objects[i] = Array.get(obj, i);
        }
        return objects;
    }

    public static <E> void printList(List<E> list, PrintStream out) {
        if (list == null) {
            out.println("null");
        }
        for (E e : list) {
            out.println(e);
        }
    }

    public static <E> void printList(List<E> list) {
        printList(list, System.out);
    }

    public static <K, V> void printMap(Map<K, V> map) {
        printMap(map, System.out);

    }


    public static <K, V> void printMap(Map<K, V> map, PrintStream out) {
        String format = "%-10s = %s\n";
        printMap(map, format, out);

    }


    public static <K, V> void printMap(Map<K, V> map, int keyWidth, int valueWidth, String start, String end, boolean isNewLine) {
        if (keyWidth < 0 || valueWidth < 0) {
            throw new IllegalArgumentException("width can can be less than zero");
        }
        StringBuilder sb = new StringBuilder();
        if (start != null)
            if (start.equals("%")) {
                sb.append("%%");
            } else {
                sb.append(start);
            }
        sb.append(String.format("%%-%ds=%%-%ds", keyWidth, valueWidth));
        if (end != null) {
            if (end.equals("%")) {
                sb.append("%%");
            } else {
                sb.append(end);
            }
        }
        if (isNewLine) {
            sb.append("\n");
        }
        String format = sb.toString();
        printMap(map, format, System.out);

    }

    public static <K, V> void printMap(Map<K, V> map, String format, PrintStream out) {
        if (map == null) {
            out.println("null");
        }
        Set<Map.Entry<K, V>> entries = map.entrySet();
        for (Map.Entry<K, V> entry : entries) {
            out.printf(format, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Convert the gbk file to a utf-8 file .
     *
     * @param file the file to be converted
     */

    public static void gbk2utf8(Path file) {
        try {
            List<String> lines = Files.readAllLines(file, Charset.forName("GBK"));
            Files.write(file, lines, Charset.forName("utf-8"), StandardOpenOption.CREATE);
            Commons.log("Convert file %s success\n", file.toAbsolutePath().toString());

        } catch (IOException e) {
            Commons.log("=======Convert file %s failed e.message=%s\n", file.toAbsolutePath()
                    .toString(), e.getMessage());
        }
    }

    @Deprecated
    public static void gbk2utf8(String fileName) {

        try {
            List<String> lines = MyFiles.readAllLines(fileName, "gbk");
            MyFiles.writeAllLines(fileName, lines, "utf-8", FileOpenMode.CREATE);
            Commons.log("Convert file %s success\n", fileName);
        } catch (IOException e) {
            Commons.log("=======Convert file %s failed e.message=%s\n", fileName, e.getMessage());
        }

    }

    /**
     * get a file's extension e.g "a.java"->"java" "a"->""  "a.java.cpp"->"cpp"
     *
     * @param fileName the file name
     * @return the file's extension with out doc
     */
    public static String getExtension(String fileName) {
        String ret = "";
        int index = -1;
        if (fileName != null && (index = fileName.lastIndexOf(".")) != -1) {
            if (index + 1 < fileName.length()) {
                ret = fileName.substring(index + 1, fileName.length());
            }
        }
        return ret;
    }

    public static <T> List<T> generateList(T... ts) {
        if (ts == null) {
            return null;
        }
        List<T> list = new ArrayList<>(ts.length);
        for (T t : ts) {
            list.add(t);
        }
        return list;
    }

    public static List<Integer> range(int from, int to) {
        if (from < to) {
            return range(from, to, 1);
        } else {
            return range(from, to, -1);
        }
    }

    public static List<Integer> range(int from, int to, int step) {
        if (0 == step) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        switch (step / Math.abs(step)) {
            case 1:
                if (from > to) {
                    return null;
                }

                for (int i = from; i < to; i = i + step) {
                    list.add(i);
                }
                break;
            case -1:
                if (from < to) {
                    return null;
                }
                for (int i = from; i > to; i = i + step) {
                    list.add(i);
                }
                break;
        }

        return list;
    }

    public static <K, V> void printMap(Map<K, V> map, String format) {
        printMap(map, format, System.out);
    }

    public static void printSplits() {
        printSplits(System.out, "=", 80);
    }

    public static void printSplits(PrintStream out, CharSequence split, int count) {
        out.println(Strings.repeat(split, count));

    }

    public static Path getToolInitPath(String[] args) {
        Path init;
        if (args.length == 0) {
            init = Paths.get(System.getProperty("user.dir"));
        } else {
            init = Paths.get(args[0]);
        }
        return init;
    }

    public static boolean assertArgumentAmount(Object obj, MathComparator comparator) {
        switch (comparator) {
            case EQUAL:
            case NOT_EQUAL:
            case LESS:
            case LESS_EQUAL:
            case GREATER:
            case GREATER_THAN:
                return obj instanceof Integer;
            case IN:
                return obj instanceof List;
            case BETWEEN:
                boolean ret = false;
                if ((obj instanceof Pair)) {
                    Pair pair = (Pair) obj;
                    Object first = pair.getFirst();
                    Object second = pair.getSecond();
                    if (first.getClass()
                            .equals(second.getClass()) && first instanceof Comparable && second instanceof Comparable) {
                        Comparable c1 = (Comparable) first;
                        Comparable c2 = (Comparable) second;
                        if (c1.compareTo(c2) < 0) {
                            ret = true;
                        }
                    }
                }

                return ret;
        }
        return false;
    }

    @NotTest
    public static boolean checkArguments(String args, Object obj, MathComparator comparator) {
        if (!assertArgumentAmount(obj, comparator)) {
            return false;
        }
        int amount = args.length();
        switch (comparator) {
            case EQUAL:
                return obj.equals(amount);
            case NOT_EQUAL:
                return (int) obj != amount;
            case LESS:
                return (int) obj < amount;
            case LESS_EQUAL:
                return (int) obj <= amount;
            case GREATER:
                return (int) obj > amount;
            case GREATER_THAN:
                return (int) obj >= amount;
            case IN:
                return ((List) obj).contains(amount);
            case BETWEEN:
                Pair pair = (Pair) obj;
                int first = (int) pair.getFirst();
                int second = (int) pair.getSecond();
                return first <= amount && second > amount;
            default:
                return false;
        }

    }

    public static void printAscii() {
        for (int i = 0; i < 127; i++) {
            System.out.println((char) i);
        }
    }

    public static void zip(File sorceFile, File targetFile) throws IOException {
        FileInputStream fin = new FileInputStream(sorceFile);
        GZIPOutputStream fout = new GZIPOutputStream(new FileOutputStream(targetFile));
        IOStreams.copy(fin, fout);
    }

    public static void unZip(File sorceFile, File targetFile) throws IOException {
        GZIPInputStream fin = new GZIPInputStream(new FileInputStream(sorceFile));
        FileOutputStream fout = new FileOutputStream(targetFile);
        IOStreams.copy(fin, fout);
    }


    public static Map<Integer, String> parseR(Path rPath) throws IOException {
        Map<Integer, String> R = new HashMap<>();
        boolean isFirstClass = true;
        String className = "";
        String format = "R.%s.%s";
        List<String> lines = Files.readAllLines(rPath, Charset.forName("UTF-8"));
        for (String line : lines) {
            if (line.contains("public static final class")) {
//                if (isFirstClass) {
//                    isFirstClass = false;
//                    continue;
//                }

                String[] split = line.split(" ");
                className = split[split.length - 1];
            } else if (line.contains("public static final int")) {
                String[] split = line.split(" ");
                int length = split.length;
                String s = split[length - 1];
                R.put(Integer.parseInt(s.substring(0, s.length() - 1)),
                        String.format(format, className, split[length - 3]));
            }
        }
        return R;
    }

    public static String parseSqlInsertFormat(String tableName, int columnCount) {
        if (Strings.isNullOrEmpty(tableName)) {
            throw new RuntimeException("table name could not be empty");
        }

        if (columnCount < 1) {
            throw new RuntimeException("columnCount must >=1");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(tableName)
                .append(" (");
        for (int i = 0; i < columnCount - 1; i++) {
            sb.append("%s,");
        }
        sb.append("%s) values (");
        for (int i = 0; i < columnCount - 1; i++) {
            sb.append("'%%s',");
        }
        sb.append("'%%s');");
        return sb.toString();
    }


    public static String parseSqlInsertFormat(String tableName, String[] colums) {
        if (Strings.isNullOrEmpty(tableName)) {
            throw new RuntimeException("table name could not be empty");
        }
        if (colums == null || colums.length < 1) {
            throw new RuntimeException("length of columns must >=1");
        }
        String format = parseSqlInsertFormat(tableName, colums.length);
        return String.format(format, colums);
    }


    /**
     * @param id like "@+id/timepicker"
     * @return like "timepicker"
     */
    public static String splitAndroidId(String id) {
        return Strings.lastPart(id, "/");
    }

    /**
     *
     * @param className
     * @return org.lion.utils.Commons=>Commons
     */
    public static String simpleClassName(String className) {
        return Strings.lastPart(className, ".");
    }

    public static String fileNameWithoutExt(String file) {
        if (Strings.isEmpty(file)) {
            return "";
        }
        String s = Strings.lastPart(file, File.separator);
        Pattern pattern = Pattern.compile("(.*)\\..*");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return s;
    }
}
