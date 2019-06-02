package com.example.moguhaian.easyshop.fragment;

import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseApplication;
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
    private String[] items = {"1688", "一件代发", "下一页", "一键铺货", "登陆", "图片空间", "获取图片空间图片", "发布现场", "过滤文字", "官方传",
            "新建文件夹", "文件夹名称", "淘管家", "1688详情", "获取1688详情图片", "获取上传图片", "login", "生成手机详情", "上传图片", "滑动记录开关",
            "图片输入框点击记录", "图片选择点击记录", "图片搜索点击记录", "粘贴点击记录", "编辑sku", "编辑价格", "一键发布", "调试开关", "sku数量"};

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
    private ArrayList<String> skuEditPricesList;
    private ArrayList<String> skuPicInfo;
    private int skuEditPos;
    private int skuEditPicPos = 0;
    private boolean aliOneKeyPublish = false;
    private int skuEditPricesPos;
    private int skuLimit = 2;
    private boolean deBug = false;
    private ArrayList<Object> skuEditCountList;
    private int skuEditCountPos;


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
//                pageIndex = 1;
//
//                webView.loadUrl(url);
                webView.loadUrl("https://item.publish.taobao.com/sell/publish.htm?catId=124392001&itemId=593358799794");
//                webView.loadMyUrl("https://item.publish.taobao.com/sell/publish.htm?catId=124392001&itemId=593358799794",Constants.CSS_FILE_NAME);

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

                ToastUtils.showToast("对比开始");
                biz.diffResult(vu.getLocalMethod().getAliDetailDataList(), vu.getLocalMethod().getPicSpaceUrlList(), new Ali1688Biz.DiffProgressListener() {
                    @Override
                    public void diffFinish() {
                        fragmentRightClick(7);
                    }
                });
                break;
            case 16://login
                webView.loadUrl(JsUtils.addJsMethod("login()"));
                break;
            case 17://生成手机详情
                webView.loadUrl(JsUtils.addJsMethod("comfirMobileDetail()"));
                break;
            case 18://上传图片

//                CommonUtils.copyText("f871d1bb-3b5f-4206-8b64-7d9f8788bc57");
//                skuPicInfo = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    skuPicInfo.add("f871d1bb-3b5f-4206-8b64-7d9f8788bc57");
//                }
//                skuEditPicPos = 0;
//                webView.loadUrl(JsUtils.addJsMethod("editSKuPic(\"" + skuEditPicPos + "\")"));

                //=========================================================
                webView.scrollTo(0, webView.getScrollYRange());
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseApplication.getmHandler().removeCallbacks(this);
                        skuEditPicPos = 0;
                        ArrayList<String> compareResultPicList = biz.getCompareResultList();

                        LogUtils.e("上传数量:" + compareResultPicList.size());
                        if (compareResultPicList.size() < 1) {
                            ToastUtils.showToast("请选择上传图片");
                            return;
                        }

                        CommonUtils.copyText(compareResultPicList.get(skuEditPicPos));
                        skuPicInfo = new ArrayList<>();
                        for (int i = 0; i < compareResultPicList.size(); i++) {
                            skuPicInfo.add(compareResultPicList.get(i).split("\n")[1]);
                        }
                        webView.loadUrl(JsUtils.addJsMethod("editSKuPic(\"" + skuEditPicPos + "\")"));
                    }
                }, 1000);

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
                ArrayList<String> compareResultList = biz.getCompareResultList();
                LogUtils.e("上传数量:" + compareResultList.size());
                for (int i = 0; i < compareResultList.size(); i++) {
                    skuInfo.add(compareResultList.get(i).split("\n")[0]);
                }
                if (skuInfo.size() > 0) {
                    webView.loadUrl(JsUtils.addJsMethod("clearSku(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\")"));
                }

//                skuEditPos = 0;
//                skuInfo = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    skuInfo.add("哥莫拉" + i);
//                }
//                webView.loadUrl(JsUtils.addJsMethod("clearSku(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\")"));
                break;
            case 25://编辑价格:
                //TODO
                skuEditPricesPos = 0;
                skuEditPricesList = new ArrayList<>();
//
                ArrayList<String> compareResultPriceList = biz.getCompareResultList();
                LogUtils.e("上传数量:" + compareResultPriceList.size());
                for (int i = 0; i < compareResultPriceList.size(); i++) {
                    skuEditPricesList.add(compareResultPriceList.get(i).split("\n")[2]);
                }
                double prices = Double.parseDouble(skuEditPricesList.get(skuEditPricesPos)) * 2 + 10;
                LogUtils.e("origin_prices:" + skuEditPricesList.get(skuEditPricesPos));
                webView.loadUrl(JsUtils.addJsMethod("setSkuPrice(\"" + skuEditPricesPos + "\",\"" + prices + "\")"));
//                webView.loadUrl(JsUtils.addJsMethod("setSkuPrice(\"" + 0 + "\",\"" + 88.88 + "\")"));
                 break;
            case 26://一键发布
//                13-->14-->5-->6-->15-->7-->24-->18-->25
                aliOneKeyPublish = true;
                fragmentRightClick(13);
                 break;
            case 27://调试开关
                deBug = !deBug;
                ToastUtils.showToast(deBug ? "调试开启" : "调试关闭");
                 break;
            case 28://sku数量:

                skuEditCountPos = 0;
                skuEditCountList = new ArrayList<>();
//
                ArrayList<String> compareResultCountList = biz.getCompareResultList();
                LogUtils.e("上传数量:" + compareResultCountList.size());
                for (int i = 0; i < compareResultCountList.size(); i++) {
                    skuEditCountList.add(compareResultCountList.get(i).split("\n")[3]);
                }
                LogUtils.e("origin_prices:" + skuEditCountList.get(skuEditCountPos));
                webView.loadUrl(JsUtils.addJsMethod("setSkuCount(\"" + skuEditCountPos + "\",\"" + skuEditCountList.get(skuEditCountPos) + "\")"));

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
        if (deBug) {
            return;
        }
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
            case 5://加载图片空间
                fragmentRightClick(6);
                break;
            case 7://发布现场
                fragmentRightClick(24);
                break;
            case 13:
                if (aliOneKeyPublish) {
                    fragmentRightClick(14);

                }
                break;
        }
    }

    @Override
    public void afterGetJson(final String json) {
        if (deBug) {
            return;
        }
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
            case 6:
                fragmentRightClick(15);
                break;
            case 8:
                webView.loadUrl(JsUtils.addJsMethod("showKeyboardAfterClick(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));
                break;
            case 14:
                if (aliOneKeyPublish) {
                    fragmentRightClick(5);
                }
                break;
            case 17:
//                生成手机详情
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.removeCallbacks(this);
                        webView.loadUrl(JsUtils.addJsMethod("comfirMobileDetail()"));
                    }
                }, 1000);
                break;
            case 18:

                skuEditPicPos++;
                if (skuEditPicPos < (skuPicInfo.size() < skuLimit ? skuPicInfo.size() : skuLimit)) {
                    webView.loadUrl(JsUtils.addJsMethod("editSKuPic(\"" + skuEditPicPos + "\")"));
                } else {
                    ToastUtils.showToast("图片sku结束");
                    fragmentRightClick(25);

                }

                break;
            case 24:

                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X,KeyEvent.KEYCODE_DEL};
                        for (int i = 0; i < keyCodeArray.length; i++) {
                            try {
                                typeIn(keyCodeArray[i]);
                                Thread.sleep( 500 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                skuEditPos++;
                                if (skuEditPos < (skuInfo.size() < skuLimit ? skuInfo.size() : skuLimit)) {
                                    webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\")"));
                                } else {
                                    ToastUtils.showToast("文字sku结束");
                                    webView.scrollTo(0, webView.getScrollYRange());
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mHandler.removeCallbacks(this);
                                            fragmentRightClick(18);

                                        }
                                    }, 1000);
                                }
                            }
                        });
                    }
                });
                break;
            case 25://
                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Instrumentation inst = new Instrumentation();
                        inst.sendStringSync(json);
                        LogUtils.e(json);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                skuEditPricesPos++;
                                if (skuEditPricesPos < (skuEditPricesList.size() < skuLimit ? skuEditPricesList.size() : skuLimit)) {
                                    double prices = Double.parseDouble(skuEditPricesList.get(skuEditPricesPos)) * 2 + 10;
                                    LogUtils.e("origin_prices:" + skuEditPricesList.get(skuEditPricesPos));
                                    webView.loadUrl(JsUtils.addJsMethod("setSkuPrice(\"" + skuEditPricesPos + "\",\"" + prices + "\")"));
                                } else {
                                    ToastUtils.showToast("价格sku结束");
                                    fragmentRightClick(28);
                                }
                            }
                        });
                    }
                });

                break;
            case 28://
                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Instrumentation inst = new Instrumentation();
                        inst.sendStringSync(json);
                        LogUtils.e(json);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                skuEditCountPos++;
                                if (skuEditCountPos < (skuEditCountList.size() < skuLimit ? skuEditCountList.size() : skuLimit)) {
                                    LogUtils.e("origin_prices:" + skuEditCountList.get(skuEditCountPos));
                                    webView.loadUrl(JsUtils.addJsMethod("setSkuCount(\"" + skuEditCountPos + "\",\"" + skuEditCountList.get(skuEditCountPos) + "\")"));
                                } else {
                                    ToastUtils.showToast("sku库存结束");
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
        if (deBug) {
            return;
        }
        webView.scrollTo(0, webView.getScrollYRange());
        webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
        switch (clickPosition) {
            case 18://上传图片
                CommonUtils.copyText(skuPicInfo.get(skuEditPicPos));
                webView.loadUrl(JsUtils.addJsMethod("getPicFromSpaces()"));

                break;

        }
    }
}
