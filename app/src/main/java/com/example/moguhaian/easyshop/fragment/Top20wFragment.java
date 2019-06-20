package com.example.moguhaian.easyshop.fragment;

import android.util.Log;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Bean.Top20wBean;
import com.example.moguhaian.easyshop.Bean.Top20wRecordBean;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.GreenDaoUtils;
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

    MyWebView webView;
    private String[] items = {"获取top20w","录入","结果"};
    private List<Top20wBean> users;
    private int foreachPosition;
    private Top20wBean top20wBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        webView = view.findViewById(R.id.webView);
        setDataStrs(items);
        LogUtils.e("Top20wFragment loadUrl");
        vu.initWebViewSetting(webView,getActivity());
        biz.initWebView(webView, getActivity());
        biz.getWebViewClient().setOnLoadFinishListener(Top20wFragment.this);
        vu.getLocalMethod().setLocalMethodListener(this);
//        webView.loadUrl(Constants.Top20wUrl);
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

        clickPosition = position;

        switch (clickPosition) {
            case 0:
                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        onImport();
                    }
                });
                break;
            case 1:
                foreachPosition = 0;

                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        insertDb();
                    }
                });
                insertDb();

                break;
            case 2:

                List<Top20wRecordBean> top20wRecordBeanList = GreenDaoUtils.getTop20wRecordBeanList();
                LogUtils.e("top20w:" + top20wRecordBeanList.size());
                break;
        }
//        webView.loadUrl(JsUtils.addJsMethod("findElementsByClassName(\"has-child current\")"));
    }

    private void insertDb() {
        top20wBean = users.get(foreachPosition);
        Top20wRecordBean top20wRecordBean = new Top20wRecordBean();
        top20wRecordBean.setKeyWord(top20wBean.getKeyWord());
        top20wRecordBean.setLv1(top20wBean.getLv1());
        top20wRecordBean.setLv2(top20wBean.getLv2());
        top20wRecordBean.setLv3(top20wBean.getLv3());
        GreenDaoUtils.insertTop20wRecordBean(top20wRecordBean);
//        if (foreachPosition < users.size()) {
        if (foreachPosition < users.size()) {
            foreachPosition++;
            Log.e("插入进度", "插入进度:" + foreachPosition + "/" + users.size());
            insertDb();
        }

    }

    @Override
    public void loadFinish(WebView wv, String url) {
        LogUtils.e("loadFinish!!!");
    }

    @Override
    public void loadFinish(com.tencent.smtt.sdk.WebView wv, String url) {

    }

    @Override
    public void afterGetJson(String json) {
        LogUtils.e("afterGetJson!!!");
    }

    @Override
    public void afterClick() {

    }

    @Override
    public void inputFinish() {

    }

    @Override
    public void errorOccur() {
        switch (clickPosition) {

        }
    }


    private void onImport() {
        try {
            long t1 = System.currentTimeMillis();
//            InputStream excelStream = asset.open("users.xls");
//            excelStream = new FileInputStream("users.xls");
//            InputStreamReader excelStream = new InputStreamReader( getResources().getAssets().open("users.xls") );
            InputStream excelStream = this.getResources().getAssets().open("users.xls");

            ExcelManager excelManager = new ExcelManager();
            users = excelManager.fromExcel(excelStream, Top20wBean.class);
            long t2 = System.currentTimeMillis();
            final double time = (t2 - t1) / 1000.0D;
            LogUtils.e("解析完成，耗时" + time);
        } catch (Exception e) {
            Log.e("读取异常", e.toString());
            e.printStackTrace();
        }

    }
}
