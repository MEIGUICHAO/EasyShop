package com.example.moguhaian.easyshop.fragment;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Search.SameStyleBiz;
import com.example.moguhaian.easyshop.View.SameStyleVu;

public class SameStyleFragment extends BaseFragment<SameStyleVu,SameStyleBiz> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {

    }

    @Override
    protected Class getVuClass() {
        return SameStyleVu.class;
    }

    @Override
    protected Class getBizClass() {
        return SameStyleBiz.class;
    }
}
