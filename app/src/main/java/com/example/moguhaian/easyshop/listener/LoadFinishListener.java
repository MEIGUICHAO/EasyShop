package com.example.moguhaian.easyshop.listener;

import android.webkit.WebView;

public interface LoadFinishListener {
    void loadFinish(WebView wv, String url);

    void loadFinish(com.tencent.smtt.sdk.WebView wv, String url);

}
