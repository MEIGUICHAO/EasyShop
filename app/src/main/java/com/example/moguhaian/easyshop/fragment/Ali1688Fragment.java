package com.example.moguhaian.easyshop.fragment;

import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.CommonUtils;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.View.Ali1688Vu;
import com.example.moguhaian.easyshop.biz.Ali1688Biz;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.listener.LoalMethodListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Ali1688Fragment extends BaseFragment<Ali1688Vu, Ali1688Biz> implements LoadFinishListener, LoalMethodListener {

    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;
    private String[] items = {"1688", "一件代发", "下一页", "一键铺货", "登陆", "图片空间", "获取图片空间图片", "发布现场", "过滤文字", "官方传", "新建文件夹"
            , "文件夹名称", "淘管家", "1688详情", "获取1688详情图片", "获取上传图片", "login", "生成手机详情", "上传图片", "滑动记录开关", "图片输入框点击记录",
            "图片选择点击记录", "图片搜索点击记录", "粘贴点击记录","编辑sku"};
    private int pageIndex = 0;
    //    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch#beginPage=2&offset=0
//    private String url = "https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch";
    private String url = "https://detail.1688.com/offer/539556562483.html?sk=consign";
    //    private String url = "https://item.publish.taobao.com/sell/publish.htm?catId=50013459&itemId=592570571674";
    private String loginUrl = "https://login.1688.com/member/signin.htm?tracelog=account_verify";
    //    private String picSpaceUrl = "https://sucai.wangpu.taobao.com/?spm=a2113j.8836301.0.0.1206139dRygyV4#/manage/pic?_k=40zg4c";
    private String picSpaceUrl = "https://sucai.wangpu.taobao.com/?spm=a2113j.8836301.0.0.694f139dxs5m9o#/manage/pic?_k=umx2ua";
    private String nextUrl;
    private boolean isInit = false;
    private boolean needGetJson = false;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
        }
    };
    private String oldUrl = "";
    private ArrayList<String> skuInfo;
    private int skuEditPos;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        vu.initWebViewSetting(webView, getActivity());
        biz.initWebView(webView, getActivity());
        biz.getWebViewClient().setOnLoadFinishListener(Ali1688Fragment.this);
        vu.getLocalMethod().setLocalMethodListener(this);
        setDataStrs(items);


    }

    @Override
    public void fragmentRightClick(int position) {
        super.fragmentRightClick(position);
        clickPosition = position;
        switch (clickPosition) {
            case 0:
//                biz.getWebViewClient().setNeedListener(true);
                pageIndex = 1;

                webView.loadUrl(url);
                break;
            case 1:
                isInit = true;
                webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
                break;
            case 2:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"fui-next\")"));
                break;
            case 3:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"menu-item-btn  J_ConsignBtn_DistributeToTaobao\")"));
                break;
            case 4:
                if (!oldUrl.contains("login.1688.com")) {
                    webView.loadUrl(loginUrl);
                } else {
                    webView.loadUrl(JsUtils.addJsMethod("login1688()"));
                }
                break;
            case 5://图片空间
                webView.loadUrl(picSpaceUrl);
                break;
            case 6://获取图片空间图片
                vu.getLocalMethod().resetPicspaceList();
                webView.loadUrl(JsUtils.addJsMethod("getSrcByClassName()"));
                break;
            case 7://发布现场
                webView.loadUrl("https://item.publish.taobao.com/sell/publish.htm?catId=124392001&itemId=593358799794");
                break;
            case 8://过滤文字
                webView.loadUrl(JsUtils.addJsMethod("filterWorld(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));
//                webView.loadUrl(JsUtils.addJsMethod("findMoblieImgLength(\"m-editor-content-body\")"));
                break;
            case 9:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"btn confirm official-confirm\")"));
                break;
            case 10:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"itemList-head-btn-item\")"));
                break;
            case 11://文件夹名称
                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium fileCreat-setting-panel-text-input\",\"test\")"));
                break;
            case 12://淘管家
                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium fileCreat-setting-panel-text-input\",\"test\")"));
                break;
            case 13://1688详情
                webView.loadUrl("https://detail.1688.com/offer/564583873717.html?spm=a262fr.8351313.0.0.kOvAk8&sk=consignPrivate");
                break;
            case 14://获取1688详情图片
                webView.loadUrl(JsUtils.addJsMethod("getSrcAttrByTagName(\"table-sku\",\"alt\")"));
                break;
            case 15://获取上传图片
                biz.diffResult(vu.getLocalMethod().getAliDetailDataList(), vu.getLocalMethod().getPicSpaceUrlList());
                break;
            case 16://login
                webView.loadUrl(JsUtils.addJsMethod("login()"));
                break;
            case 17://生成手机详情
                webView.loadUrl(JsUtils.addJsMethod("showKeyboardAfterClick(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));
                break;
            case 18://上传图片
                CommonUtils.copyText("f871d1bb-3b5f-4206-8b64-7d9f8788bc57");
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-btn next-btn-normal next-btn-medium upload-img-btn\")"));
                break;
            case 19://开关活动记录
                webView.setNeedDraw(!webView.isNeedDraw());
                ToastUtils.showToast(webView.isNeedDraw() ? "开启" : "关闭");
                break;
            case 20://图片输入框点击记录
                if (!webView.isClickRecord()) {
                    webView.setClickRecord(true);
                    webView.setSlideRecord(false);
                    ToastUtils.showToast("点击记录开启");
                    return;
                }
                if (webView.isNeedDraw()) {
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X, webView.getACTION_CLICK_DOWN_X() + "");
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y, webView.getACTION_CLICK_DOWN_Y() + "");
                }
                LogUtils.e("CLICK_DOWN_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X)));
                LogUtils.e("CLICK_DOWN_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y)));
                break;
            case 21://图片选择点击记录
                if (!webView.isClickRecord()) {
                    webView.setClickRecord(true);
                    webView.setSlideRecord(false);
                    ToastUtils.showToast("点击记录开启");
                    return;
                }
                if (webView.isNeedDraw()){
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X, webView.getACTION_CLICK_DOWN_X() + "");
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y, webView.getACTION_CLICK_DOWN_Y() + "");
                }
                LogUtils.e("CLICK_DOWN_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X)));
                LogUtils.e("CLICK_DOWN_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y)));
                break;
            case 22://图片搜索
                if (!webView.isClickRecord()) {
                    webView.setClickRecord(true);
                    webView.setSlideRecord(false);
                    ToastUtils.showToast("点击记录开启");
                    return;
                }
                if (webView.isNeedDraw()){
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_X, webView.getACTION_CLICK_DOWN_X() + "");
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_Y, webView.getACTION_CLICK_DOWN_Y() + "");
                }
                LogUtils.e("CLICK_DOWN_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_X)));
                LogUtils.e("CLICK_DOWN_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_Y)));
                break;
            case 23://粘贴点击记录
                if (!webView.isClickRecord()) {
                    webView.setClickRecord(true);
                    webView.setSlideRecord(false);
                    ToastUtils.showToast("点击记录开启");
                    return;
                }
                if (webView.isNeedDraw()){
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_PASTE_CLICK_DOWN_X, webView.getACTION_CLICK_DOWN_X() + "");
                    SharedPreferencesUtils.putValue(Constants.PIC_SPACE_PASTE_CLICK_DOWN_Y, webView.getACTION_CLICK_DOWN_Y() + "");
                }
                LogUtils.e("CLICK_DOWN_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_PASTE_CLICK_DOWN_X)));
                LogUtils.e("CLICK_DOWN_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_PASTE_CLICK_DOWN_Y)));
                break;
            case 24://编辑sku
                skuEditPos = 0;
                skuInfo = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    skuInfo.add("sku" + i);
                }
                webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\")"));
                break;
        }

    }


    public void typeIn(final int KeyCode) {
        try {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync(KeyCode);
        } catch (Exception e) {
            Log.e("Exception：", e.toString());
        }
    }


    @Override
    protected Class getVuClass() {
        return Ali1688Vu.class;
    }

    @Override
    protected Class getBizClass() {
        return Ali1688Biz.class;
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
        LogUtils.e("url:\n" + url);

        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (oldUrl.equals(url)) {
            return;
        }
        oldUrl = url;
        webView.scrollTo(0, webView.getScrollYRange());
        switch (clickPosition) {
            case 0:

                break;
            case 1:
                if (!url.contains(Constants.BAIDU)) {
                    if (needGetJson) {
                        needGetJson = false;
                        isInit = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(4000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                mHandler.sendEmptyMessage(0);
                            }
                        }).start();
                    }
//                    else {
//                        webView.loadUrl(Constants.BAIDU);
//                    }
                } else {
                    needGetJson = true;
                    webView.loadUrl(nextUrl);
                }
                break;
            case 2:
                break;
            case 6:
                break;
        }
    }

    @Override
    public void afterGetJson(final String json) {
        LogUtils.e("pageIndex:" + pageIndex + "\n" + json);
        switch (clickPosition) {
            case 0:
                break;
            case 1:
                LogUtils.e("afterGetJson_urlJson:" + "pageIndex\n" + json);
                if (isInit) {
                    isInit = false;

                    if (pageIndex < vu.getLocalMethod().getPagingNum()) {
                        pageIndex++;
                        nextUrl = url + "#beginPage=" + pageIndex + "&offset=0";
                        webView.loadUrl(Constants.BAIDU);
                    }
                }
//                if (!TextUtils.isEmpty(json)) {
////                    webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"fui-next\")"));
//                }
                break;
            case 24:

                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        Instrumentation inst = new Instrumentation();
                        inst.sendStringSync(json);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                skuEditPos++;
                                if (skuEditPos < skuInfo.size()) {
                                    webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\")"));

                                }
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    public void afterClick() {
        webView.scrollTo(0, webView.getScrollYRange());
        webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
        switch (clickPosition) {
            case 18://上传图片
//
                webView.loadUrl(JsUtils.addJsMethod("getPicFromSpaces(\"7d09bb68-a732-4c3c-bde2-07c9ce1a9358\")"));

                break;

        }
    }
}
