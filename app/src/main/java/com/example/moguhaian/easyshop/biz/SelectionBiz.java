package com.example.moguhaian.easyshop.biz;

import android.text.TextUtils;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Bean.CaijiBean;
import com.example.moguhaian.easyshop.CaijiBeanDao;
import com.example.moguhaian.easyshop.Utils.CommonUtils;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectionBiz extends BaseBiz {

    private CaijiBeanDao caijiBeanDao;
    private ArrayList<String> caijiBeansRecords;
    private boolean randomAgain;

    public void dropDown(WebView webView) {
//        widget-multi-dropdown
        webView.loadUrl(JsUtils.addJsMethod("foreachTable(" + "\"ui-table ui-table-simple\"" + ",0" + ")"));
//        + "ui-table ui-table-simple" + "\"0\"" + "\
//        singleThreadExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                JsoupUtils.parseForm("https://www.taosj.com/tool/tool.htm#/optimize_market/keyword/", "ui-table ui-table-simple");
//            }
//        });
//        webView.loadUrl("findElementsByClassName(\"" + "multi-dropdown J_MutliSelect" + "\")");
//        singleThreadExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                jsoupData(Constants.SelectionUrl);
//            }
//        });
    }


    public String jsoupData(String url) {
        String json = "";
        try {
            Document document = Jsoup.connect(url).cookie("Cookie", SharedPreferencesUtils.getValue(Constants.Cookies)).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
//            Document document = Jsoup.connect(url).cookie("Cookie", "t=09739d8b9ea2481146e732e9f3c29613; cookie2=18ebce4230b2907136b111c94bb425f0; v=0; _tb_token_=e7a48e5e3fefe").userAgent(Constants.UserAgentString).ignoreContentType(true).get();
            Elements script = document.getElementsByTag("script");
            for (Element ele : script) {
                if (ele.data().contains("g_page_config")) {
                    json = ele.data();
                }
            }

            if (TextUtils.isEmpty(json)) {
                LogUtils.e(document.toString());
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());

        }
        return json;
    }

    public void jsoupShop(final String url, final int position, final JsoupParseListener listener) {
        LogUtils.e("url:" + url);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
                    Elements title = document.getElementsByClass("tb-main-title");
                    LogUtils.e("采集~~标题:" + title.html());
                    if (!TextUtils.isEmpty(title.html())) {
                        Element j_imgBooth = document.getElementById("J_ImgBooth");
                        LogUtils.e("采集~~图片地址:" + "https:" + j_imgBooth.attr("src"));

                        CaijiBean caijiBean = new CaijiBean();
                        caijiBean.setLv1Name("玩具/童车/益智/积木/模型");
                        caijiBean.setLv2Name("串珠/拼图/配对/拆装/敲打玩具");
                        caijiBean.setLv3Name("建构/拼插积木");
                        caijiBean.setShopUrl(url);
                        caijiBean.setMainPath(j_imgBooth.attr("src"));
                        caijiBean.setPosition(position);
                        caijiBean.setOriginTitle(title.html());
                        insertCaijiBean(caijiBean);
                    }

                    listener.complete();
                } catch (IOException e) {
                    listener.onFail(url);
                    e.printStackTrace();
                }

            }
        });
    }

    public void insertCaijiBean(CaijiBean bean) {
        initCaijiDao();
        List<CaijiBean> list = caijiBeanDao.queryBuilder().where(CaijiBeanDao.Properties.ShopUrl.eq(bean.getShopUrl())).build().list();
        if (list.size() < 1) {
            caijiBeanDao.insert(bean);
            if (null == caijiBeansRecords) {
                caijiBeansRecords = new ArrayList<>();
            }
            caijiBeansRecords.add(bean.getOriginTitle());
        }

    }



    public void upateCaiji() {
        initCaijiDao();
        caijiBeanDao.queryBuilder().where(CaijiBeanDao.Properties.Position.eq(CaijiBeanDao.Properties.ExchagePosition)).build().list();

    }


    public void initCaijiDao() {
        if (null == caijiBeanDao) {
            caijiBeanDao = BaseApplication.getInstances().getDaoSession().getCaijiBeanDao();
        }
    }

    public void updateCaijiExchageTitle() {
        initCaijiDao();
        List<CaijiBean> caijiList = caijiBeanDao.queryBuilder().build().list();
        if (null == caijiBeansRecords) {
            caijiBeansRecords = new ArrayList<>();
            for (int i = 0; i < caijiList.size(); i++) {
                caijiBeansRecords.add(caijiList.get(i).getOriginTitle());
            }
        }


        randomAgain = false;
        final int[] random = CommonUtils.getRandom(caijiBeansRecords.size());
        for (int i = 0; i < random.length; i++) {
            if (i == random[i]) {
                randomAgain = true;
            }
        }
        LogUtils.e("random.length:" + random.length);
        if (randomAgain) {
            updateCaijiExchageTitle();
            return;
        }

        for (int i = 0; i < random.length; i++) {
            CaijiBean caijiBean = caijiList.get(i);
            caijiBean.setExchagePosition(random[i]);
            caijiBean.setExchangeTitle(caijiBeansRecords.get(random[i]));
            caijiBeanDao.updateInTx(caijiBean);
        }
        List<CaijiBean> list = caijiBeanDao.queryBuilder().list();
        String url = "";
        String title = "";

        for (int i = 0; i < list.size(); i++) {
            url = TextUtils.isEmpty(url) ? list.get(i).getShopUrl() : url + "\n"  +list.get(i).getShopUrl();
            title = TextUtils.isEmpty(title) ? list.get(i).getExchangeTitle() : title + "\n" + list.get(i).getExchangeTitle();
        }
        LogUtils.e("采集结果url：" + url);
        LogUtils.e("采集结果title：" + title);

    }



}
