package com.example.moguhaian.easyshop.biz;

import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Utils.JsUtils;

public class SelectionBiz extends BaseBiz {

    public void dropDown(WebView webView) {
//        widget-multi-dropdown
//        webView.loadUrl(JsUtils.addJsMethod("jsCangkuGoNextPage();"));
        webView.loadUrl(JsUtils.addJsMethod("findElementsByClassName(\"" + "multi-dropdown J_MutliSelect" + "\")"));
//        webView.loadUrl("findElementsByClassName(\"" + "multi-dropdown J_MutliSelect" + "\")");
    }
}
