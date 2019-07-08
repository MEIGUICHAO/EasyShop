package com.example.moguhaian.easyshop.fragment;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Base.Shops;
import com.example.moguhaian.easyshop.MainActivity;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.CommonUtils;
import com.example.moguhaian.easyshop.Utils.DateUtil;
import com.example.moguhaian.easyshop.Utils.GestureTouchUtils;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.ResUtil;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
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

public class ShuaiShouFragment extends BaseFragment<Ali1688Vu, Ali1688Biz> implements LoadFinishListener, LoalMethodListener, View.OnLongClickListener {


    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;
    private int[] items = {R.string.one_key_publish, R.string.go1688, R.string.one_piece_send, R.string.nextpage, R.string.one_click_shop, R.string.login, R.string.filter_word, R.string.detail_1688, R.string.login_name, R.string.slide_record_switch
            , R.string.ymd_click_record, R.string.hmm_click_record, R.string.timing_publish_click, R.string.comfir_publish_click_record, R.string.comfir_publish_click, R.string.set_title, R.string.cache_available, R.string.autoDebug_switch, R.string.save_draft,
            R.string.get_publish_result, R.string.refresh_page, R.string.go_draft_page, R.string.reload_draft_click_record, R.string.shuaiShou};

    private int pageIndex = 0;
    private String urlOrigin;
    private String urlOrigin1 = "https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=";
    private String urlOrigin2 = "&netType=1%2C11&n=y&from=taoSellerSearch";
    private String loginUrl = "https://login.1688.com/member/signin.htm?tracelog=account_verify";
    private String nextUrl;
    private String aliResult = "";
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

    private boolean aliOneKeyPublish = false;
    //    private int skuLimit = 19;
    private int aliCurrentPage = -1;
    private boolean notAuto = false;

    private boolean debug = false;
    private String[] aliResutlArray;
    private boolean cacheAvailable = true;
    private MainActivity activity;
    private Long singleSpaceTime;
    private String fullDateFromat;
    private String[] titlResultArray;
    private boolean autoDebug = false;
    private boolean isErrorOccur = false;

    private boolean YMD_INPUT_FINISH = false;
    private boolean HMM_INPUT_FINISH = false;
    private int oldClickPosition;
    private int indexAddTime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {
        urlOrigin = urlOrigin1 + Shops.aliName + urlOrigin2;

        activity = (MainActivity) getActivity();
        vu.initWebViewSetting(webView, getActivity());
        biz.initWebView(webView, getActivity());
        biz.getWebViewClient().setOnLoadFinishListener(ShuaiShouFragment.this);
        vu.getLocalMethod().setLocalMethodListener(this);
        webView.setOnLongClickListener(this);
        setDataStrs(items);

    }



    public void autoFragmentClick(final int position) {
        BaseApplication.getmHandler().post(new Runnable() {


            @Override
            public void run() {

                vu.blockNetIamge(webView, false);
                if ((!notAuto && aliOneKeyPublish) || autoDebug) {
                    LogUtils.e("autoFragmentClick:" + ResUtil.getS(position));
                    if (position == R.string.refresh_page) {
                        oldClickPosition = clickPosition;
                    }
                    clickPosition = position;
                    rightClickSwitch(position);
                }
            }
        });
    }

    @Override
    public void fragmentRightClick(int position) {
        super.fragmentRightClick(position);

        if (position == R.string.refresh_page) {
            oldClickPosition = clickPosition;
        }
        clickPosition = items[position];
        LogUtils.e("fragmentRightClick:" + ResUtil.getS(clickPosition));
        rightClickSwitch(items[position]);

    }

    private void rightClickSwitch(int item) {
        switch (item) {
            case R.string.shuaiShou:
                webView.loadUrl(JsUtils.addJsMethod("shuaishou()"));
                break;
            case R.string.go1688:
                webView.loadUrl(TextUtils.isEmpty(nextUrl) ? urlOrigin : nextUrl);

                break;
            case R.string.one_piece_send:
                vu.blockNetIamge(webView, true);
                isInit = true;
                webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
                break;
            case R.string.nextpage:

                if (cacheAvailable) {
                    aliCurrentPage = SharedPreferencesUtils.getIntValue(Constants.ALI_CURRENT_PAGE, 0);
                }
                if (aliCurrentPage == -1) {
                    aliCurrentPage = 0;
                }
                aliCurrentPage++;
                SharedPreferencesUtils.putIntValue(Constants.ALI_CURRENT_PAGE, aliCurrentPage);
                autoFragmentClick(R.string.detail_1688);
                break;
            case R.string.one_click_shop:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"menu-item-btn  J_ConsignBtn_DistributeToTaobao\")"));
                break;
            case R.string.login:
                if (!oldUrl.contains("login.1688.com")) {
                    webView.loadUrl(loginUrl);
                } else {
                    webView.loadUrl(JsUtils.addJsMethod("login1688()"));
                }
                break;
            case R.string.filter_word://过滤文字
                webView.scrollTo(0, webView.getScrollY());
                webView.loadUrl(JsUtils.addJsMethod("filterWorld(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));

//                webView.loadUrl(JsUtils.addJsMethod("findMoblieImgLength(\"m-editor-content-body\")"));
                break;
            case R.string.office_publish:
                webView.loadUrl(JsUtils.addJsMethod("shuaishou()"));
                break;
            case R.string.detail_1688://1688详情
                webView.loadUrl("https://detail.1688.com/offer/37955297658.html?spm=b26110380.sw1688.mof001.47.79727d17ARTcG3&sk=consign");
//                webView.loadUrl("https://detail.1688.com/offer/578607374928.html?sk=consign");
//                webView.loadUrl("https://detail.1688.com/offer/580549523893.html?sk=consign");

//                vu.blockNetIamge(webView, true);
//                if (!cacheAvailable && !TextUtils.isEmpty(aliResult)) {
//                    SharedPreferencesUtils.putValue(Constants.ALI_SHOP_RESULT, aliResult);
//                }
//                if (cacheAvailable) {
//                    aliResult = SharedPreferencesUtils.getValue(Constants.ALI_SHOP_RESULT);
//                    aliCurrentPage = SharedPreferencesUtils.getIntValue(Constants.ALI_CURRENT_PAGE, 0);
//                    LogUtils.e("detail_1688 aliResult:" + aliResult);
//                    LogUtils.e("detail_1688 aliCurrentPage:" + aliCurrentPage);
//                }
//
//                String[] aliTempResutlArray = aliResult.split("\n");
//                LogUtils.e("aliTempResutlArray:" + aliTempResutlArray.length);
//                aliResutlArray = TaoUtils.getSingle(aliTempResutlArray);
//                LogUtils.e("aliResutlArray:" + aliResutlArray.length);
//                if (aliCurrentPage == -1) {
//                    aliCurrentPage = 0;
//                }
//                LogUtils.e("aliCurrentPage:" + aliCurrentPage);
//                if (aliCurrentPage >= aliResutlArray.length) {
//                    ToastUtils.showToast("detail 最大值");
//                    aliCurrentPage = aliResutlArray.length / 2;
//                    return;
//                }
//                LogUtils.e("1688url:" + aliResutlArray[aliCurrentPage]);
//                if (!TextUtils.isEmpty(aliResutlArray[aliCurrentPage])) {
//                    webView.loadUrl(aliResutlArray[aliCurrentPage]);
//                } else {
//                    autoFragmentClick(R.string.nextpage);
//                }
//                webView.getSettings().setJavaScriptEnabled(true);
                break;
            case R.string.login_name://login
                webView.loadUrl(JsUtils.addJsMethod("login()"));
                break;
            case R.string.slide_record_switch://开关活动记录
                webView.setNeedDraw(!webView.isNeedDraw());
                ToastUtils.showToast(webView.isNeedDraw() ? "开启" : "关闭");
                break;
            case R.string.one_key_publish://一键发布
                aliResult = "";
                aliOneKeyPublish = true;
                autoFragmentClick(cacheAvailable ? R.string.detail_1688 : R.string.one_piece_send);
                break;
            case R.string.debug_switch://调试开关
                notAuto = !notAuto;
                ToastUtils.showToast(notAuto ? "调试开启" : "调试关闭");
                break;
            case R.string.timing_publish:
                webScrollToEnd();
                webView.loadUrl(JsUtils.addJsMethod("timePublish(\"sell-o-radio\",\"7\",\"input\",\"1\")"));
                break;
            case R.string.ymd_click_record:
                clickRecord(Constants.TIME_CLICK_YMD_X, Constants.TIME_CLICK_YMD_Y);
                break;
            case R.string.hmm_click_record:
                clickRecord(Constants.TIME_CLICK_HMM_X, Constants.TIME_CLICK_HMM_Y);
                break;
            case R.string.timing_publish_click:
                if (YMD_INPUT_FINISH && HMM_INPUT_FINISH) {
                    YMD_INPUT_FINISH = false;
                    HMM_INPUT_FINISH = false;
                }
                if (!YMD_INPUT_FINISH) {
                    getPublishDate();
                    if (!TextUtils.isEmpty(fullDateFromat)) {
                        String[] date = fullDateFromat.split(" ");
                        vu.getLocalMethod().clickPublishTime(date[0], date[1]);
                    } else {
                        String dayFromat = DateUtil.getDayFromat(System.currentTimeMillis() + 86400000);
                        vu.getLocalMethod().clickPublishTime(dayFromat, "20:20:20");
                    }
                }
//                else if (!HMM_INPUT_FINISH) {
//
//                    if (!TextUtils.isEmpty(fullDateFromat)) {
//                        String[] date = fullDateFromat.split(" ");
//                        vu.getLocalMethod().clickPublishTimeHmm(date[0], date[1]);
//                    } else {
//                        String dayFromat = DateUtil.getDayFromat(System.currentTimeMillis() + 86400000);
//                        vu.getLocalMethod().clickPublishTimeHmm(dayFromat, "20:20:20");
//                    }
//                }

                break;
            case R.string.comfir_publish_click_record:
                clickRecord(Constants.TIME_CLICK_COMFIR_X, Constants.TIME_CLICK_COMFIR_Y);
                break;
            case R.string.comfir_publish_click:
                vu.getLocalMethod().clickPublishTimeComfir();
                break;
            case R.string.set_title:
                String titleResult = activity.getTitleResult();
                if (!TextUtils.isEmpty(titleResult)) {
                    titlResultArray = titleResult.split("\n");
                }
                webView.loadUrl(JsUtils.addJsMethod("setTitle(\"next-input next-input-single next-input-medium fusion-input\"" + ",\"" + ((null == titlResultArray || aliCurrentPage == -1) ? "开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏开启快速冷藏" : titlResultArray[aliCurrentPage]) + "\")"));
//                webView.loadUrl(JsUtils.addJsMethod("showKeyboardInput(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\"" + ",\"" + ((null == titlResultArray || aliCurrentPage == -1) ? "test" : titlResultArray[aliCurrentPage]) + "\")"));
//                webView.loadUrl(JsUtils.addJsMethod("showKeyboardInput(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\"" + ",\"" + fullDateFromat + "\")"));

//                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium fusion-input\"+ ",\"" + aliResutlArray[aliCurrentPage] + "\")"));
                break;
            case R.string.ymd_input:
                getPublishDate();
                LogUtils.e(fullDateFromat);
                webView.loadUrl(JsUtils.addJsMethod("showKeyboardInput(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\"" + ",\"" + fullDateFromat + "\")"));

//                String ymd = fullDateFromat.split(" ")[0];
//                sendStrSync(ymd);

                break;
            case R.string.hmm_input:
                getPublishDate();
                String hmm = fullDateFromat.split(" ")[1];
                sendStrSync(hmm);
                break;
            case R.string.autoDebug_switch:
                autoDebug = !autoDebug;
                ToastUtils.showToast(autoDebug ? "开" : "关");
                break;
            case R.string.save_draft:
                webView.loadUrl(JsUtils.addJsMethod("saveDraft()"));
                break;
            case R.string.get_publish_result:
                String draft = SharedPreferencesUtils.getValue(Constants.SAVE_DRAFT);
                LogUtils.e("draft:\n" + draft);
                break;
            case R.string.clear_publish_result:
                aliCurrentPage = -1;
                SharedPreferencesUtils.putIntValue(Constants.ALI_CURRENT_PAGE, aliCurrentPage);
                SharedPreferencesUtils.putValue(Constants.SAVE_DRAFT, "");
                break;
            case R.string.refresh_page:
                if (oldUrl.contains("item.publish.taobao.com")) {
                    webView.getSettings().setJavaScriptEnabled(false);
                }
                webView.reload();
                webView.getSettings().setJavaScriptEnabled(true);
                break;

        }
    }


    private void sendStrSync(final String str) {
        biz.getSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Instrumentation instrumentation = new Instrumentation();
                instrumentation.sendStringSync(str);
            }
        });
    }

    private void getPublishDate() {
        String beginTime = DateUtil.date2TimeStamp(activity.getBeginTime(), "yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtil.date2TimeStamp(activity.getEndTime(), "yyyy-MM-dd HH:mm:ss");
        Long spaceTime = Long.parseLong(endTime) - Long.parseLong(beginTime);
        if (null == aliResutlArray) {
            ToastUtils.showToast("getPublishDate test");
            String dayFromat = DateUtil.getDayFromat(System.currentTimeMillis() + 86400000);
            fullDateFromat = dayFromat + " 12:12:12";
            return;
        }
        singleSpaceTime = spaceTime / (aliResutlArray.length < 38 ? aliResutlArray.length : 38);
        if (aliCurrentPage > 38) {
            indexAddTime = aliCurrentPage / 38;
        } else {
            indexAddTime = 0;
        }
        Long dateTime = Long.parseLong(beginTime) + aliCurrentPage * singleSpaceTime + indexAddTime * 10 * 60 * 1000;
        fullDateFromat = DateUtil.getFullDateFromat(dateTime);
    }

    private void clickRecord(String picSpacePasteClickDownX, String picSpacePasteClickDownY) {
        if (!webView.isClickRecord()) {
            webView.setClickRecord(true);
            webView.setSlideRecord(false);
            ToastUtils.showToast("点击记录开启");
            return;
        }
        if (webView.isNeedDraw()) {
            SharedPreferencesUtils.putValue(picSpacePasteClickDownX, webView.getACTION_CLICK_DOWN_X() + "");
            SharedPreferencesUtils.putValue(picSpacePasteClickDownY, webView.getACTION_CLICK_DOWN_Y() + "");
        }
        LogUtils.e("CLICK_DOWN_X:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(picSpacePasteClickDownX)));
        LogUtils.e("CLICK_DOWN_Y:" + (int) Float.parseFloat(SharedPreferencesUtils.getValue(picSpacePasteClickDownY)));
    }


    public void typeIn(final int KeyCode) {
        try {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync(KeyCode);
        } catch (Exception e) {
            Log.e("Exception：", e.toString());
        }
    }


    public void typeIn(final String str) {
        biz.getSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendStringSync(str);
                } catch (Exception e) {
                    Log.e("Exception：", e.toString());
                }
            }
        });
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
        LogUtils.e("loadFinish:\n" + url);


        if (oldUrl.contains("item.publish.taobao.com") && (clickPosition == R.string.refresh_page || clickPosition == R.string.go_draft_page)) {
            webView.getSettings().setJavaScriptEnabled(true);
        }

        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (oldUrl.equals(url) && !isErrorOccur) {
            return;
        }
        isErrorOccur = false;
        oldUrl = url;
        if (clickPosition != R.string.upload_pic) {
            webScrollToEnd();
        }

        switch (clickPosition) {
            case R.string.one_piece_send:
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
        }
    }





    @Override
    public void afterGetJson(final String json) {
        LogUtils.e("afterGetJson_urlJson:" + json);
        LogUtils.e("pageIndex:" + pageIndex + "\n" + json);
        switch (clickPosition) {
            case R.string.one_piece_send:
                LogUtils.e("afterGetJson_urlJson:" + "pageIndex\n" + json);
                vu.blockNetIamge(webView, true);
                aliResult = TextUtils.isEmpty(aliResult) ? json : aliResult + "\n" + json;
                if (isInit) {
                    isInit = false;
                    LogUtils.e("getPagingNum()" + vu.getLocalMethod().getPagingNum() + "");
                    if (pageIndex < (debug ? 1 : vu.getLocalMethod().getPagingNum())) {
                        if (pageIndex == 0) {
                            pageIndex = 1;
                        }
                        pageIndex++;
                        nextUrl = urlOrigin + "#beginPage=" + pageIndex + "&offset=0";
                        webView.loadUrl(Constants.BAIDU);
                    } else {
                        autoFragmentClick(R.string.detail_1688);
                    }
                }
//                if (!TextUtils.isEmpty(json)) {
////                    webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"fui-next\")"));
//                }
                break;
            case R.string.filter_word:
                webView.loadUrl(JsUtils.addJsMethod("showKeyboardAfterClick(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));
                break;
            case R.string.timing_publish:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-date-picker next-date-picker-medium next-date-picker-show-time\")"));
                break;

        }
    }

    private void webScrollToEnd() {
        webView.scrollTo(0, webView.getScrollYRange());
    }

    @Override
    public void afterClick() {
        LogUtils.e("afterClick:" + ResUtil.getS(clickPosition));
        switch (clickPosition) {
            case R.string.timing_publish:
                autoFragmentClick(R.string.timing_publish_click);
                break;
            case R.string.shuaiShou:
                webView.loadUrl(JsUtils.addJsMethod("shuaishouComfirm()"));
                break;
        }
    }

    private static void simuateClick(MyWebView webView, String constantx,String constanty) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(constantx))) {
            GestureTouchUtils.simulateClick(webView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(constantx)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(constanty)));
        } else {
            LogUtils.e(constantx + "," + constanty + "为空");
        }
    }

    @Override
    public void inputFinish() {
        LogUtils.e("inputFinish:" + ResUtil.getS(clickPosition));
        hideKeybord();
        switch (clickPosition) {

        }
    }

    public void hideKeybord() {
        vu.getLocalMethod().hideKeybord();
    }

    @Override
    public void errorOccur() {

        LogUtils.e("errorOccur:" + ResUtil.getS(clickPosition));
        isErrorOccur = true;
        switch (clickPosition) {
            case R.string.one_click_shop:

                break;
            case R.string.one_piece_send:
                if (!oldUrl.contains("www.baidu.com")) {
                    autoFragmentClick(R.string.detail_1688);
                }
                break;
        }
    }




    private void delayAutoFragmentClick(final int nextpage) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                autoFragmentClick(nextpage);
                BaseApplication.getmHandler().removeCallbacks(this);
            }
        }, 1500);
    }


    @Override
    public boolean onLongClick(View v) {
        LogUtils.e("onLongClick");
        return false;
    }
}
