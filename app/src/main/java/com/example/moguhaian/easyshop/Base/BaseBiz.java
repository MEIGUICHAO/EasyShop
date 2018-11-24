package com.example.moguhaian.easyshop.Base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.moguhaian.easyshop.Utils.UrlUtils;

public class BaseBiz {

    LoadFinishListener listener;
    WebView webView;



    public void initWebView(WebView wv,LoadFinishListener loadFinishListener) {
        this.webView = wv;
        this.listener = loadFinishListener;
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                BaseApplication.getInjectJS();
                super.onPageFinished(view, url);
                listener.loadFinish(view);
            }
        });
    }

    public void loadTBSearchUrlByName(String name) {
        webView.loadUrl(UrlUtils.setQueryWord(name));
    }

}
