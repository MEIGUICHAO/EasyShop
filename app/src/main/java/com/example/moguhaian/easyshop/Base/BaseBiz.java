package com.example.moguhaian.easyshop.Base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Utils.UrlUtils;

public class BaseBiz {

    LoadFinishListener listener;
    WebView webView;



    public void initWebView(WebView wv,LoadFinishListener loadFinishListener) {
        this.webView = wv;
        this.listener = loadFinishListener;
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    listener.loadFinish(view);
                }
            }
        });
    }

    public void loadTBSearchUrlByName(String name) {
        webView.loadUrl(UrlUtils.setQueryWord(name));
    }

}
