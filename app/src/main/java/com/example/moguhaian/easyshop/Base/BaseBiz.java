package com.example.moguhaian.easyshop.Base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.moguhaian.easyshop.Utils.UrlUtils;

public class BaseBiz {

    WebView webView;



    public void initWebView(WebView wv,LoadFinishListener loadFinishListener) {
        this.webView = wv;
        webView.setWebViewClient(new MyWebViewClient(loadFinishListener));
    }

    public void loadTBSearchUrlByName(String name) {
        webView.loadUrl(UrlUtils.setQueryWord(name));
    }

}
