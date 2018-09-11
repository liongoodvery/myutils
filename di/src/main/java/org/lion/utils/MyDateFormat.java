package org.lion.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by more on 2016-05-05 16:14:18.
 * Singleton SimpleDateFormat
 */
class MyDateFormat {
    private static final DateFormat instance = new SimpleDateFormat("HH:mm:ss.SSS");

    private MyDateFormat() {
    }

    public static DateFormat getInstance() {
        return instance;
    }


}
