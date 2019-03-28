package com.example.moguhaian.easyshop.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.example.moguhaian.easyshop.Bean.SameSytleUrlBean;
import com.example.moguhaian.easyshop.Bean.SamestyleBean;
import com.example.moguhaian.easyshop.Utils.GestureTouchUtils;
import com.example.moguhaian.easyshop.Utils.GreenDaoUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.util.List;

@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class LocalMethod {

    private final MyWebView mWebView;
    Activity mContext;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String json;

    public LocalMethod(Activity c, MyWebView webView) {
        this.mContext = c;
        mWebView = webView;
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void JI_LOG(final String content) {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("JI_LOG: " + content);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void getJsonData(final String content) {
        json = content;
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void slideTouch(final int left, final int width, final int bottom) {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GestureTouchUtils.simulateScroll(mWebView, 600, 244, 920, 244, 2000, GestureTouchUtils.HIGH);
//                for (int i = 3 * bottom/4; i < bottom; i++) {
//                    GestureTouchUtils.simulateScroll(mWebView, left, i, width * 3, i, 2000, GestureTouchUtils.HIGH);
//                }
            }
        });


    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void insertSameStyleUrls(String name, String[] urls) {

        List<SamestyleBean> list = GreenDaoUtils.getSameStyleBeanByProductName(name);
        if (list.size() < 1) {
            SamestyleBean bean = new SamestyleBean();
            bean.setProductNames(name);
            GreenDaoUtils.insertSameStyleBean(bean);
        }
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i].split("&nid=")[0] + Constants.SaleDescSort;
            insertSameStyleUrlBean(name, url);
        }
        LogUtils.e("find complete");
//
    }

    private void insertSameStyleUrlBean(String name, String url) {
        SamestyleBean samestyleBean = GreenDaoUtils.getSameStyleBeanByProductName(name).get(0);
        if (!GreenDaoUtils.isUrlExist(url,samestyleBean.getId())) {
            SameSytleUrlBean sameSytleUrlBean = new SameSytleUrlBean();
            sameSytleUrlBean.setProductId(samestyleBean.getId());
            sameSytleUrlBean.setSameStyleUrl(url);
            GreenDaoUtils.insertSameStyleUrlBean(sameSytleUrlBean);
        }
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void JsLog(String string) {
        LogUtils.e(string);
    }

}
