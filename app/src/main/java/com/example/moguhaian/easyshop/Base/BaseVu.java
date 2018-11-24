package com.example.moguhaian.easyshop.Base;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Search.SearchTaobaoActivity;

import butterknife.ButterKnife;

public abstract class BaseVu implements Vu {
    public View view;

    public void initWebViewSetting(WebView webView, Context context) {
        WebSettings webSetting = webView.getSettings();
        // 支持获取手势焦点
        webView.requestFocusFromTouch();
        webView.setHorizontalFadingEdgeEnabled(true);
        webView.setVerticalFadingEdgeEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        // 支持JS
        webSetting.setJavaScriptEnabled(true);
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
        webSetting.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        //自适应屏幕
        //自动缩放
        webSetting.setBuiltInZoomControls(true);
        webSetting.setSupportZoom(true);
        //支持获取手势焦点
//        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new LocalMethod(context), "localMethod");
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

}
