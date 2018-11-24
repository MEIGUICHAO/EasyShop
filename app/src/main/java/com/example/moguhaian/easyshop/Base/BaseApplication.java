package com.example.moguhaian.easyshop.Base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.moguhaian.easyshop.Utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseApplication extends Application {

    private static Context mContext;

    public static String getInjectJS() {
        return injectJS;
    }

    private static String injectJS;

    public static Context getContext() {
        return mContext;
    }

    private static final String BASIC_JS_PATH = "basic_inject.js";



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initData();
    }

    private void initData()
    {
        injectJS = "javascript:" + getJsFromFile(this, BASIC_JS_PATH);
        LogUtils.e(injectJS);

    }


    /** 注入本地文件中的JS方法 */
    protected String getJsFromFile(Application mContext, String jsPath)
    {
        InputStream in = null;
        try
        {
            in = mContext.getBaseContext().getAssets().open(jsPath);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        byte buff[] = new byte[1024];
        ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
        do
        {
            int numRead = 0;
            try
            {
                numRead = in.read(buff);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            if (numRead <= 0)
            {
                break;
            }
            fromFile.write(buff, 0, numRead);
        } while (true);

        return fromFile.toString();
    }
}
