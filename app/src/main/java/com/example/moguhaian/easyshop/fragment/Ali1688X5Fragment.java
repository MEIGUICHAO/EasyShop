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
import com.example.moguhaian.easyshop.Base.Shops;
import com.example.moguhaian.easyshop.MainActivity;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.View.Ali1688Vu;
import com.example.moguhaian.easyshop.X5.X5WebView;
import com.example.moguhaian.easyshop.biz.Ali1688Biz;
import com.example.moguhaian.easyshop.weidge.MyX5WebView;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Ali1688X5Fragment extends Base1688Fragment{


//    @BindView(R.id.wv_x5)
//    MyX5WebView wvX5;
    Unbinder unbinder;
    FrameLayout flWv;
    private String[] items = {"1688"};
    private X5WebView mWebView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_1688_x5;
    }

    @Override
    protected void afterOnCreate() {
        flWv = view.findViewById(R.id.fl_wv);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
                getActivity().getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }


        mWebView = new X5WebView(getActivity(), null);

        flWv.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

        urlOrigin = urlOrigin1 + Shops.aliName + urlOrigin2;
        activity = (MainActivity) getActivity();
        vu.initX5WebViewSetting(mWebView, getActivity());
        biz.initX5WebView(mWebView, getActivity());
//        biz.getWebViewClient().setOnLoadFinishListener(Ali1688X5Fragment.this);
        vu.getLocalMethod().setLocalMethodListener(this);
        setDataStrs(items);


    }

}
