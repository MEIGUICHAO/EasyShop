package com.example.moguhaian.easyshop.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment<Vu extends BaseVu,Biz extends BaseBiz> extends Fragment {

    private Vu vu;
    private Biz biz;

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
        ButterKnife.bind(this, view);

        return view;

    }








    protected abstract int getLayoutId();

    protected abstract void afterOnCreate();

    protected abstract Class<Vu> getVuClass();

    protected abstract Class<Biz> getBizClass();

}
