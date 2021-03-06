package com.example.moguhaian.easyshop.Base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.moguhaian.easyshop.MainActivity;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.fragment.Ali1688Fragment;
import com.example.moguhaian.easyshop.listener.ShouldOverrideUrlLoadingListener;

import java.util.List;

public class MyWebViewClient extends WebViewClient {


    private final MainActivity mActivity;
    private Ali1688Fragment fragment;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private String userAgent = "";

    ShouldOverrideUrlLoadingListener listener;

    public void setShouldLoadingListener(ShouldOverrideUrlLoadingListener shouldLoadingListener) {
        listener = shouldLoadingListener;
    }

    private float indexScale = -1;
    public MyWebViewClient(Activity activity) {
        mActivity = (MainActivity) activity;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getClass().getSimpleName().equals("Ali1688Fragment")) {
                fragment = (Ali1688Fragment) fragments.get(i);
                break;
            }
        }

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.loadUrl(request.getUrl().toString());
            LogUtils.e("shouldOverrideUrlLoading:" + request.getUrl().toString());
            url = request.getUrl().toString();
        } else {
            view.loadUrl(request.toString());
            LogUtils.e("shouldOverrideUrlLoading:" + request.toString());
            url = request.toString();
        }
        if (url.contains("http://item.publish.taobao.com")) {
            if (null != listener) {
                listener.goPublish(url);
            }
        }
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.e("shouldOverrideUrlLoading:" + url);
        if (url.contains("http://item.publish.taobao.com")) {
            if (null != listener) {
                listener.goPublish(url);
            }
        } else {
            view.loadUrl(url);
        }
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
        if (newScale > 0.63) {
            view.zoomOut();
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (newScale > 0.63) {
                        if (null != view) {
                            if (!TextUtils.isEmpty(view.getUrl())) {
                                if (null != fragment && !view.getUrl().contains("login")) {
                                    fragment.hideKeybord();
                                }
                            }
                        }
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
