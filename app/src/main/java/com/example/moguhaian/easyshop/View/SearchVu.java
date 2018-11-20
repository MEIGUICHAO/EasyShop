package com.example.moguhaian.easyshop.View;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseVu;
import com.example.moguhaian.easyshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchVu extends BaseVu {



    public void initWebView(WebView wvSearch) {
        wvSearch.loadUrl("https://www.baidu.com/");

    }


    public void initWebViewSetting(WebView webView) {

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
//		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.supportMultipleWindows();
        webSetting.setSupportMultipleWindows(true);
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


//
//		webSetting.setJavaScriptEnabled(true);
//		webSetting.setDefaultTextEncodingName("utf-8");
//		webSetting.setAllowFileAccess(true);
//		webSetting.setUseWideViewPort(true);
//		webSetting.setLoadWithOverviewMode(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        webView.loadUrl("https://www.baidu.com/");
//        webView.setWebViewClient(new MyListWebViewClient());
//        mLocalMethod = new WA_YundaFragment.LocalMethod(getActivity(), parameter);
//        webView.addJavascriptInterface(mLocalMethod, "localMethod");
    }


}
