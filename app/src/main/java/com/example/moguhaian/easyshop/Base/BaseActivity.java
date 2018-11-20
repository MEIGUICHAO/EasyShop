package com.example.moguhaian.easyshop.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.moguhaian.easyshop.R;

import butterknife.ButterKnife;

public abstract class BaseActivity<Vu extends BaseVu,Biz extends BaseBiz> extends Activity {

    public Vu vu;
    public Biz biz;

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
