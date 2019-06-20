package com.example.moguhaian.easyshop.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.ResUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<Vu extends BaseVu,Biz extends BaseBiz> extends Fragment {

    public Vu vu;
    public Biz biz;
    public View view;

    public String[] getDataStrs() {
        return dataStrs;
    }

    public void setDataStrs(int[] dataStrs) {
        String[] strings = new String[dataStrs.length];
        for (int i = 0; i < dataStrs.length; i++) {
            strings[i] = ResUtil.getS(dataStrs[i]);
        }
        this.dataStrs = strings;
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

    public int clickPosition;


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
        view = inflater.inflate(getLayoutId(), container, false);
        afterOnCreate();

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
