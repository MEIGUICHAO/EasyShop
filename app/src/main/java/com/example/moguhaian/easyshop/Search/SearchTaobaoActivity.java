package com.example.moguhaian.easyshop.Search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Base.LoadFinishListener;
import com.example.moguhaian.easyshop.Bean.SameSytleUrlBean;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.GreenDaoUtils;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.Utils.UrlUtils;
import com.example.moguhaian.easyshop.View.SearchVu;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTaobaoActivity extends BaseActivity<SearchVu, SearchBiz> implements LoadFinishListener {

    @BindView(R.id.wv_search)
    WebView wvSearch;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_test2)
    Button btnTest2;

    String name = "晾衣架";
    @BindView(R.id.btn_test3)
    Button btnTest3;
    private String cookie;
    private String url = "https://s.taobao.com/search?q=%E5%8D%95%E6%9D%86%E5%BC%8F%E5%87%89%E8%A1%A3%E6%9E%B6%E8%90%BD%E5%9C%B0%E7%AE%80%E6%98%93%E6%99%BE%E8%A1%A3%E6%9D%86%E5%AE%B6%E7%94%A8%E5%8D%A7%E5%AE%A4%E5%86%85%E6%99%92%E8%A1%A3%E6%9E%B6%E6%8A%98%E5%8F%A0%E9%98%B3%E5%8F%B0%E6%8C%82%E8%A1%A3%E6%9C%8D%E6%9E%B6%E5%AD%90&imgfile=&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20181202&ie=utf8";


    @Override
    protected Class<SearchVu> getVuClass() {
        return SearchVu.class;
    }

    @Override
    protected Class<SearchBiz> getBizClass() {
        return SearchBiz.class;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void afterOnCreate() {
        vu.initWebViewSetting(wvSearch, this);
        biz.initWebView(wvSearch, this);
//        wvSearch.loadUrl(UrlUtils.setQueryWord(name));

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wvSearch.loadUrl(JsUtils.addJsMethod("findSameStyle(\"" + name + "\")"));
            }
        });
        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

//                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.Cookies))) {
//
//                            return;
//                        }

                        try {

                            Connection connect = Jsoup.connect("https://login.taobao.com/");
                            // 带参数开始
                            connect.data("TPL_username", "17665495053");
                            connect.data("TPL_password", "Mgc520553");
                            connect.method(Connection.Method.POST);
                            Connection.Response execute = connect.execute();
                            Map<String, String> cookies = execute.cookies();
                            cookie = cookies.toString();
                            cookie = cookie.substring(cookie.indexOf("{")+1, cookie.lastIndexOf("}"));
                            cookie = cookie.replaceAll(",", ";");
                            SharedPreferencesUtils.putValue(Constants.Cookies, cookie);
                        } catch (Exception e) {

                            LogUtils.e(e.toString());

                        }
                    }
                }).start();

//                List<SameSytleUrlBean> list = GreenDaoUtils.getSameStyleUrlListByName(name);
//                list.get(0);
//                for (int i = 0; i < list.size(); i++) {
//                    SameSytleUrlBean sameSytleUrlBean = list.get(i);
//                    LogUtils.e(sameSytleUrlBean.getSameStyleUrl());
//                }
            }
        });
        btnTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        jsoupData();
                    }
                }).start();
            }
        });

    }

    private void jsoupData() {
        try {

            String json = "";
            Document document = Jsoup.connect(url).cookie("Cookie", SharedPreferencesUtils.getValue(Constants.Cookies)).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
            Elements script = document.getElementsByTag("script");
            for (Element ele : script) {
                Attributes attributes = ele.attributes();
                if (ele.data().contains("g_page_config")) {
//                    LogUtils.e("ele.data()~~~~~~~~~~~~~~~" + ele.data());
                    json = ele.data();
                }
            }
            TaoUtils.getNameSplitResult(json);
//            json = json.replace("\\", "");
//            String regex = "classu003dHu003e(.*?)u003c";
//            Pattern pattern = Pattern.compile (regex);
//            Matcher matcher = pattern.matcher(json);
//            while (matcher.find()) {
//                LogUtils.e(matcher.group());
//            }
        } catch (Exception e) {

            LogUtils.e(e.toString());

        }

    }


    @Override
    public void loadFinish(WebView wv) {

    }

    @Override
    public void onBackPressed() {
        if (!vu.onBackPress(wvSearch)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
