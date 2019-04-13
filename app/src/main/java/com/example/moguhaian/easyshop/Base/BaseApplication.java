package com.example.moguhaian.easyshop.Base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.moguhaian.easyshop.DaoMaster;
import com.example.moguhaian.easyshop.DaoSession;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseApplication extends Application {

    private static BaseApplication instances;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static String getInjectJS() {
        return injectJS;
    }

    private static String injectJS;

    public static boolean isCookieOpen() {
        return cookieOpen;
    }

    public static void setCookieOpen(boolean open) {
        cookieOpen = open;
    }

    private static boolean cookieOpen = false;


    private static final String BASIC_JS_PATH = "basic_inject.js";

    public static Handler getmHandler() {
        return mHandler;
    }

    private static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



    @Override
    public void onCreate() {
        super.onCreate();

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

        instances = this;
        initData();
        setDatabase();
    }

    private void initData() {
        injectJS = "javascript:" + getJsFromFile(this, BASIC_JS_PATH);
        LogUtils.e(injectJS);

    }

    public static BaseApplication getInstances(){
        return instances;
    }


    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 注入本地文件中的JS方法
     */
    protected String getJsFromFile(Application mContext, String jsPath) {
        InputStream in = null;
        try {
            in = mContext.getBaseContext().getAssets().open(jsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte buff[] = new byte[1024];
        ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
        do {
            int numRead = 0;
            try {
                numRead = in.read(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (numRead <= 0) {
                break;
            }
            fromFile.write(buff, 0, numRead);
        } while (true);

        return fromFile.toString();
    }
}
