package com.example.moguhaian.easyshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.View.Ali1688Vu;
import com.example.moguhaian.easyshop.biz.Ali1688Biz;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Ali1688Fragment extends BaseFragment<Ali1688Vu, Ali1688Biz> implements LoadFinishListener {

    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;
    private String[] items = {"1688","一件代发"};
    private int clickPosition;
    private String url = "https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        vu.initWebViewSetting(webView, getActivity());
        biz.initWebView(webView, getActivity());
        biz.getWebViewClient().setOnLoadFinishListener(Ali1688Fragment.this);
        setDataStrs(items);


    }

    @Override
    public void fragmentRightClick(int position) {
        super.fragmentRightClick(position);
        clickPosition = position;
        switch (clickPosition) {
            case 0:
                webView.loadUrl(url);
                break;
            case 1:
                webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
                break;
            case 2:
                break;
        }

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

    @Override
    public void loadFinish(WebView wv, String url) {
        switch (clickPosition) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
