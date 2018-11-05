package com.rmmis.platform.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Edianzu on 2018/11/2.
 */

public class TimeUtil {
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
