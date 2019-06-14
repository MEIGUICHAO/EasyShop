package com.example.moguhaian.easyshop.Utils;

import android.util.Log;

public class LogUtils {
    private static int LOG_MAXLENGTH = 8000;
    private static String tag = "EASYSHOP_ALI";

    public static void e(String msg) {
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.e(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.e(tag, msg);
    }
}
