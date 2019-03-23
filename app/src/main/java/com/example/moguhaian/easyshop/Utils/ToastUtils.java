package com.example.moguhaian.easyshop.Utils;

import android.widget.Toast;

import com.example.moguhaian.easyshop.Base.BaseApplication;

public class ToastUtils {

    public static void showToast(final String string) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseApplication.getInstances(), string, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
