package com.example.moguhaian.easyshop.Base;

import android.webkit.WebView;

import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.UrlUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseBiz {
    public ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    WebView webView;


    public void initWebView(WebView wv, LoadFinishListener loadFinishListener) {
        this.webView = wv;
        webView.setWebViewClient(new MyWebViewClient(loadFinishListener));

    }


    public void TBLogin() {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connect = Jsoup.connect("https://login.taobao.com/");
                    // 带参数开始
                    connect.data("TPL_username", "大王派我来巡山23333:鬼鬼");
                    connect.data("TPL_password", "m12345678");
                    connect.method(Connection.Method.POST);
                    Connection.Response execute = connect.execute();
                    Map<String, String> cookies = execute.cookies();
                    String cookie = cookies.toString();
                    cookie = cookie.substring(cookie.indexOf("{")+1, cookie.lastIndexOf("}"));
                    cookie = cookie.replaceAll(",", ";");
                    SharedPreferencesUtils.putValue(Constants.Cookies, cookie);
                } catch (Exception e) {

                    LogUtils.e(e.toString());

                }
            }
        });

    }

    public void loadTBSearchUrlByName(String name) {
        webView.loadUrl(UrlUtils.setQueryWord(name));
    }

}
