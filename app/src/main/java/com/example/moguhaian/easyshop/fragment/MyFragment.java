package com.example.moguhaian.easyshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.R;

public class MyFragment extends Fragment {

    private WebView webview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        webview = (WebView) view.findViewById(R.id.webView);
        webview.loadUrl("http://www.cnblogs.com/purediy/p/3276545.html");
        return view;
    }



}
