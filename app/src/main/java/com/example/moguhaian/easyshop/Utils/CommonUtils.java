package com.example.moguhaian.easyshop.Utils;

import android.app.Instrumentation;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.moguhaian.easyshop.Base.BaseApplication;

import java.util.Random;

public class CommonUtils {

    private static ClipboardManager mClipboard;

    public static int[] getRandom(int n) {
        int[] x = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = i;
        }
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int in = r.nextInt(n - i) + i;
            int t = x[in];
            x[in] = x[i];
            x[i] = t;
        }
        return x;
    }


    public static void copyText(String text) {

        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) BaseApplication.getInstances().getSystemService(Context.CLIPBOARD_SERVICE);

        }

        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText("simple text", text);

        // Set the clipboard's primary clip.
        mClipboard.setPrimaryClip(clip);
    }

    public static String pasteToResult() {
        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) BaseApplication.getInstances().getSystemService(Context.CLIPBOARD_SERVICE);
        }

        String resultString = "";
        // 检查剪贴板是否有内容
        if (!mClipboard.hasPrimaryClip()) {
            Toast.makeText(BaseApplication.getInstances(),
                    "Clipboard is empty", Toast.LENGTH_SHORT).show();
        } else {
            ClipData clipData = mClipboard.getPrimaryClip();
            int count = clipData.getItemCount();

            for (int i = 0; i < count; ++i) {

                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item
                        .coerceToText(BaseApplication.getInstances());
                Log.i("mengdd", "item : " + i + ": " + str);

                resultString += str;
            }

        }
        return resultString;
    }

    public static void keySync() {
        int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X,KeyEvent.KEYCODE_DEL};
        for (int i = 0; i < keyCodeArray.length; i++) {
            typeIn(keyCodeArray[i]);
        }
    }

    public static void typeIn( final int KeyCode ){
        try {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync( KeyCode );
        } catch (Exception e) {
            Log.e("Exception：", e.toString());
        }
    }
}
