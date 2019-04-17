package com.example.moguhaian.easyshop.Base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

public class MyWebChromeClient extends WebChromeClient {

    LoadFinishListener listener;

//    public boolean isNeedListener() {
//        return needListener;
//    }
//
//    public void setNeedListener(boolean needListener) {
//        this.needListener = needListener;
//    }

    private boolean needListener = true;


    public void setOnLoadFinishListener(LoadFinishListener onLoadFinishListener) {
        listener = onLoadFinishListener;
    }



    @Override
    public void onProgressChanged(final WebView view, int newProgress) {
        view.loadUrl("javascript:" + BaseApplication.getInjectJS());
        super.onProgressChanged(view, newProgress);
        if (newProgress == 100) {
            if (null != listener && needListener) {
                needListener = false;
                if (view instanceof MyWebView) {
                    view.scrollTo(0, ((MyWebView) view).getScrollYRange());
                }
                BaseApplication.getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        listener.loadFinish(view, view.getUrl());
                    }
                });
            }
        } else {
            needListener = true;
        }
    }
}
