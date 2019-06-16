package com.example.moguhaian.easyshop.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Base.Ips;
import com.example.moguhaian.easyshop.Base.Shops;
import com.example.moguhaian.easyshop.Bean.ResultBean;
import com.example.moguhaian.easyshop.MainActivity;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Search.SameStyleBiz;
import com.example.moguhaian.easyshop.Utils.GreenDaoUtils;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.View.SameStyleVu;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.listener.LoalMethodListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class SameStyleFragment extends BaseFragment<SameStyleVu, SameStyleBiz> implements LoadFinishListener, LoalMethodListener {
    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;

    private String[] items = {"同款链接", "获取链接", "获取结果", "获取母宝贝", "母宝贝结果", "数据库结果", "login", "关闭cookie", "下一个", "滑动", "刷新", "清楚cookie", "开关滑动记录", "获取标题"};
    //    private String shopsUrl = "https://www.taobao.com/?spm=a21bo.2017.201857.1.5c0111d9sMj916";
    private String shopsUrl = "https://s.taobao.com/search?spm=a230r.1.14.107.7396d7b2qjum31&type=samestyle&app=i2i&rec_type=1&uniqpid=-580033393&nid=568968377828&sort=sale-desc";
    //    private String sameUrl = "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid=-465089991&nid=569519871896&sort=sale-desc";
    private String sameUrl = "https://s.taobao.com/search?type=samestyle&uniqpid=-465089991&sort=sale-desc";
    //        private String url = sameUrl;
    private int index;
    private int agentIndedx = 0;
    private int ipsIndedx = 0;
    private String[] userAgent;
    private String[] ips;
    private String shopName = Shops.shopName;

        private String url = Constants.searchUrl1;
    private MainActivity activity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        setDataStrs(items);
        activity = (MainActivity) getActivity();
        vu.initWebViewSetting(webView, getActivity());
        biz.initWebView(webView, getActivity());
        ips = Ips.ips_dianxin.split("\n");
        biz.getWebViewClient().setOnLoadFinishListener(SameStyleFragment.this);
        vu.getLocalMethod().setLocalMethodListener(this);

    }


    @Override
    public void fragmentRightClick(int position) {
        clickPosition = position;
        switch (position) {
            case 0://同款链接
                webView.loadUrl(Constants.searchUrl1 + shopName + Constants.searchUrl2);
                break;
            case 1://获取链接
                biz.getTitleList().clear();
                biz.getSameUrlList().clear();
                biz.setFunctionIndex(0);
                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                break;
            case 2://获取结果
                for (int i = 0; i < biz.getTitleList().size(); i++) {
                    LogUtils.e("titleList" + i + ":" + biz.getTitleList().get(i));
                }
//                LogUtils.e("=======================================");
//                for (int i = 0; i < biz.getSameUrlList().size(); i++) {
//                    LogUtils.e("sameUrlList" + i + ":" + biz.getSameUrlList().get(i));
//                }
//                BaseApplication.setCookieOpen(true);
//                webView.loadUrl(url);
                break;
            case 3://获取母宝贝
                biz.setFunctionIndex(1);
                biz.getMinUrlList().clear();
//                String[] split = Temple.templeUlr.split("\n");
//                for (int i = 0; i < split.length; i++) {
//                    String[] split1 = split[i].split("= \"https");
//                    String replace = split1[1].replace("\"", "");
//                    biz.getSameUrlList().add("https" + replace);
//                }
                if (biz.getSameUrlList().size() > 0) {
                    biz.setSameUrlIndex(0);
                    webView.loadUrl(biz.getSameUrlList().get(biz.getSameUrlIndex()));
                }

//                SharedPreferencesUtils.putValue(Constants.Cookies, "");
                break;
            case 4://母宝贝结果
                int[] random = TaoUtils.getRandom(0, biz.getTitleList().size() - 1, biz.getMinUrlList().size() * 30);
                String title = "";
                String templeTitle = "";
                for (int i = 0; i < random.length; i++) {
                    if (templeTitle.length() > 60) {
                        title = TextUtils.isEmpty(title) ? templeTitle : title + "\n" + templeTitle;
                        templeTitle = biz.getTitleList().get(random[i]);
                    } else {
                        if (TaoUtils.levenshtein(biz.getTitleList().get(random[i]), templeTitle) < 0.2) {
                            templeTitle = TextUtils.isEmpty(templeTitle) ? biz.getTitleList().get(random[i]) : templeTitle + biz.getTitleList().get(random[i]);
                        }
                    }
                }
                LogUtils.e("标题结果：\n" + title);

                String urlResult = "";
                biz.setMinUrlList(TaoUtils.getSingle(biz.getMinUrlList()));
                for (int i = 0; i < biz.getMinUrlList().size(); i++) {
                    urlResult = TextUtils.isEmpty(urlResult) ? biz.getMinUrlList().get(i) : urlResult + "\n" + biz.getMinUrlList().get(i);
                }
                LogUtils.e("母宝贝结果：\n" + urlResult);

                try {
                    if (!GreenDaoUtils.isSearchResultNameExit(Shops.shoplv, shopName)) {
                        insertResultBean(urlResult);
                    }
                } catch (Exception e) {
                    insertResultBean(urlResult);
                }

                break;
            case 5://获取结果
                List<ResultBean> resultList = GreenDaoUtils.getResultList(Shops.shoplv, shopName);
                String reslutStr = "";
                for (int i = 0; i < resultList.size(); i++) {
                    reslutStr = TextUtils.isEmpty(reslutStr) ? resultList.get(i).getRootResult() : reslutStr + "\n" + resultList.get(i).getRootResult();
                }
                LogUtils.e("母宝贝结果：\n" + reslutStr);
//                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                break;
            case 6://login
                webView.loadUrl(JsUtils.addJsMethod("login()"));
                break;
            case 7://关闭cookie
                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
//                webView.setNeedDraw(!webView.isNeedDraw());
//                BaseApplication.setCookieOpen(false);
                break;
            case 8://下一个
                biz.getInitShop("");
//                agentIndedx++;
//                if (agentIndedx == userAgent.length) {
//                    agentIndedx = 0;
//                }
//                LogUtils.e("agentIndedx:" + agentIndedx);
                break;
            case 9://滑动
                if (!webView.isSlideRecord()) {
                    webView.setSlideRecord(true);
                    webView.setClickRecord(false);
                    ToastUtils.showToast("滑动记录开启");
                    return;
                }
                SharedPreferencesUtils.putValue(Constants.SLIDE_DOWN_X, webView.getACTION_DOWN_X() + "");
                SharedPreferencesUtils.putValue(Constants.SLIDE_DOWN_Y, webView.getACTION_DOWN_Y() + "");
                SharedPreferencesUtils.putValue(Constants.SLIDE_UP_X, webView.getACTION_UP_X() + "");
                SharedPreferencesUtils.putValue(Constants.SLIDE_UP_Y, webView.getACTION_UP_Y() + "");

                LogUtils.e("SLIDE_DOWN_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_DOWN_X)));
                LogUtils.e("SLIDE_DOWN_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_DOWN_Y)));
                LogUtils.e("SLIDE_UP_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_UP_X)));
                LogUtils.e("SLIDE_UP_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_UP_Y)));

//                GestureTouchUtils.simulateScroll(webView, Integer.parseInt(SharedPreferencesUtils.getValue(Constants.SLIDE_DOWN_X)), 175, 671, 173, 2000, GestureTouchUtils.HIGH);
                break;
            case 10:
                if (!webView.isClickRecord()) {
                    webView.setClickRecord(true);
                    webView.setSlideRecord(false);
                    ToastUtils.showToast("点击记录开启");
                    return;
                }
                if (webView.isNeedDraw()) {
                    SharedPreferencesUtils.putValue(Constants.CLICK_DOWN_X, webView.getACTION_CLICK_DOWN_X() + "");
                    SharedPreferencesUtils.putValue(Constants.CLICK_DOWN_Y, webView.getACTION_CLICK_DOWN_Y() + "");
                }
                LogUtils.e("CLICK_DOWN_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.CLICK_DOWN_X)));
                LogUtils.e("CLICK_DOWN_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.CLICK_DOWN_Y)));
//                GestureTouchUtils.simulateClick(webView, 545, 170);

                break;
            case 11:
                CookieSyncManager.createInstance(getActivity().getApplicationContext());
                CookieManager cookieManager = CookieManager.getInstance();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.removeSessionCookies(null);
                    cookieManager.removeAllCookie();
                    cookieManager.flush();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        cookieManager.removeSessionCookies(null);
                    }
                    cookieManager.removeAllCookie();
                    CookieSyncManager.getInstance().sync();
                }

                break;
            case 12://开启滑动记录:
                webView.setNeedDraw(!webView.isNeedDraw());
                ToastUtils.showToast(webView.isNeedDraw() ? "开启" : "关闭");
                break;
            case 13://获取标题:
                int[] randomTitle = TaoUtils.getRandom(0, biz.getTitleList().size() - 1, 100);
                LogUtils.e("biz.getTitleList().size():" + biz.getTitleList().size());
                activity.setTitleList(biz.getTitleList());
                String titleOrigin = "";
                for (int i = 0; i < biz.getTitleList().size(); i++) {
                    titleOrigin = TextUtils.isEmpty(titleOrigin) ? biz.getTitleList().get(i) : titleOrigin + "\n" + biz.getTitleList().get(i);
                }
                LogUtils.e("titleOrigin:\n" + titleOrigin);
                String titleOnly = "";
                String templeTitleOnly = "";
                for (int j = 0; j < 20; j++) {
                    for (int i = 0; i < randomTitle.length; i++) {
                        if (templeTitleOnly.length() > 60) {
                            titleOnly = TextUtils.isEmpty(titleOnly) ? templeTitleOnly : titleOnly + "\n" + templeTitleOnly;
                            templeTitleOnly = biz.getTitleList().get(randomTitle[i]);
                        } else {
                            if (TaoUtils.levenshtein(biz.getTitleList().get(randomTitle[i]), templeTitleOnly) < 0.2) {
                                templeTitleOnly = TextUtils.isEmpty(templeTitleOnly) ? biz.getTitleList().get(randomTitle[i]) : templeTitleOnly + biz.getTitleList().get(randomTitle[i]);
                            }
                        }
                    }
                }
                LogUtils.e("标题长度：" + titleOnly.split("\n").length);
                LogUtils.e("标题结果：\n" + titleOnly);
                activity.setTitleResult(titleOnly);
                break;
//            try {
////                String address = InetAddress.getLocalHost().getHostAddress().toString();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
        }

    }

    private void insertResultBean(String urlResult) {
        ResultBean resultBean = new ResultBean();
        resultBean.setShopName(shopName);
        String adjWord = "";
        for (int i = 0; i < biz.getTitleList().size(); i++) {
            adjWord = TextUtils.isEmpty(adjWord) ? biz.getTitleList().get(i) : adjWord + "###" + biz.getTitleList().get(i);
        }
        resultBean.setAdjWord(adjWord);
        resultBean.setRootResult(urlResult);
        String[] shoplvSplit = Shops.shoplv.split("###");
        resultBean.setRootName("");
        if (shoplvSplit.length > 0) {
            resultBean.setLv1(shoplvSplit[0]);
        }
        if (shoplvSplit.length > 1) {
            resultBean.setLv2(shoplvSplit[1]);
        }
        if (shoplvSplit.length > 2) {
            resultBean.setLv3(shoplvSplit[2]);
        }
        if (shoplvSplit.length > 3) {
            resultBean.setLv4(shoplvSplit[3]);
        }
        if (shoplvSplit.length > 4) {
            resultBean.setLv5(shoplvSplit[4]);
        }
        resultBean.setLvResult(Shops.shoplv);

        GreenDaoUtils.insertResultBean(resultBean);

    }


    @Override
    protected Class getVuClass() {
        return SameStyleVu.class;
    }

    @Override
    protected Class getBizClass() {
        return SameStyleBiz.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadFinish(WebView wv, String url) {
//        LogUtils.e("loadFinish!!!!!");
        switch (clickPosition) {
            case 0:
//                LogUtils.e("getDocument!!!");
                break;
            case 1:
                if (biz.isGetSameUrlBegin()) {
                    webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                }
                break;
            case 3:
            case 8:
                if (biz.isCanGetJson()) {
                    biz.setCanGetJson(false);
                    webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                }
                break;
        }
    }

    @Override
    public void afterGetJson(final String json) {
        LogUtils.e("afterGetJson!!!");
        switch (biz.getFunctionIndex()) {
            case 0:
                biz.getSameUrl(json, Constants.searchUrl1 + shopName + Constants.searchUrl2);
                break;
            case 1:
                BaseApplication.getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        biz.getInitShop(json);
                    }
                });


//            BaseApplication.getmHandler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                }
//            }, Constants.DELAY_TIME);
                break;
        }
    }

    @Override
    public void afterClick() {

    }

    @Override
    public void inputFinish() {

    }


}
