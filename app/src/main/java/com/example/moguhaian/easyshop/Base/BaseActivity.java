package com.example.moguhaian.easyshop.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.moguhaian.easyshop.Utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public abstract class BaseActivity<Vu extends BaseVu,Biz extends BaseBiz> extends AppCompatActivity {

    public Vu vu;
    public Biz biz;
    public Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            vu = getVuClass().newInstance();
            biz = getBizClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        afterOnCreate();

    }




    protected abstract int getLayoutId();

    protected abstract void afterOnCreate();

    protected abstract Class<Vu> getVuClass();

    protected abstract Class<Biz> getBizClass();


}
