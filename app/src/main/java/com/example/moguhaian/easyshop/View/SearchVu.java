package com.example.moguhaian.easyshop.View;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseVu;
import com.example.moguhaian.easyshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchVu extends BaseVu {



    public void initWebView(WebView wvSearch) {
        wvSearch.loadUrl("https://www.baidu.com/");

    }




}
