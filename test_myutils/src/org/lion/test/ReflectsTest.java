package org.lion.test;

import org.junit.Test;
import org.lion.utils.Commons;
import org.lion.utils.Reflects;

/**
 * Created by more on 2016-05-10 20:45:05.
 */
public class ReflectsTest {
    @Test
    public void test8() throws Exception {
        int a[] = {1};
        long b[] = {2};
        Commons.log(Reflects.isTypeOf(a, int[].class));
        Commons.log(Reflects.isTypeOf(b, long[].class));
        
        
    }

    @Test
    public void test23() throws Exception {
        /*
        class [B
        class [S
        class [I
        class [J
        class [C
        class [Z
        class [F
        class [D
        class [Ljava.lang.Void;

         */
        byte[] bytes = {1,2,3};
        short[] shorts = {1,2,3};
        int[] ints = {1,2,3};
        long[] longs = {1,2,3};
        char[] chars={1,2,3};
        boolean[] booleans = {false};
        float [] floats = {1.2f};
        double[] doubles={1.2};

        Void[] voids = {};
        System.out.println(bytes.getClass().toString());
        System.out.println(shorts.getClass().toString());
        System.out.println(ints.getClass().toString());
        System.out.println(longs.getClass().toString());
        System.out.println(chars.getClass().toString());
        System.out.println(booleans.getClass().toString());
        System.out.println(floats.getClass().toString());
        System.out.println(doubles.getClass().toString());
        System.out.println(voids.getClass().toString());
    }
}
