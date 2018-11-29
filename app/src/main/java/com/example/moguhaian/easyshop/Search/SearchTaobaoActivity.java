package com.example.moguhaian.easyshop.Search;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Base.LoadFinishListener;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.UrlUtils;
import com.example.moguhaian.easyshop.View.SearchVu;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTaobaoActivity extends BaseActivity<SearchVu, SearchBiz> implements LoadFinishListener {

    @BindView(R.id.wv_search)
    WebView wvSearch;
    @BindView(R.id.btn_test)
    Button btnTest;


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
        wvSearch.loadUrl(UrlUtils.setQueryWord("手电筒"));
//        wvSearch.loadUrl("https://www.taobao.com/");

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wvSearch.loadUrl(JsUtils.addJsMethod("findSameStyle();"));
            }
        });

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                wvSearch.loadUrl(JsUtils.addJsMethod("jsCangkuGoNextPage();"));
//            }
//        });
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
