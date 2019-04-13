package com.example.moguhaian.easyshop.fragment;

import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Bean.Top20wBean;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.View.Top20wVu;
import com.example.moguhaian.easyshop.biz.Top20wBiz;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.listener.LoalMethodListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import top.eg100.code.excel.jxlhelper.ExcelManager;

public class Top20wFragment extends BaseFragment<Top20wVu, Top20wBiz> implements LoadFinishListener, LoalMethodListener {

    @BindView(R.id.webView)
    MyWebView webView;
    private String[] items = {"获取top20w"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        setDataStrs(items);
        LogUtils.e("Top20wFragment loadUrl");
        vu.initWebViewSetting(webView,getActivity());
        biz.initWebView(webView, getActivity());
        biz.getWebViewClient().setOnLoadFinishListener(Top20wFragment.this);
        vu.getLocalMethod().setLocalMethodListener(this);
        webView.loadUrl(Constants.Top20wUrl);
    }

    @Override
    protected Class getVuClass() {
        return Top20wVu.class;
    }

    @Override
    protected Class getBizClass() {
        return Top20wBiz.class;
    }

    @Override
    public void fragmentRightClick(int position) {
        super.fragmentRightClick(position);
//        webView.loadUrl(JsUtils.addJsMethod("findElementsByClassName(\"has-child current\")"));
        biz.getSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                onImport();
            }
        });
    }

    @Override
    public void loadFinish(WebView wv, String url) {
        LogUtils.e("loadFinish!!!");
    }

    @Override
    public void afterGetJson(String json) {
        LogUtils.e("afterGetJson!!!");
    }


    private void onImport() {
        try {
            long t1 = System.currentTimeMillis();
//            InputStream excelStream = asset.open("users.xls");
//            excelStream = new FileInputStream("users.xls");
//            InputStreamReader excelStream = new InputStreamReader( getResources().getAssets().open("users.xls") );
            InputStream excelStream = this.getResources().getAssets().open("users.xls");

            ExcelManager excelManager = new ExcelManager();
            final List<Top20wBean> users = excelManager.fromExcel(excelStream, Top20wBean.class);
            long t2 = System.currentTimeMillis();
            final double time = (t2 - t1) / 1000.0D;
            LogUtils.e("解析完成，耗时" + time);
        } catch (Exception e) {
            Log.e("读取异常", e.toString());
            e.printStackTrace();
        }

    }
}
