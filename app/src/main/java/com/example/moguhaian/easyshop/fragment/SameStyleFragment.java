package com.example.moguhaian.easyshop.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Search.SameStyleBiz;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.View.SameStyleVu;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SameStyleFragment extends BaseFragment<SameStyleVu, SameStyleBiz> {
    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;

//    private String shopsUrl = "https://www.taobao.com/?spm=a21bo.2017.201857.1.5c0111d9sMj916";
    private String shopsUrl = "https://s.taobao.com/search?spm=a230r.1.14.107.7396d7b2qjum31&type=samestyle&app=i2i&rec_type=1&uniqpid=-580033393&nid=568968377828&sort=sale-desc";
    private String url = "https://s.taobao.com/search?&initiative_id=tbindexz_20170306&ie=utf8&spm=a21bo.2017.201856-taobao-item.2&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all&imgfile=&q=%E7%A7%AF%E6%9C%A8%E6%8B%BC%E8%A3%85%E7%8E%A9%E5%85%B7%E7%9B%8A%E6%99%BA&suggest=0_1&_input_charset=utf-8&wq=%E7%A7%AF%E6%9C%A8&suggest_query=%E7%A7%AF%E6%9C%A8&source=suggest";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        vu.initWebViewSetting(webView,getActivity());
        biz.initWebView(webView, getActivity());
//        webView.loadUrl(url);


    }

    @Override
    public void fragmentRightClick(int position) {
//        webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.evaluateJavascript(JsUtils.addJsMethod("getDocument()"), new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {
//
//                }
//            });
//        } else {
//
//        }
        biz.jsoupShopList(url, new JsoupParseListener() {
            @Override
            public void complete() {

            }

            @Override
            public void onFail(String url) {

            }
        });

    }

    @Override
    protected Class getVuClass() {
        return SameStyleVu.class;
    }

    @Override
    protected Class getBizClass() {
        return SameStyleBiz.class;
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
