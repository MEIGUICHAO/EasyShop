package com.example.moguhaian.easyshop.Base;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.weidge.MyWebView;

public abstract class BaseVu implements Vu {
    public View view;

    public LocalMethod getLocalMethod() {
        return localMethod;
    }

    public void setLocalMethod(LocalMethod localMethod) {
        this.localMethod = localMethod;
    }

    private LocalMethod localMethod;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void initWebViewSetting(MyWebView webView, Activity context) {

        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setUserAgentString(Constants.UserAgentString);

        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //支持获取手势焦点
        localMethod = new LocalMethod(context, webView);
        webView.addJavascriptInterface(localMethod, "localMethod");
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


    }

    public void blockNetIamge(final WebView webView, final boolean block) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {

                WebSettings webSetting = webView.getSettings();
                webSetting.setBlockNetworkImage(block);
            }
        });
    }

    public boolean onBackPress(WebView webView) {
        if (null == webView) {
            return false;
        }
        boolean canGoBack = webView.canGoBack();
        if (webView.canGoBack()) {
            webView.goBack();
        }
        return canGoBack;
    }

}
