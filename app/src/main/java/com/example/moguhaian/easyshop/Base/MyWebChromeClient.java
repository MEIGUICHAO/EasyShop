package com.example.moguhaian.easyshop.Base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Utils.LogUtils;

public class MyWebChromeClient extends WebChromeClient {


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        view.loadUrl("javascript:" + BaseApplication.getInjectJS());
    }
}
