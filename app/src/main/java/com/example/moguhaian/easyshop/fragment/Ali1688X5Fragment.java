package com.example.moguhaian.easyshop.fragment;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.View.Ali1688Vu;
import com.example.moguhaian.easyshop.X5.X5WebView;
import com.example.moguhaian.easyshop.biz.Ali1688Biz;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Ali1688X5Fragment extends BaseFragment<Ali1688Vu, Ali1688Biz> {


//    @BindView(R.id.wv_x5)
//    MyX5WebView wvX5;
    Unbinder unbinder;
    @BindView(R.id.fl_wv)
    FrameLayout flWv;
    private String[] items = {"1688"};
    private X5WebView mWebView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_1688_x5;
    }

    @Override
    protected void afterOnCreate() {
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
                getActivity().getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }

        setDataStrs(items);

        mWebView = new X5WebView(getActivity(), null);

        flWv.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));


    }


    @Override
    public void fragmentRightClick(int position) {
        super.fragmentRightClick(position);
//        wvX5.loadUrl("https://www.1688.com/");
        mWebView.loadUrl("https://www.1688.com/");

    }

    @Override
    protected Class getVuClass() {
        return Ali1688Vu.class;
    }

    @Override
    protected Class getBizClass() {
        return Ali1688Biz.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
