package com.example.moguhaian.easyshop.Base;

import android.app.Activity;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Bean.SameStyleShopsBean;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.UrlUtils;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseBiz {
    public ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    WebView webView;

    public Activity activity;

    public void initWebView(WebView wv,Activity activity) {
        this.webView = wv;
        webView.setWebViewClient(new MyWebViewClient((LoadFinishListener) activity));
        this.activity = activity;
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
                    LogUtils.e("login success");
                    LogUtils.e(cookie);
                } catch (Exception e) {

                    LogUtils.e(e.toString());

                }
            }
        });

    }

    public void quickLogin(final String loginUrl, final String loginAccount, final String loginAccountValue, final String loginPSw, final String loginPSwValue, final String cacheName,final JsoupParseListener listener) {

        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connect = Jsoup.connect(loginUrl);

                    connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                    connect.header("Accept-Encoding", "gzip, deflate");
                    connect.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                    connect.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
                    connect.header("Content-Type", "application/x-www-form-urlencoded");
                    // 带参数开始
                    connect.data(loginAccount, loginAccountValue);
                    connect.data(loginPSw, loginPSwValue);
                    connect.method(Connection.Method.POST);
                    Connection.Response execute = connect.execute();
                    Map<String, String> cookies = execute.cookies();
                    String cookie = cookies.toString();
                    cookie = cookie.substring(cookie.indexOf("{")+1, cookie.lastIndexOf("}"));
                    cookie = cookie.replaceAll(",", ";");
                    SharedPreferencesUtils.putValue(cacheName, cookie);
                    LogUtils.e("login success");
                    LogUtils.e(cookie);
                    listener.complete();
                } catch (Exception e) {
                    listener.onFail("");
                    LogUtils.e(e.toString());

                }
            }
        });

    }



    public void loadTBSearchUrlByName(String name) {
        webView.loadUrl(UrlUtils.setQueryWord(name));
    }


    public int getRandomNum() {
        Random random = new Random();
        return random.nextInt(10) + 3000;
    }

    public void bubbleSort(List<SameStyleShopsBean.DataBean.ItemsBean> items) {
        SameStyleShopsBean.DataBean.ItemsBean[] itemsBeans = new SameStyleShopsBean.DataBean.ItemsBean[items.size()];
        for(int i=0;i<itemsBeans.length-1;i++){
            for(int j=0;j<itemsBeans.length-1-i;j++){
                if (Double.parseDouble(itemsBeans[j].getView_price()) > Double.parseDouble(itemsBeans[j + 1].getView_price())) {
                    SameStyleShopsBean.DataBean.ItemsBean tmp = itemsBeans[j + 1];
                    itemsBeans[j + 1] = itemsBeans[j];
                    itemsBeans[j] = tmp;
                }
            }
        }
    }

}
