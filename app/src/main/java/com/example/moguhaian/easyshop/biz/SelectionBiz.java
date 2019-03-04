package com.example.moguhaian.easyshop.biz;

import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseBiz;

public class SelectionBiz extends BaseBiz {

    public void dropDown(WebView webView) {
//        widget-multi-dropdown
        webView.loadUrl("findElementsByClassName(\"" + "multi-dropdown J_MutliSelect" + "\")");
    }
}
