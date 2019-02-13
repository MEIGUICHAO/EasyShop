package com.example.moguhaian.easyshop.fragment;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.View.Top20wVu;
import com.example.moguhaian.easyshop.biz.Top20wBiz;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import butterknife.BindView;

public class Top20wFragment extends BaseFragment<Top20wVu, Top20wBiz> {

    @BindView(R.id.webView)
    MyWebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        LogUtils.e("Top20wFragment loadUrl");
        vu.initWebViewSetting(webView,getActivity());
        webView.loadUrl(Constants.Top20wUrl);

    }

    @Override
    protected Class getVuClass() {
        return Top20wVu.class;
    }

    @Override
    protected Class getBizClass() {
        return Top20wBiz.class;
    }
}
