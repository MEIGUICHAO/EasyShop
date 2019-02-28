package com.example.moguhaian.easyshop.Base;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.moguhaian.easyshop.listener.LoadFinishListener;

public class MyWebViewClient extends WebViewClient {


    LoadFinishListener listener;

    public MyWebViewClient(LoadFinishListener loadFinishListener) {
        listener = loadFinishListener;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl(BaseApplication.getInjectJS());
        super.onPageFinished(view, url);
        listener.loadFinish(view,url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

    }
}
