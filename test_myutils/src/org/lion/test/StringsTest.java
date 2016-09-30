package org.lion.test;

import org.junit.Assert;
import org.junit.Test;
import org.lion.utils.Strings;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by more on 2016-05-08 11:39:51.
 * Test class Strings
 */
public class StringsTest {
    @Test
    public void testTrimRight() {
        Assert.assertTrue(Strings.trimRight("a ").length() == 1);
        Assert.assertTrue(Strings.trimRight("a  ").length() == 1);
        Assert.assertTrue(Strings.trimRight(" a  ").length() == 2);
        Assert.assertTrue(Strings.trimRight("  ").length() == 0);
        Assert.assertTrue(Strings.trimRight("").length() == 0);
        Assert.assertTrue(Strings.trimRight(null) == null);
    }

    @Test
    public void testTrimLeft() {
        Assert.assertEquals("a", Strings.trimLeft(" a"));
        Assert.assertEquals("a", Strings.trimLeft("  a"));
        Assert.assertEquals("a ", Strings.trimLeft("a "));
        Assert.assertEquals("", Strings.trimLeft(""));
        Assert.assertEquals("", Strings.trimLeft("              "));
        Assert.assertEquals(null, Strings.trimLeft(null));

    }

    @Test
    public void testRepeat() {
        Assert.assertEquals("aa", Strings.repeat("a", 2));
        Assert.assertEquals("", Strings.repeat("", 2));
        Assert.assertEquals(null, Strings.repeat(null, 2));
    }

    @Test
    public void testIsNullOrEmpty() {
        Assert.assertTrue(Strings.isNullOrEmpty(null));
        Assert.assertTrue(Strings.isNullOrEmpty(""));
        Assert.assertTrue(Strings.isNullOrEmpty(new StringBuilder()));
        Assert.assertTrue(Strings.isNullOrEmpty(new StringBuffer()));
        Assert.assertFalse(Strings.isNullOrEmpty("a"));

    }

    @Test
    public void test50() throws FileNotFoundException {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("赵四", 21);
        map.put("王二", 17);
        map.put("张三", 18);
        map.put("小丫", 25);
        map.put("李四", 26);
        map.put("王五", 38);

        for (String s : map.keySet()) {
            System.out.printf("%s = %d\n", s, map.get(s));

        }
        System.out.println("==================================");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.printf("%s = %d\n", entry.getKey(), entry.getValue());


        }
        map.put("小丫", 18);
        PrintStream stream = new PrintStream("z:/student.txt");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 24) {
                stream.printf("%s = %d\n", entry.getKey(), entry.getValue());
            }

        }
        stream.close();


    }
}
