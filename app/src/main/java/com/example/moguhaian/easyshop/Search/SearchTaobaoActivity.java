package com.example.moguhaian.easyshop.Search;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Base.LoadFinishListener;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.UrlUtils;
import com.example.moguhaian.easyshop.View.SearchVu;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTaobaoActivity extends BaseActivity<SearchVu, SearchBiz> implements LoadFinishListener {

    @BindView(R.id.wv_search)
    WebView wvSearch;
    @BindView(R.id.btn)
    Button btn;


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
//        biz.initWebView(wvSearch, this);
//        biz.loadTBSearchUrlByName("连衣裙");
        wvSearch.loadUrl(UrlUtils.setQueryWord("连衣裙"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e(JsUtils.addJsMethod("jsCangkuGoNextPage();"));
                wvSearch.loadUrl("javascript: var newscript = document.createElement(\"script\");newscript.text = window.onload=doAutoTest();function doAutoTest() { jsCangkuGoNextPage();}document.body.appendChild(newscript);");

//                wvSearch.loadUrl("javascript: var newscript = document.createElement(\"script\");newscript.text = window.onload=doAutoTest();function doAutoTest() { jsCangkuGoNextPage();}document.body.appendChild(newscript);");

            }
        });
    }


    @Override
    public void loadFinish(WebView wv) {
//        wv.loadUrl(JsUtils.addJsMethod("testJs();"));
        LogUtils.e(JsUtils.addJsMethod("jsCangkuGoNextPage();"));
        LogUtils.e("6666666");
//        LogUtils.e("wv:" + wv.getUrl());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
