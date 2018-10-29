package com.rmmis.platform.util;

import android.util.Log;

/**
 * Created by Spy on 2016/3/28.
 */
public class LogUtil {
    public static String logStr = "zwp";
    public static boolean open = true;

    public static void showLog(String log) {
        if (open) {
//            int max_str_length=2001-logStr.length();
//            //大于4000时
//            while (log.length()>max_str_length){
//                Log.e(logStr, log.substring(0,max_str_length) );
//                log=log.substring(max_str_length);
//            }
//            //剩余部分
//            Log.i(logStr, log );

            if (log.length() > 4000) {
                int chunkCount = log.length() / 4000;     // integer division
                for (int i = 0; i <= chunkCount; i++) {
                    int max = 4000 * (i + 1);
                    if (max >= log.length()) {
                        Log.i(logStr, log.substring(4000 * i + 1));
                    } else {
                        Log.i(logStr, log.substring(4000 * i, max));
                    }
                }
            } else {
                Log.i(logStr, log);
            }
        }

    }
}
