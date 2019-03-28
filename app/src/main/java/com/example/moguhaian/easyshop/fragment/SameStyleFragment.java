package com.example.moguhaian.easyshop.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.View.SameStyleVu;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.net.InetAddress;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class SameStyleFragment extends BaseFragment<SameStyleVu, SameStyleBiz> implements LoadFinishListener {
    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;

    private String[] items = {"同款链接", "同款分析", "获取cookie", "清除cookie", "登录", "小二", "userAgent", "关闭cookie", "下一个"};
    //    private String shopsUrl = "https://www.taobao.com/?spm=a21bo.2017.201857.1.5c0111d9sMj916";
    private String shopsUrl = "https://s.taobao.com/search?spm=a230r.1.14.107.7396d7b2qjum31&type=samestyle&app=i2i&rec_type=1&uniqpid=-580033393&nid=568968377828&sort=sale-desc";
//    private String sameUrl = "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid=-465089991&nid=569519871896&sort=sale-desc";
    private String sameUrl = "https://s.taobao.com/search?type=samestyle&uniqpid=-465089991&sort=sale-desc";
//        private String url = sameUrl;
    private String url = "https://s.taobao.com/search?&initiative_id=tbindexz_20170306&ie=utf8&spm=a21bo.2017.201856-taobao-item.2&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all&imgfile=&q=%E7%A7%AF%E6%9C%A8%E6%8B%BC%E8%A3%85%E7%8E%A9%E5%85%B7%E7%9B%8A%E6%99%BA&suggest=0_1&_input_charset=utf-8&wq=%E7%A7%AF%E6%9C%A8&suggest_query=%E7%A7%AF%E6%9C%A8&source=suggest";
    private int index;
    private int agentIndedx = 0;
    private int ipsIndedx = 0;
    private String[] userAgent;
    private String[] ips;
    private int clickPosition;
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
    }


    @Override
    public void fragmentRightClick(int position) {
        clickPosition = position;
        switch (position) {
            case 0:
                webView.loadUrl(url);
//                biz.jsoupSameStyleList(webView, url, ips[ipsIndedx],new JsoupParseListener() {
//                    @Override
//                    public void complete() {
//                        ipsIndedx++;
//                        fragmentRightClick(0);
//                    }
//
//                    @Override
//                    public void onFail(String url) {
//                        ipsIndedx++;
//                        fragmentRightClick(0);
//                    }
//                });
                break;
            case 1:
                webView.loadUrl(JsUtils.addJsMethod("getDocument()"));
//                index = 0;
//                getSameStyleData();
                break;
            case 2:
                BaseApplication.setCookieOpen(true);
                webView.loadUrl(url);
                break;
            case 3:
                SharedPreferencesUtils.putValue(Constants.Cookies, "");
                break;
            case 4:
//                webView.loadUrl(JsUtils.addJsMethod("test()"));
                webView.loadUrl(JsUtils.addJsMethod("login()"));
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
            case 1:
                LogUtils.e("getDocument!!!");
                LogUtils.e(vu.getLocalMethod().getJson());
                break;
        }
    }
}
