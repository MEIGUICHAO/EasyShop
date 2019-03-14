package com.example.moguhaian.easyshop.Utils;

import android.util.Log;

public class LogUtils {
    private static int LOG_MAXLENGTH = 8000;

    public static void e(String msg) {

        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                Log.e("tblm" + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e("tblm" + i, msg.substring(start, strLength));
                break;
            }
        }
    }
}
