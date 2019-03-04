package com.example.moguhaian.easyshop.Base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        view.loadUrl("javascript:" + BaseApplication.getInjectJS());
        super.onProgressChanged(view, newProgress);
    }
}
