package com.example.moguhaian.easyshop.Search;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SearchBiz extends BaseBiz {


    public void getTitleSplitArray(final String url) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = jsoupData(url);
                TaoUtils.getNameSplitResult(json);
            }
        });
    }


    public void getSameStyleBean(final String url, final JsoupParseListener listener) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = jsoupData(url);
                if (!TextUtils.isEmpty(json)) {
                    TaoUtils.getSameStyleInfoBean(json);
                    try {

                        LogUtils.e("before");
                        Thread.sleep(3000);
                        LogUtils.e("after");
                        listener.complete();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFail(url);
                        }
                    });

                }
            }
        });
    }


    private String jsoupData(String url) {
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

    public void findSameStyleUrl(final WebView wvSearch, final String name) {
        wvSearch.loadUrl(JsUtils.addJsMethod("findSameStyle(\"" + name + "\")"));
    }
}
