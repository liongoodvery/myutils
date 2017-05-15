package org.lion.test;

import org.junit.Assert;
import org.junit.Test;
import org.lion.MathComparator;
import org.lion.utils.Commons;
import org.lion.utils.IOStreams;
import org.lion.utils.Strings;
import org.lion.utils.windows.Pair;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by more on 2016-05-31 22:01:32.
 */
public class NewTest {
    @Test
    public void test8() throws Exception {
        Map<String, String> map = new HashMap<>();
        Method[]
                methods =
                map.getClass()
                   .getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }


        System.out.println(Strings.repeat("=", 80));
        Method[]
                declaredMethods =
                map.getClass()
                   .getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println(method.getName());
        }

        System.out.println(Strings.repeat("=", 80));

    }

    @Test
    public void test33() throws Exception {
        Commons.log("成功".getBytes("utf-8").length);
    }

    @Test
    public void test40() throws Exception {
        Assert.assertEquals("java", Commons.getExtension("a.java"));
        Assert.assertEquals("", Commons.getExtension("a"));
        Assert.assertEquals("", Commons.getExtension(null));
        Assert.assertEquals("java", Commons.getExtension("a.cpp.java"));
    }

    @Test
    public void test49() throws Exception {
        FileInputStream in = new FileInputStream("z:/abc");
        String gbk = IOStreams.stream2String(in);
        System.out.println(gbk);
    }

    @Test
    public void test58() throws Exception {
        while (true) {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("start....");
            Robot robot = new Robot();
            robot.delay(100);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(100);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }

    }

    @Test
    public void test75() throws Exception {
        Assert.assertTrue(Commons.assertArgumentAmount(1, MathComparator.EQUAL));
        Assert.assertTrue(Commons.assertArgumentAmount(1, MathComparator.NOT_EQUAL));
        Assert.assertTrue(Commons.assertArgumentAmount(1, MathComparator.LESS));
        Assert.assertTrue(Commons.assertArgumentAmount(1, MathComparator.LESS_EQUAL));
        Assert.assertTrue(Commons.assertArgumentAmount(1, MathComparator.GREATER));
        Assert.assertTrue(Commons.assertArgumentAmount(1, MathComparator.GREATER_THAN));
        Assert.assertTrue(Commons.assertArgumentAmount(new ArrayList<String>(), MathComparator.IN));
        Assert.assertTrue(Commons.assertArgumentAmount(new HashSet<String>(), MathComparator.IN));
        Assert.assertTrue(Commons.assertArgumentAmount(Pair.makePair(1, 2), MathComparator.BETWEEN));
        Assert.assertTrue(!Commons.assertArgumentAmount(Pair.makePair(2, 1), MathComparator.BETWEEN));
    }

    @Test
    public void test93() throws Exception {
        Commons.printList(Commons.generateList(1, 2, 3, 4));
        Commons.printList(Commons.generateList(null));
    }

    @Test
    public void test97() throws Exception {
        int[] arr = {1, 2, 3};
        Commons.print(arr);
        Commons.print(null);
        Commons.print(Commons.generateList(1, 2, 3));
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(4, "d");
        Commons.print(map);
    }

    @Test
    public void test113() throws Exception {
    }

    @Test
    public void test116() throws Exception {

        Commons.print(Commons.range(5, 1));
        Commons.printSplits();
    }

    @Test
    public void test125() throws Exception {
        Commons.printAscii();
    }

    @Test
    public void test130() throws Exception {
        Commons.zip(new File("z:/a"), new File("z:/b.zip"));
        Commons.unZip(new File("z:/b.zip"), new File("z:/c"));
    }

    @Test
    public void test135() throws Exception {

        Map<Integer, String> r = Commons.parseR(Paths.get("z:/R.java"));
        Commons.print(r);
        String str = "case aaaaaaa2131427777:case 2131427779:case 2131427781:";
        Pattern pattern = Pattern.compile("\\d+");

        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String group = matcher.group();
            String replacement = r.get(Integer.parseInt(group));
            System.out.println(replacement);
            if (group.length() == 10 && replacement !=null) {
                str = str.replace(group, replacement);
            }
            Commons.log(str);
        }
    }

    @Test
    public void test165() throws Exception {
        System.out.println(UUID.randomUUID());

    }
}
