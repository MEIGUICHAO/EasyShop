package com.example.moguhaian.easyshop.Search;

import android.text.TextUtils;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Base.JsoupParseListener;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;

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
                }
            }
        });
    }



    private String jsoupData(String url) {
        String json = "";
        try {
            Document document = Jsoup.connect(url).cookie("Cookie", SharedPreferencesUtils.getValue(Constants.Cookies)).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
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

    public void findSameStyleUrl(WebView wvSearch, String name) {
        wvSearch.loadUrl(JsUtils.addJsMethod("findSameStyle(\"" + name + "\")"));
    }
}
