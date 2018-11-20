package com.example.moguhaian.easyshop.Search;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseActivity;
import com.example.moguhaian.easyshop.Base.Vu;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.View.SearchVu;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTaobaoActivity extends BaseActivity<SearchVu, SearchBiz>{

    @BindView(R.id.wv_search)
    WebView wvSearch;


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
        vu.initWebViewSetting(wvSearch);
    }


}
