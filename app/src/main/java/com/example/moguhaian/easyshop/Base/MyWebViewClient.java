package com.example.moguhaian.easyshop.Base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;

public class MyWebViewClient extends WebViewClient {


    LoadFinishListener listener;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private String userAgent = "";

    public MyWebViewClient() {
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.toString());
        return true;
    }

    public void setOnLoadFinishListener(LoadFinishListener onLoadFinishListener) {
        listener = onLoadFinishListener;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl(BaseApplication.getInjectJS());

        if (BaseApplication.isCookieOpen()) {
            CookieManager cookieManager = CookieManager.getInstance();
            String CookieStr = cookieManager.getCookie(url);
            SharedPreferencesUtils.putValue(Constants.Cookies + userAgent, CookieStr);
            ToastUtils.showToast("获取cookie成功");
//            LogUtils.e("CookieStr:" + CookieStr);
            LogUtils.e("SharedPreferencesUtils_CookieStr:" + SharedPreferencesUtils.getValue(Constants.Cookies + userAgent));
        }

        super.onPageFinished(view, url);
        if (null != listener) {
            listener.loadFinish(view,url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        LogUtils.e("onReceivedError");

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//        handler.proceed();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        super.onReceivedSslError(view, handler, error);
    }


}
