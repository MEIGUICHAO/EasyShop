package com.example.moguhaian.easyshop.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<Vu extends BaseVu,Biz extends BaseBiz> extends Fragment {

    protected Vu vu;
    protected Biz biz;
    Unbinder unbinder;

    public String[] getDataStrs() {
        return dataStrs;
    }

    public void setDataStrs(String[] dataStrs) {
        this.dataStrs = dataStrs;
    }

    private String[] dataStrs;
    private ArrayList<String> items = new ArrayList<>();

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems() {
        if (items.size() < 1 && null != dataStrs) {
            for (int i = 0; i < dataStrs.length; i++) {
                items.add(dataStrs[i]);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            vu = getVuClass().newInstance();
            biz = getBizClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        afterOnCreate();

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    protected abstract int getLayoutId();

    protected abstract void afterOnCreate();

    protected abstract Class<Vu> getVuClass();

    protected abstract Class<Biz> getBizClass();

    public void fragmentRightClick(int position) {
        
    }

    public boolean canGoback() {
        return biz.webView.canGoBack();
    }

    public void webviewGoback() {
        biz.webView.goBack();
    }
}
