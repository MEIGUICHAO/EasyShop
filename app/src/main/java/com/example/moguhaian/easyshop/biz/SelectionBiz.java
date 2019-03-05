package com.example.moguhaian.easyshop.biz;

import android.text.TextUtils;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.JsoupUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SelectionBiz extends BaseBiz {

    public void dropDown(WebView webView) {
//        widget-multi-dropdown
        webView.loadUrl(JsUtils.addJsMethod("foreachTable(\"" + "ui-table ui-table-simple" + "\")"));
//        singleThreadExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                JsoupUtils.parseForm("https://www.taosj.com/tool/tool.htm#/optimize_market/keyword/", "ui-table ui-table-simple");
//            }
//        });
//        webView.loadUrl("findElementsByClassName(\"" + "multi-dropdown J_MutliSelect" + "\")");
//        singleThreadExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                jsoupData(Constants.SelectionUrl);
//            }
//        });
    }


    public String jsoupData(String url) {
        String json = "";
        try {
            Document document = Jsoup.connect(url).cookie("Cookie", SharedPreferencesUtils.getValue(Constants.Cookies)).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
//            Document document = Jsoup.connect(url).cookie("Cookie", "t=09739d8b9ea2481146e732e9f3c29613; cookie2=18ebce4230b2907136b111c94bb425f0; v=0; _tb_token_=e7a48e5e3fefe").userAgent(Constants.UserAgentString).ignoreContentType(true).get();
            Elements script = document.getElementsByTag("script");
            for (Element ele : script) {
                if (ele.data().contains("g_page_config")) {
                    json = ele.data();
                }
            }

            if (TextUtils.isEmpty(json)) {
                LogUtils.e(document.toString());
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());

        }
        return json;
    }
}
