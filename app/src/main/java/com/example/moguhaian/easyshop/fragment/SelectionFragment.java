package com.example.moguhaian.easyshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Base.Shops;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.View.SelectionVu;
import com.example.moguhaian.easyshop.biz.SelectionBiz;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SelectionFragment extends BaseFragment<SelectionVu, SelectionBiz> {
    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;
    private String[] split;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        vu.initWebViewSetting(webView, getActivity());
        biz.initWebView(webView, getActivity());
        webView.loadUrl(Constants.SelectionUrl);
        split = Shops.shops.split("\n");
        LogUtils.e("split.length:" + split.length);
//        for (int i = 0; i < split.length; i++) {
//            LogUtils.e("split" + i + ":" + split[i]);
//        }

//        loginCode   loginPassword
//        biz.quickLogin(Constants.taosjLoginUrl, "loginCode", "18620587647", "loginPassword", "m123456", "taosjCookie", new JsoupParseListener() {
//            @Override
//            public void complete() {
//                webView.loadUrl(Constants.SelectionUrl);
//            }
//
//            @Override
//            public void onFail(String url) {
//
//                LogUtils.e("taosjLoginUrl quickLogin fail");
//            }
//        });
    }

    public void test(final int position) {

        biz.jsoupShop(split[position], new JsoupParseListener() {
            @Override
            public void complete() {
                LogUtils.e(position + "采集~~success!!!!!!!!!!!!!!!!!!!");
                if ((position + 1) < split.length) {
                    test(position + 1);
                }
            }

            @Override
            public void onFail(String url) {
                LogUtils.e(position + "采集~~fail!!!!!!!!!!!!!!!!!!!");
                if (position < split.length) {
                    test(position + 1);
                }
            }
        });
    }

    @Override
    protected Class<SelectionVu> getVuClass() {
        return SelectionVu.class;
    }

    @Override
    protected Class<SelectionBiz> getBizClass() {
        return SelectionBiz.class;
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
