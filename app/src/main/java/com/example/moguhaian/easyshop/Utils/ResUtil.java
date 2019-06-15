package com.example.moguhaian.easyshop.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.example.moguhaian.easyshop.Base.BaseApplication;

public class ResUtil {



    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }
    /**
     * get color
     *
     * @param res
     * @return
     */
    public static int getC(@ColorRes int res) {
        return ContextCompat.getColor(BaseApplication.getInstances(), res);
    }

    /**
     * get String
     *
     * @param res
     * @return
     */
    public static String getS(@StringRes int res) {
        return BaseApplication.getInstances().getString(res);
    }

    /**
     * get String
     *
     * @param res
     * @return
     */
    public static String getS(@StringRes int res, Object... formatArgs) {
        return BaseApplication.getInstances().getString(res, formatArgs);
    }

    /**
     * get String
     *
     * @param res        有占位符的String res
     * @param resReplace 要替换的String res
     * @return
     */
    public static String getS(@StringRes int res, @StringRes int resReplace) {
        return BaseApplication.getInstances().getString(res, getS(resReplace));
    }


    /**
     * get String
     *
     * @param res         有两个占位符的String res
     * @param resReplace1 要替换的String res
     * @param resReplace2 要替换的String res
     * @return
     */
    public static String getS(@StringRes int res, @StringRes int resReplace1, @StringRes int resReplace2) {
        return getS(res, getS(resReplace1), getS(resReplace2));
    }

    /**
     * get String
     *
     * @param res         有三个占位符的String res
     * @param resReplace1 要替换的String res
     * @param resReplace2 要替换的String res
     * @param resReplace3 要替换的String res
     * @return
     */
    public static String getS(@StringRes int res, @StringRes int resReplace1, @StringRes int resReplace2, @StringRes int resReplace3) {
        return getS(res, getS(resReplace1), getS(resReplace2), getS(resReplace3));
    }

    /**
     * 获取尺寸
     */
    public static float getDimen(Context context, @DimenRes int id) {
        return context.getResources().getDimension(id);
    }

    /**
     * 获取Drawable
     */
    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }

    /**
     * 根据名字获取ID
     */
    public static int getId(String name, String type) {
        int id = 0;
        try {
            id = BaseApplication.getInstances().getResources().getIdentifier(name, type,
                    BaseApplication.getInstances().getPackageName());
        } catch (Exception e) {
        }
        return id;
    }

    /**
     * 去掉小数点后面的 0
     *
     * @param d
     * @return
     */
    public static String getText(double d) {
        return d % 1 == 0 ? (int) d + "" : d + "";
    }
}
