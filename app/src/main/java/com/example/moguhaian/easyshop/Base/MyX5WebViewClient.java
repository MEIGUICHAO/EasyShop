package com.example.moguhaian.easyshop.Base;

import android.graphics.Bitmap;
import android.webkit.CookieManager;

import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MyX5WebViewClient extends WebViewClient {



    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private String userAgent = "";

    private float indexScale = -1;
    public MyX5WebViewClient() {

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.toString());
        return true;
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
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

    }

    @Override
    public void onScaleChanged(final WebView view, float oldScale, final float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
//        LogUtils.e("oldScale" + oldScale);
//        LogUtils.e("newScale" + newScale);
        if (indexScale == -1) {
            indexScale = oldScale;
        }
//        if (newScale < indexScale) {
//
//        }
        if (newScale > 0.65) {
            view.zoomOut();
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (newScale > 0.65) {
                        view.zoomOut();
                    } else {
                        BaseApplication.getmHandler().removeCallbacks(this);
                    }
                }
            }, 50);

        }

    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        LogUtils.e("onReceivedError");

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        super.onReceivedSslError(view, handler, error);
    }

}
