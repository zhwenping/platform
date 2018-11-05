package com.rmmis.platform.util;

import android.util.Log;

import com.rmmis.platform.net.Constant;

/**
 * Created by Spy on 2016/3/28.
 */
public class LogUtil {
    public static String logStr = "zwp";

    public static void showLog(String log) {
        if (Constant.logFlag) {
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
