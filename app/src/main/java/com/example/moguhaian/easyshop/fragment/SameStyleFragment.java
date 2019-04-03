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
    private int index;
    private int agentIndedx = 0;
    private int ipsIndedx = 0;
    private String[] userAgent;
    private String[] ips;
    private int clickPosition;
    private String shopName = "学步手推车";

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

    }


    @Override
    public void fragmentRightClick(int position) {
        clickPosition = position;
        switch (position) {
            case 0:
                webView.loadUrl(Constants.searchUrl1 + shopName + Constants.searchUrl2);
                break;
            case 1:
                biz.getTitleList().clear();
                biz.getSameUrlList().clear();
                biz.setFunctionIndex(0);
                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                break;
            case 2:
                for (int i = 0; i < biz.getTitleList().size(); i++) {
                    LogUtils.e("titleList" + i + ":" + biz.getTitleList().get(i));
                }
                LogUtils.e("=======================================");
                for (int i = 0; i < biz.getSameUrlList().size(); i++) {
                    LogUtils.e("sameUrlList" + i + ":" + biz.getSameUrlList().get(i));
                }
//                BaseApplication.setCookieOpen(true);
//                webView.loadUrl(url);
                break;
            case 3:
                biz.setFunctionIndex(1);
                biz.getMinUrlList().clear();
                if (biz.getSameUrlList().size() > 0) {
                    biz.setSameUrlIndex(0);
                    webView.loadUrl(biz.getSameUrlList().get(biz.getSameUrlIndex()));
                }

//                SharedPreferencesUtils.putValue(Constants.Cookies, "");
                break;
            case 4:
//                webView.loadUrl(JsUtils.addJsMethod("test()"));
//                webView.loadUrl(JsUtils.addJsMethod("login()"));
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
                webView.loadUrl(Constants.searchUrl1 + shopName + Constants.searchUrl2);
                break;
            case 7:
                BaseApplication.setCookieOpen(false);
                break;
            case 8:
                biz.getInitShop("");
//                agentIndedx++;
//                if (agentIndedx == userAgent.length) {
//                    agentIndedx = 0;
//                }
//                LogUtils.e("agentIndedx:" + agentIndedx);
                break;
//            try {
////                String address = InetAddress.getLocalHost().getHostAddress().toString();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
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
                if (biz.isGetSameUrlBegin()) {
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
        switch (biz.getFunctionIndex()) {
            case 0:
                biz.getSameUrl(json, Constants.searchUrl1 + shopName + Constants.searchUrl2);
                break;
            case 1:
                biz.getInitShop(json);
                break;
        }
    }



}
