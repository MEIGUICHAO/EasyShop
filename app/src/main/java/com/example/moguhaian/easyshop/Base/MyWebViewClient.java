package com.example.moguhaian.easyshop.Base;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
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


}
