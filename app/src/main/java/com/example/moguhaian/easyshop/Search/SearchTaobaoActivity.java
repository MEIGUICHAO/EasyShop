package com.example.moguhaian.easyshop.Search;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Base.JsoupParseListener;
import com.example.moguhaian.easyshop.Base.LoadFinishListener;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.GreenDaoUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.View.SearchVu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTaobaoActivity extends BaseActivity<SearchVu, SearchBiz> implements LoadFinishListener {

    @BindView(R.id.wv_search)
    WebView wvSearch;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_test2)
    Button btnTest2;

    String name = "恐龙玩具";
    @BindView(R.id.btn_test3)
    Button btnTest3;
    @BindView(R.id.btn_test4)
    Button btnTest4;
    @BindView(R.id.btn_test5)
    Button btnTest5;
    private String cookie;
    private String sameUrl = "https://s.taobao.com/search?spm=a230r.1.14.479.67f12140b7MMNr&type=samestyle&app=i2i&rec_type=1&uniqpid=-233600459";
    private String url = "https://s.taobao.com/search?q=%E5%8D%95%E6%9D%86%E5%BC%8F%E5%87%89%E8%A1%A3%E6%9E%B6%E8%90%BD%E5%9C%B0%E7%AE%80%E6%98%93%E6%99%BE%E8%A1%A3%E6%9D%86%E5%AE%B6%E7%94%A8%E5%8D%A7%E5%AE%A4%E5%86%85%E6%99%92%E8%A1%A3%E6%9E%B6%E6%8A%98%E5%8F%A0%E9%98%B3%E5%8F%B0%E6%8C%82%E8%A1%A3%E6%9C%8D%E6%9E%B6%E5%AD%90&imgfile=&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20181202&ie=utf8";
    private int index = 0;
    private ArrayList<String> sameStyleUrlList;


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

        System.getProperties().setProperty("http.proxyHost", "61.142.72.154");
        System.getProperties().setProperty("http.proxyPort", "30074");
        vu.initWebViewSetting(wvSearch, this);
        biz.initWebView(wvSearch, this);
//        wvSearch.loadUrl(UrlUtils.setQueryWord(name));
        wvSearch.loadUrl("http://www.ip138.com/");

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                biz.findSameStyleUrl(wvSearch, name);
            }
        });
        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biz.TBLogin();
            }
        });
        btnTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biz.getTitleSplitArray(url);
            }
        });
        btnTest4.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                sameStyleUrlList = GreenDaoUtils.getSameStyleUrlList();

            }
        });
        btnTest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foreachTitle();
            }
        });

    }

    private void foreachTitle() {
        LogUtils.e(sameStyleUrlList.get(index));
        biz.getSameStyleBean(sameStyleUrlList.get(index), new JsoupParseListener() {
            @Override
            public void complete() {
                index++;
                if (index < sameStyleUrlList.size()) {
                    foreachTitle();
                }
            }
        });
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
