package com.example.moguhaian.easyshop.Utils;

import android.util.TypedValue;

import com.example.moguhaian.easyshop.Base.BaseApplication;

public class UiUtils {

    /**
     * dp转px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BaseApplication.getInstances().getResources().getDisplayMetrics());
    }

    public static int getC(int color_white) {
        return BaseApplication.getInstances().getResources().getColor(color_white);
    }
}
