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

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Base.BaseFragment;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.MainActivity;
import com.example.moguhaian.easyshop.R;
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
import com.example.moguhaian.easyshop.listener.ShouldOverrideUrlLoadingListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShuaiShouFragment extends BaseFragment<Ali1688Vu, Ali1688Biz> implements LoadFinishListener, LoalMethodListener, View.OnLongClickListener, ShouldOverrideUrlLoadingListener {


    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;
    private int[] items = {R.string.one_key_publish, R.string.go1688, R.string.one_piece_send, R.string.nextpage, R.string.one_click_shop, R.string.login, R.string.filter_word, R.string.detail_1688, R.string.login_name, R.string.slide_record_switch
            , R.string.ymd_click_record, R.string.hmm_click_record, R.string.timing_publish,R.string.timing_publish_click, R.string.comfir_publish_click_record, R.string.comfir_publish_click, R.string.set_title, R.string.cache_available, R.string.autoDebug_switch, R.string.save_draft,
            R.string.get_publish_result, R.string.refresh_page, R.string.go_draft_page, R.string.reload_draft_click_record, R.string.shuaiShou, R.string.shuaiShou_title, R.string.get_ali_limit_prices, R.string.shuaiShou_to_taobao, R.string.shuaiShou_to_taobao_click_record
            , R.string.shuaiShou_to_taobao, R.string.shuaiShou_to_publish_click_record, R.string.shuaiShou_to_publish, R.string.click_moblie_detail, R.string.comfir_moblie_detail, R.string.check_sku_price, R.string.publish_scene,R.string.shuaishou_2_get_title};

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

    public boolean isAliOneKeyPublish() {
        return aliOneKeyPublish;
    }

    public void setAliOneKeyPublish(boolean aliOneKeyPublish) {
        this.aliOneKeyPublish = aliOneKeyPublish;
    }

    private boolean aliOneKeyPublish = false;
    //    private int skuLimit = 19;
    private int aliCurrentPage = -1;
    private boolean notAuto = false;

    private boolean debug = false;
    private String[] aliResutlArray;

    public boolean isCacheAvailable() {
        return cacheAvailable;
    }

    public void setCacheAvailable(boolean cacheAvailable) {
        this.cacheAvailable = cacheAvailable;
    }

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
    private int shuaishouIndex = 0;
    private Timer timer;
    private String refreshUrl = "";
    private boolean clickEnable = true;
    private String[] aliName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void afterOnCreate() {


        activity = (MainActivity) getActivity();
        vu.initWebViewSetting(webView, getActivity());
        biz.initWebView(webView, getActivity());
        biz.getWebChromeClient().setOnLoadFinishListener(ShuaiShouFragment.this);
        biz.getWebViewClient().setShouldLoadingListener(ShuaiShouFragment.this);
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
                    if (null != timer) {
                        timer.cancel();
                        timer = new Timer();
                    } else {
                        timer = new Timer();
                    }
                    clickPosition = position;
                    rightClickSwitch(position);
                    refreshUrl = webView.getUrl();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //do something
                            BaseApplication.getmHandler().post(new Runnable() {
                                @Override
                                public void run() {
//                                    if (clickPosition == R.string.save_draft) {
//                                        autoFragmentClick(R.string.save_draft);
//                                        return;
//                                    }

                                    LogUtils.e("timer_schedule:" + webView.getUrl());
                                    if (!TextUtils.isEmpty(refreshUrl)) {
                                        if (refreshUrl.equals(webView.getUrl())) {
                                            autoFragmentClick(R.string.nextpage);
                                            return;
                                        }
                                    }
                                    if (webView.getUrl().contains("https://item.publish.taobao.com")) {
                                        if (!webView.getUrl().contains("draftId")) {
                                            autoFragmentClick(R.string.set_title);
                                        } else {
                                            webView.getSettings().setJavaScriptEnabled(false);
                                            autoFragmentClick(R.string.go_draft_page);
                                        }
                                    } else {
                                        webView.reload();
                                        if (webView.getUrl().contains("detail.1688.com")) {
                                            autoFragmentClick(R.string.get_ali_limit_prices);
                                        } else if (webView.getUrl().contains("https://page.1688.com/html")) {
                                            autoFragmentClick(R.string.shuaiShou);
                                        }
                                    }
                                }
                            });
                        }
                    },1000 * 60 * 2);
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
            case R.string.publish_scene:
                webView.loadUrl("https://item.publish.taobao.com/sell/draft.htm?draftId=26387548");
                break;
            case R.string.cache_available:
                cacheAvailable = !cacheAvailable;
                ToastUtils.showToast(cacheAvailable ? "开启" : "关闭");
                break;
            case R.string.check_sku_price:
                webView.loadUrl(JsUtils.addJsMethod("getSkuPrices(\"0\"" + ",\"" + vu.getLocalMethod().getAliLimitPrice() + "\")"));

                break;
            case R.string.click_moblie_detail:
                clickRecord(Constants.PUBLISH_MOBILE_DETAIL_X, Constants.PUBLISH_MOBILE_DETAIL_Y);
                break;
            case R.string.comfir_moblie_detail:
                clickRecord(Constants.PUBLISH_MOBILE_COMFIR_X, Constants.PUBLISH_MOBILE_COMFIR_Y);
                break;
            case R.string.shuaiShou:
                webView.loadUrl(JsUtils.addJsMethod("shuaishou()"));
                break;
            case R.string.go1688:
                aliName = activity.getResources().getStringArray(R.array.ali_name);
                urlOrigin = urlOrigin1 + aliName[activity.getIndex()] + urlOrigin2;
//                webView.loadUrl("https://page.1688.com/html/shuaishou-puhuo.html?sourceBizId=&appKeyInSourcePlatform=5159993&sign=529B4B7381A0AEC4FDB3899623CBC213ED5EC879&userIdInSourcePlatform=2511106550&targetPlatformId=TAOBAO&targetPlatformShopId=null&pushProductIds=37955297658");
//                webView.loadUrl("https://item.publish.taobao.com/sell/publish.htm?itemId=597958252566");
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
                if (webView.getUrl().contains("https://item.publish.")) {
                    webView.getSettings().setJavaScriptEnabled(false);
                }
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
//                webView.loadUrl("https://detail.1688.com/offer/37955297658.html?spm=b26110380.sw1688.mof001.47.79727d17ARTcG3&sk=consign");
//                webView.loadUrl("https://detail.1688.com/offer/578607374928.html?sk=consign");
//                webView.loadUrl("https://detail.1688.com/offer/580549523893.html?sk=consign");

                vu.blockNetIamge(webView, true);
                if (!cacheAvailable && !TextUtils.isEmpty(aliResult)) {
                    SharedPreferencesUtils.putValue(Constants.ALI_SHOP_RESULT, aliResult);
                }
                if (cacheAvailable) {
                    aliResult = SharedPreferencesUtils.getValue(Constants.ALI_SHOP_RESULT);
                    aliCurrentPage = SharedPreferencesUtils.getIntValue(Constants.ALI_CURRENT_PAGE, 0);
                    LogUtils.e("detail_1688 aliResult:" + aliResult);
                    LogUtils.e("detail_1688 aliCurrentPage:" + aliCurrentPage);
                }

                String[] aliTempResutlArray = aliResult.split("\n");
                LogUtils.e("aliTempResutlArray:" + aliTempResutlArray.length);
                aliResutlArray = TaoUtils.getSingle(aliTempResutlArray);
                LogUtils.e("aliResutlArray:" + aliResutlArray.length);
                if (aliCurrentPage == -1) {
                    aliCurrentPage = 0;
                }
                LogUtils.e("aliCurrentPage:" + aliCurrentPage);
                if (aliCurrentPage >= aliResutlArray.length) {
                    ToastUtils.showToast("detail 最大值");
                    aliCurrentPage = -1;
                    activity.setIndex(activity.getIndex() + 1);
                    activity.switchFragment(1);
                    cacheAvailable = false;
//                    aliCurrentPage = aliResutlArray.length / 2;
                    return;
                }
                LogUtils.e("1688url:" + aliResutlArray[aliCurrentPage]);
                if (!TextUtils.isEmpty(aliResutlArray[aliCurrentPage])) {
                    webView.loadUrl(aliResutlArray[aliCurrentPage]);
                } else {
                    autoFragmentClick(R.string.nextpage);
                }
                webView.getSettings().setJavaScriptEnabled(true);
                break;
            case R.string.login_name://login
                webView.loadUrl(JsUtils.addJsMethod("login()"));
                break;
            case R.string.slide_record_switch://开关活动记录
                webView.setNeedDraw(!webView.isNeedDraw());
                ToastUtils.showToast(webView.isNeedDraw() ? "开启" : "关闭");
                break;
            case R.string.one_key_publish://一键发布
//                webView.loadUrl("https://item.publish.taobao.com/sell/publish.htm?itemId=597958252566");

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
                delayLoadJs("timePublish(\"sell-o-radio\",\"7\",\"input\",\"1\")");
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
            case R.string.shuaiShou_title:
                webView.loadUrl(JsUtils.addJsMethod("setShuaiShouTitle()"));
                break;
            case R.string.get_ali_limit_prices:
                webView.loadUrl(JsUtils.addJsMethod("getAliLimitPrice()"));
                break;
            case R.string.shuaiShou_to_taobao_click_record:
                clickRecord(Constants.SHUAISHOU_TAOBAO_CLICK_RECORD_X, Constants.SHUAISHOU_TAOBAO_CLICK_RECORD_Y);
                break;
            case R.string.shuaiShou_to_taobao:
                simuateClick(webView, Constants.SHUAISHOU_TAOBAO_CLICK_RECORD_X, Constants.SHUAISHOU_TAOBAO_CLICK_RECORD_Y);
                break;
            case R.string.shuaiShou_to_publish_click_record:
                clickRecord(Constants.SHUAISHOU_PUBLISH_CLICK_RECORD_X, Constants.SHUAISHOU_PUBLISH_CLICK_RECORD_Y);
                break;
            case R.string.shuaiShou_to_publish:
                simuateClick(webView, Constants.SHUAISHOU_PUBLISH_CLICK_RECORD_X, Constants.SHUAISHOU_PUBLISH_CLICK_RECORD_Y);
                break;
            case R.string.edit_detail_area:
                webView.loadUrl(JsUtils.addJsMethod("goEditDetailArea()"));
                break;
            case R.string.go_draft_page:
//                webView.getSettings().setJavaScriptEnabled(false);

                webView.loadUrl(JsUtils.addJsMethod("clickChildElementByTagName(\"draft-item\",0,\"div\",0)"));
                break;
            case R.string.shuaishou_2_get_title:
                activity.setIndex(activity.getIndex() + 1);
                activity.switchFragment(1);
                break;
        }
    }

    private void delayLoadJs(final String method) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                webView.loadUrl(JsUtils.addJsMethod(method));
                BaseApplication.getmHandler().removeCallbacks(this);
            }
        }, 1000);
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
        } if (oldUrl.contains("item.publish.taobao.com") ) {
            clickEnable = true;
        }

        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!refreshUrl.equals(url)) {
            refreshUrl = "";
        }

        if (url.contains("https://page.1688")) {
            simuateClick(webView, Constants.SHUAISHOU_TAOBAO_CLICK_RECORD_X, Constants.SHUAISHOU_TAOBAO_CLICK_RECORD_Y);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    simuateClick(webView, Constants.SHUAISHOU_PUBLISH_CLICK_RECORD_X, Constants.SHUAISHOU_PUBLISH_CLICK_RECORD_Y);

                }
            }, 2000);
//            if (clickPosition == R.string.shuaiShou_to_taobao) {
//                shuaishouIndex++;
//                if (shuaishouIndex > 1) {
//                    delayAutoFragmentClick(R.string.shuaiShou_to_publish);
//                }
//            } else {
//                shuaishouIndex++;
//                if (shuaishouIndex > 2) {
//                    delayAutoFragmentClick(R.string.shuaiShou_to_taobao);
//                    shuaishouIndex = 0;
//                }
//            }
        }

        if (clickPosition == R.string.shuaiShou) {
            if (url.contains("https://item.publish.taobao.com")) {
                autoFragmentClick(R.string.set_title);
            }
        }


        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (oldUrl.equals(url) && !isErrorOccur) {
            return;
        }
        isErrorOccur = false;
        oldUrl = url;
//        if (clickPosition != R.string.upload_pic) {
//            webScrollToEnd();
//        }

        switch (clickPosition) {
            case R.string.go1688:
                autoFragmentClick(R.string.one_key_publish);
                break;
//            case R.string.shuaiShou_to_publish:
//                autoFragmentClick(R.string.set_title);
//                break;
            case R.string.detail_1688:
                delayAutoFragmentClick(R.string.get_ali_limit_prices);
                break;
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

            case R.string.go_draft_page:
                goDraftPageSave();

                break;
        }
    }





    @Override
    public void afterGetJson(final String json) {
        LogUtils.e("afterGetJson_urlJson:" + json);
        LogUtils.e("pageIndex:" + pageIndex + "\n" + json);
        switch (clickPosition) {
            case R.string.check_sku_price:
                delayAutoFragmentClick(R.string.timing_publish);

                break;
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
            case R.string.comfir_publish_click:
                if (fullDateFromat.equals(json)) {
                    LogUtils.e("fullDateFromat==json");
                    clickPosition = 0;
//                    autoFragmentClick(-1);
//                    delayAutoFragmentClick(R.string.save_draft);
                    delayAutoFragmentClick(R.string.save_draft);
                } else {
                    autoFragmentClick(R.string.timing_publish);
                }

            case R.string.edit_detail_area:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.removeCallbacks(this);
                        vu.getLocalMethod().generateMoblieDetail();

                    }
                }, 1000);


                break;

            case R.string.go_draft_page:
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.RELOAD_DRAFT_CLICK_Y))) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            GestureTouchUtils.simulateClick(webView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.RELOAD_DRAFT_CLICK_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.RELOAD_DRAFT_CLICK_Y)));
                            mHandler.removeCallbacks(this);
                        }
                    }, 1000);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (clickPosition == R.string.go_draft_page) {
                                goDraftPageSave();
                            }
                            mHandler.removeCallbacks(this);
                        }
                    }, 20000);
                }
                break;

        }
    }


    private void goDraftPageSave() {
        String draft = SharedPreferencesUtils.getValue(Constants.SAVE_DRAFT);
        String title = (null == titlResultArray || aliCurrentPage == -1) ? "test" : titlResultArray[aliCurrentPage];
        getPublishDate();
        draft = TextUtils.isEmpty(draft) ? oldUrl + "\n" + fullDateFromat : draft + "\n" + oldUrl + "\n" + fullDateFromat;
        LogUtils.e("draft:" + draft);
        SharedPreferencesUtils.putValue(Constants.SAVE_DRAFT, draft);
        webView.getSettings().setJavaScriptEnabled(false);
        autoFragmentClick(R.string.nextpage);
    }

    private void webScrollToEnd() {
        webView.scrollTo(0, webView.getScrollYRange());
    }

    @Override
    public void afterClick() {
        LogUtils.e("afterClick:" + ResUtil.getS(clickPosition));
        switch (clickPosition) {
            case R.string.comfir_publish_click:
                if (!YMD_INPUT_FINISH) {
                    YMD_INPUT_FINISH = true;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-date-picker next-date-picker-medium next-date-picker-show-time\")"));
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (!TextUtils.isEmpty(fullDateFromat)) {
                                        String[] date = fullDateFromat.split(" ");
                                        vu.getLocalMethod().clickPublishTimeHmm(date[0], date[1]);
                                    } else {
                                        String dayFromat = DateUtil.getDayFromat(System.currentTimeMillis() + 86400000);
                                        vu.getLocalMethod().clickPublishTimeHmm(dayFromat, "20:20:20");
                                    }

                                    mHandler.removeCallbacks(this);
                                }
                            }, 2500);

                            mHandler.removeCallbacks(this);
                        }
                    }, 2500);
                } else {
                    if (HMM_INPUT_FINISH) {
                        delayAutoFragmentClick(R.string.save_draft);
//                        autoFragmentClick(R.string.base_attr_select);
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                webView.loadUrl(JsUtils.addJsMethod("checkTimingData()"));
//                            }
//                        }, 1000);

                    }
                }
                break;
            case R.string.save_draft:
                autoFragmentClick(R.string.go_draft_page);
                break;
            case R.string.edit_detail_area:
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseApplication.getmHandler().removeCallbacks(this);
                        autoFragmentClick(R.string.check_sku_price);
                    }
                }, 2000);

//                autoFragmentClick(R.string.check_sku_price);
                break;
            case R.string.timing_publish:
                autoFragmentClick(R.string.timing_publish_click);
                break;
            case R.string.one_click_shop:
                delayAutoFragmentClick(R.string.shuaiShou);
                checkShuaishou();
                break;
            case R.string.shuaiShou:
                shuaishouIndex = 0;
                webView.loadUrl(JsUtils.addJsMethod("shuaishouComfirm()"));
                break;
        }
    }

    private void checkShuaishou() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (webView.getUrl().contains("detail.1688.com")) {
                    autoFragmentClick(R.string.nextpage);
                }
                mHandler.removeCallbacks(this);
            }
        }, 15000);
    }

    private void simuateClick(MyWebView webView, String constantx,String constanty) {
        if (clickEnable) {
            if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(constantx))) {
                GestureTouchUtils.simulateClick(webView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(constantx)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(constanty)));
            } else {
                LogUtils.e(constantx + "," + constanty + "为空");
            }
        }
    }

    @Override
    public void inputFinish() {
        LogUtils.e("inputFinish:" + ResUtil.getS(clickPosition));
        hideKeybord();
        switch (clickPosition) {
            case R.string.timing_publish_click:
                autoFragmentClick(R.string.comfir_publish_click);
                break;
            case R.string.comfir_publish_click:
                autoFragmentClick(R.string.comfir_publish_click);
                HMM_INPUT_FINISH = true;
                break;
            case R.string.save_draft:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-btn next-btn-primary next-btn-medium action-btn\")"));

                break;
            case R.string.get_ali_limit_prices:
                delayAutoFragmentClick(R.string.one_click_shop);
                break;
            case R.string.set_title:
                autoFragmentClick(R.string.filter_word);
                break;
            case R.string.filter_word:
                autoFragmentClick(R.string.edit_detail_area);
                break;
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
            case R.string.comfir_publish_click:
                autoFragmentClick(R.string.timing_publish);
                break;
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
        }, 1000);
    }


    @Override
    public boolean onLongClick(View v) {
        LogUtils.e("onLongClick");
        return false;
    }

    @Override
    public void goPublish(final String url) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                clickEnable = false;
                webView.loadUrl(url);
            }
        });

    }
}
