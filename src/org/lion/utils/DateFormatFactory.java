package org.lion.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by more on 2016-05-05 16:33:24.
 * Some useful DateFormat instance
 */
public class DateFormatFactory {
    private DateFormatFactory() {
    }

    private static HashMap<String, DateFormat> FORMAT_MAP;

    public static final DateFormat HH_mm_ss;

    public static final DateFormat HH_mm_ss_SSS;

    public static final DateFormat yyyy_MM_dd;

    public static final DateFormat yyyy_MM_dd_HH_mm_ss;

    public static final DateFormat mm_ss;

    static {
        FORMAT_MAP=new HashMap<String, DateFormat>();
        HH_mm_ss = new SimpleDateFormat("HH:mm:ss");
        FORMAT_MAP.put("HH:mm:ss", HH_mm_ss);

        HH_mm_ss_SSS = new SimpleDateFormat("HH:mm:ss.SSS");
        FORMAT_MAP.put("HH:mm:ss.SSS", HH_mm_ss_SSS);

        yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        FORMAT_MAP.put("yyyy-MM-dd", yyyy_MM_dd);

        yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FORMAT_MAP.put("yyyy-MM-dd HH:mm:ss", yyyy_MM_dd_HH_mm_ss);

        mm_ss = new SimpleDateFormat("mm:ss");
        FORMAT_MAP.put("mm:ss", mm_ss);
    }

    public static DateFormat getInstance(String format) {
        if (FORMAT_MAP.containsKey(format)) {
            return FORMAT_MAP.get(format);
        }
        return new SimpleDateFormat(format);
    }
}
