package org.lion.utils;

/**
 * Created by more on 2016-05-10 20:41:04.
 * The tools of reflection
 */
public class Reflects {
    private Reflects() {
    }

    public static boolean isIntArray(Object obj) {
        return isTypeOf(obj, int[].class);
    }

    public static boolean isDoubleArray(Object obj) {
        return isTypeOf(obj, double[].class);
    }

    public static boolean isLongArray(Object obj) {
        return isTypeOf(obj, long[].class);
    }

    public static boolean isTypeOf(Object obj, Class cls) {
        return obj != null && cls.toString().equals(obj.getClass().toString());
    }
}
