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
        // 支持获取手势焦点
        webView.requestFocusFromTouch();
        webView.setHorizontalFadingEdgeEnabled(true);
        webView.setVerticalFadingEdgeEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        // 支持JS
        webSetting.setBlockNetworkImage(false);
        webSetting.setJavaScriptEnabled(true);
//        webView.loadData("", "text/html", "UTF-8");
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(true);
        webSetting.setLoadWithOverviewMode(true);
        // 支持插件
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 自适应屏幕
        webSetting.setUseWideViewPort(true);
        // 支持缩放
        webSetting.setSupportZoom(true
        );//就是这个属性把我搞惨了，
        // 隐藏原声缩放控件
        // 支持内容重新布局
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.supportMultipleWindows();
        // 设置缓存模式
        webSetting.setDomStorageEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setAppCacheEnabled(true);
        webSetting.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());
        // 设置可访问文件
        webSetting.setAllowFileAccess(true);
        webSetting.setNeedInitialFocus(true);
        // 支持自定加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            webSetting.setLoadsImagesAutomatically(true);
        } else {
            webSetting.setLoadsImagesAutomatically(false);
        }
        webSetting.setNeedInitialFocus(true);
        // 设定编码格式
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //支持js
        webSetting.setUserAgentString(Constants.UserAgentString);
        //自适应屏幕
        //自动缩放
        webSetting.setBuiltInZoomControls(true);
        webSetting.setSupportZoom(true);
        //支持获取手势焦点
//        webView.setWebViewClient(new MyWebViewClient());
        localMethod = new LocalMethod(context, webView);
        webView.addJavascriptInterface(localMethod, "localMethod");
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

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
