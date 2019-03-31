package com.example.moguhaian.easyshop.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Base.Ips;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Search.SameStyleBiz;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.View.SameStyleVu;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.listener.LoalMethodListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class SameStyleFragment extends BaseFragment<SameStyleVu, SameStyleBiz> implements LoadFinishListener, LoalMethodListener {
    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;

    private String[] items = {"同款链接", "获取链接", "获取结果", "获取母宝贝", "母宝贝结果", "小二", "userAgent", "关闭cookie", "下一个"};
    //    private String shopsUrl = "https://www.taobao.com/?spm=a21bo.2017.201857.1.5c0111d9sMj916";
    private String shopsUrl = "https://s.taobao.com/search?spm=a230r.1.14.107.7396d7b2qjum31&type=samestyle&app=i2i&rec_type=1&uniqpid=-580033393&nid=568968377828&sort=sale-desc";
//    private String sameUrl = "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid=-465089991&nid=569519871896&sort=sale-desc";
    private String sameUrl = "https://s.taobao.com/search?type=samestyle&uniqpid=-465089991&sort=sale-desc";
//        private String url = sameUrl;
    private String url = "https://s.taobao.com/search?&initiative_id=tbindexz_20170306&ie=utf8&spm=a21bo.2017.201856-taobao-item.2&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all&imgfile=&q=%E7%A7%AF%E6%9C%A8%E6%8B%BC%E8%A3%85%E7%8E%A9%E5%85%B7%E7%9B%8A%E6%99%BA&suggest=0_1&_input_charset=utf-8&wq=%E7%A7%AF%E6%9C%A8&suggest_query=%E7%A7%AF%E6%9C%A8&source=suggest";
    private int index;
    private int agentIndedx = 0;
    private int pageIndedx = 0;
    private int ipsIndedx = 0;
    private String[] userAgent;
    private String[] ips;
    private int clickPosition;
    private boolean isGetSameUrlBegin = false;
    private String[] pageArray;

    private ArrayList<String> minUrlList = new ArrayList<>();
    private ArrayList<String> sameUrlList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private int functionIndex = -1;
    private int sameUrlIndex = -1;
    //    private String url = "https://www.baidu.com/";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        setDataStrs(items);
        vu.initWebViewSetting(webView, getActivity());
        biz.initWebView(webView, getActivity());
        ips = Ips.ips_dianxin.split("\n");
        biz.getWebViewClient().setOnLoadFinishListener(SameStyleFragment.this);
        vu.getLocalMethod().setLocalMethodListener(this);
        pageArray = getResources().getStringArray(R.array.page_index);

    }


    @Override
    public void fragmentRightClick(int position) {
        clickPosition = position;
        switch (position) {
            case 0:
                webView.loadUrl(url);
                break;
            case 1:
                titleList.clear();
                sameUrlList.clear();
                functionIndex = 0;
                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
//                index = 0;
//                getSameStyleData();
                break;
            case 2:
                for (int i = 0; i < titleList.size(); i++) {
                    LogUtils.e("titleList" + i + ":" + titleList.get(i));
                }
                LogUtils.e("=======================================");
                for (int i = 0; i < sameUrlList.size(); i++) {
                    LogUtils.e("sameUrlList" + i + ":" + sameUrlList.get(i));
                }
//                BaseApplication.setCookieOpen(true);
//                webView.loadUrl(url);
                break;
            case 3:
                functionIndex = 1;
                minUrlList.clear();
                if (sameUrlList.size() > 0) {
                    sameUrlIndex = 0;
                    webView.loadUrl(sameUrlList.get(sameUrlIndex));
                }

//                SharedPreferencesUtils.putValue(Constants.Cookies, "");
                break;
            case 4:
//                webView.loadUrl(JsUtils.addJsMethod("test()"));
//                webView.loadUrl(JsUtils.addJsMethod("login()"));
                int[] random = TaoUtils.getRandom(0, titleList.size() - 1, minUrlList.size() * 30);
                String title = "";
                String templeTitle = "";
                for (int i = 0; i < random.length; i++) {
                    if (templeTitle.length() > 80) {
                        title = TextUtils.isEmpty(title) ? templeTitle : title + "\n" + templeTitle;
                        templeTitle = titleList.get(random[i]);
                    } else {
                        if (TaoUtils.levenshtein(titleList.get(random[i]), templeTitle) < 0.2) {
                            templeTitle = TextUtils.isEmpty(templeTitle) ? titleList.get(random[i]) : templeTitle + titleList.get(random[i]);
                        }
                    }
                }
                LogUtils.e("标题结果：\n" + title);

                String urlResult = "";
                minUrlList = TaoUtils.getSingle(minUrlList);
                for (int i = 0; i < minUrlList.size(); i++) {
                    urlResult = TextUtils.isEmpty(urlResult) ? minUrlList.get(i) : urlResult + "\n" + minUrlList.get(i);
                }
                LogUtils.e("母宝贝结果：\n" + urlResult);
                
                break;
            case 5:
                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                break;
            case 6:
                userAgent = getResources().getStringArray(R.array.user_agent);
                if (agentIndedx < userAgent.length) {
                    webView.getSettings().setUserAgentString(Constants.Cookies + userAgent[agentIndedx]);
                }
                LogUtils.e("userAgent:" + webView.getSettings().getUserAgentString());
                webView.loadUrl(url);
                break;
            case 7:
                BaseApplication.setCookieOpen(false);
                break;
            case 8:
                agentIndedx++;
                if (agentIndedx == userAgent.length) {
                    agentIndedx = 0;
                }
                LogUtils.e("agentIndedx:" + agentIndedx);
                break;
//            try {
////                String address = InetAddress.getLocalHost().getHostAddress().toString();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
        }

    }

    private void getSameStyleData() {
        if (index < biz.getSameStyleUrl().size()) {
            biz.jsoupSameStyleJson(webView, biz.getSameStyleUrl().get(index), new JsoupParseListener() {
                @Override
                public void complete() {
                    index++;
                    getSameStyleData();
                }

                @Override
                public void onFail(String url) {
                    BaseApplication.setCookieOpen(true);
//                    index++;
//                    getSameStyleData();
                }
            });
        } else {
            ToastUtils.showToast("完成！！！");
        }
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
        LogUtils.e("loadFinish!!!!!");
        switch (clickPosition) {
            case 0:
                LogUtils.e("getDocument!!!");
                break;
            case 1:
                if (isGetSameUrlBegin) {
                    webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                }
                break;
            case 3:
                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                break;
        }
    }

    @Override
    public void afterGetJson(String json) {
        LogUtils.e("afterGetJson!!!");
        switch (functionIndex) {
            case 0:
                getSameUrl(json);
                break;
            case 1:
                sameUrlIndex++;
                if (!TextUtils.isEmpty(TaoUtils.getInitShop(json))) {
                    minUrlList.add(TaoUtils.getInitShop(json));
                }
                if (sameUrlIndex < sameUrlList.size()) {
                    BaseApplication.getmHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                webView.loadUrl(sameUrlList.get(sameUrlIndex));
                            } catch (Exception e) {
                                LogUtils.e("Exception:" + e.toString());
                            }
                        }
                    }, Constants.DELAY_TIME);
                }
                break;
        }
    }



    private void getSameUrl(String json) {
        isGetSameUrlBegin = true;
        ArrayList<String> nameSplitResult = TaoUtils.getNameSplitResult(json);
        titleList.addAll(nameSplitResult);
        titleList = TaoUtils.getSingle(titleList);
//        for (int i = 0; i < nameSplitResult.size(); i++) {
//            try {
//                if (!GreenDaoUtils.isTemSameTitleExist(nameSplitResult.get(i))) {
//                    if (nameSplitResult.get(i).length() < 500) {
//                        TemTitleBean temTitleBean = new TemTitleBean();
//                        temTitleBean.setTitle(nameSplitResult.get(i));
//                        GreenDaoUtils.insertTemTitleBeanDao(temTitleBean);
//                        LogUtils.e("titleSplit" + i + ":" + nameSplitResult.get(i));
//
//                    }
//                }
//            } catch (Exception e) {
//                if (nameSplitResult.get(i).length() < 500) {
//                    TemTitleBean temTitleBean = new TemTitleBean();
//                    temTitleBean.setTitle(nameSplitResult.get(i));
//                    GreenDaoUtils.insertTemTitleBeanDao(temTitleBean);
//                    LogUtils.e("titleSplit" + i + ":" + nameSplitResult.get(i));
//
//                }
//            }
//
//        }
        ArrayList<String> sameStyleUrl = TaoUtils.getSameStyleUrl(json);
        sameUrlList.addAll(sameStyleUrl);
        sameUrlList = TaoUtils.getSingle(sameUrlList);
//        for (int i = 0; i < sameStyleUrl.size(); i++) {
//            if (!GreenDaoUtils.isTemSameUrlExist(sameStyleUrl.get(i))) {
//                TemSameUrlBean temSameUrlBean = new TemSameUrlBean();
//                temSameUrlBean.setUrl(sameStyleUrl.get(i));
//                GreenDaoUtils.insertTemSameUrlBean(temSameUrlBean);
//                LogUtils.e(sameStyleUrl.get(i));
//            }
//        }

        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pageIndedx < pageArray.length) {
                    webView.loadUrl(url + "&" + pageArray[pageIndedx]);
                    pageIndedx++;
                } else {
                    isGetSameUrlBegin = false;
                }

            }
        }, Constants.DELAY_TIME);
    }
}
