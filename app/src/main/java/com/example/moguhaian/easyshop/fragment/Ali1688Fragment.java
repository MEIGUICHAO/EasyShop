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

public class Ali1688Fragment extends BaseFragment<Ali1688Vu, Ali1688Biz> implements LoadFinishListener, LoalMethodListener, View.OnLongClickListener {


    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;
    private int[] items = {R.string.one_key_publish, R.string.go1688, R.string.one_piece_send, R.string.nextpage, R.string.one_click_shop, R.string.login, R.string.pics_space, R.string.get_pics_space_pic, R.string.publish_scene, R.string.filter_word, R.string.office_publish
            , R.string.new_floder, R.string.floder_name, R.string.tao_keepworker, R.string.detail_1688, R.string.get_detail_1688, R.string.get_upload_pic, R.string.login_name, R.string.get_mobile_detail, R.string.upload_pic, R.string.slide_record_switch
            , R.string.pic_input_click_record, R.string.pic_select_click_record, R.string.pic_search_click_record, R.string.paste_click_record, R.string.edit_sku, R.string.edit_price, R.string.sku_count, R.string.click_moblie_detail, R.string.comfir_moblie_detail
            , R.string.timing_publish, R.string.ymd_click_record, R.string.hmm_click_record, R.string.timing_publish_click, R.string.comfir_publish_click_record, R.string.comfir_publish_click, R.string.pic_space_select_all, R.string.pic_space_click_record, R.string.pic_space_click, R.string.folder_select_click_record
            , R.string.folder_comfir_click_record, R.string.move_folder, R.string.set_title, R.string.tao_guanjia_search, R.string.tao_guanjia_to_publish_scene, R.string.tao_guanjia_search_click, R.string.record_switch, R.string.resetSku, R.string.edit_detail_area, R.string.cache_available, R.string.cur_publish_time
            , R.string.ymd_input, R.string.hmm_input, R.string.sku_pic_name, R.string.autoDebug_switch, R.string.save_draft, R.string.picspace_clear_up, R.string.leave_publish_page_record, R.string.get_publish_result, R.string.refresh_page, R.string.model_number
            , R.string.go_draft_page, R.string.reload_draft_click_record, R.string.pic_space_search_pic, R.string.clear_publish_result, R.string.base_attr_select, R.string.brand_click_record, R.string.pruduct_pot_click_record, R.string.age_availabel_click_record, R.string.sex_available_click_record};

    private int pageIndex = 0;
    //    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch#beginPage=2&offset=0
//    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch#beginPage=2&offset=0
//    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch#beginPage=4&offset=0
    private String urlOrigin;
    private String urlOrigin1 = "https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=";
    //    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=
//    &beginPage=2&offset=0
    private String urlOrigin2 = "&netType=1%2C11&n=y&from=taoSellerSearch";
    private String aliPageTag = "#beginPage=" + "placeTag" + "&offset=0";
    //    private String urlOrigin = "https://detail.1688.com/offer/539556562483.html?sk=consign";
    //    private String urlOrigin = "https://item.publish.taobao.com/sell/publish.htm?catId=50013459&itemId=592570571674";
    private String loginUrl = "https://login.1688.com/member/signin.htm?tracelog=account_verify";
    //    private String picSpaceUrl = "https://sucai.wangpu.taobao.com/?spm=a2113j.8836301.0.0.1206139dRygyV4#/manage/pic?_k=40zg4c";
    private String picSpaceUrl = "https://sucai.wangpu.taobao.com/?spm=a2113j.8836301.0.0.694f139dxs5m9o#/manage/pic?_k=umx2ua";
    private String taoGuanjiaUrl = "https://guanjia.1688.com/page/consignoffer.htm?menuCode=consignoffer";
    private String basePublish = "https://item.publish.taobao.com/sell/publish.htm?itemId=";
    private String nextUrl;
    private String aliResult = "";
    private boolean isInit = false;
    private boolean needGetJson = false;
    private String[] attrClickSelectNames = {"品牌", "产地", "适用年龄段", "适用性别"};

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
        }
    };
    private String oldUrl = "";
    private String clickStr = "";
    private ArrayList<String> skuInfo;
    private ArrayList<String> skuEditPricesList;
    private ArrayList<String> skuPicInfo;
    private int skuEditPos;
    private int skuEditPicPos = 0;
    private boolean aliOneKeyPublish = false;
    private int skuEditPricesPos;
    //    private int skuLimit = 19;
    private int skuLimit = 12;
    private int aliCurrentPage = -1;
    private int aliMaxPage = -1;
    private boolean notAuto = false;

    private boolean debug = false;
    private ArrayList<String> skuEditCountList;
    private int skuEditCountPos;
    private String[] aliResutlArray;
    private boolean recordAvailable = true;
    private boolean uploadCheck;
    private boolean cacheAvailable = true;
    private MainActivity activity;
    private Long singleSpaceTime;
    private String fullDateFromat;
    private int errorIndex = -1;
    private String[] titlResultArray;
    private boolean autoDebug = false;
    private int skuPicNamePos;
    private ArrayList<Object> skuPicNameList;
    private boolean isErrorOccur = false;
    private boolean isReset = false;
    private int oldClickPosition;
    private String currentAliTitle;
    private String itemId;
    private boolean loadSearchResult;
    private boolean oneClickShopInit;
    private boolean officePublishInit;
    private int indexAddTime = 0;
    private int attrClickSelectNamesPosition = -1;

    private String[] ATTR_CLICK_X = {Constants.BRAND_CLICK_X, Constants.PRODUCT_POT_CLICK_X, Constants.AGE_AVAILABLE_CLICK_X, Constants.SEX_AVAILABLE_CLICK_X};
    private String[] ATTR_CLICK_Y = {Constants.BRAND_CLICK_Y, Constants.PRODUCT_POT_CLICK_Y, Constants.AGE_AVAILABLE_CLICK_Y, Constants.SEX_AVAILABLE_CLICK_Y};
    private boolean YMD_INPUT_FINISH = false;
    private boolean HMM_INPUT_FINISH = false;
    private OfficePublishRunnable officePublishRunnable;

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
        biz.getWebChromeClient().setOnLoadFinishListener(Ali1688Fragment.this);
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
            case R.string.pruduct_pot_click_record:
                clickRecord(Constants.PRODUCT_POT_CLICK_X,Constants.PRODUCT_POT_CLICK_Y);
                break;
            case R.string.age_availabel_click_record:
                clickRecord(Constants.AGE_AVAILABLE_CLICK_X,Constants.AGE_AVAILABLE_CLICK_Y);
                break;
            case R.string.sex_available_click_record:
                clickRecord(Constants.SEX_AVAILABLE_CLICK_X,Constants.SEX_AVAILABLE_CLICK_Y);
                break;
            case R.string.brand_click_record:
                clickRecord(Constants.BRAND_CLICK_X,Constants.BRAND_CLICK_Y);
                break;
            case R.string.pic_space_search_pic:
                loadSearchResult = false;
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"advanced-sarch-senior\")"));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//
                        webView.loadUrl(JsUtils.addJsMethod("advanceSearchClick()"));
//                        webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-radio-inner unpress\")"));
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium con-label-input\"" + ",\"" + currentAliTitle + "\")"));
                                mHandler.removeCallbacks(this);
                            }
                        }, 1000);
                        mHandler.removeCallbacks(this);
                    }
                }, 1000);

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
                oneClickShopInit = true;
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"menu-item-btn  J_ConsignBtn_DistributeToTaobao\")"));
                break;
            case R.string.login:
                if (!oldUrl.contains("login.1688.com")) {
                    webView.loadUrl(loginUrl);
                } else {
                    webView.loadUrl(JsUtils.addJsMethod("login1688()"));
                }
                break;
            case R.string.pics_space://图片空间
                vu.blockNetIamge(webView, true);
                webView.loadUrl(picSpaceUrl);
                break;
            case R.string.get_pics_space_pic://获取图片空间图片

                vu.getLocalMethod().resetPicspaceList();
                webView.loadUrl(JsUtils.addJsMethod("getSrcByClassName(\"" + false + "\")"));
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickPosition == R.string.get_pics_space_pic) {
                            autoFragmentClick(R.string.get_pics_space_pic);
                            BaseApplication.getmHandler().removeCallbacks(this);
                        }
                    }
                }, 10000);
                break;
            case R.string.publish_scene://发布现场
//                webView.loadUrl("https://item.publish.taobao.com/sell/publish.htm?itemId=597381610891");
                webView.loadUrl("https://item.publish.taobao.com/sell/draft.htm?draftId=26387548");
                break;
            case R.string.filter_word://过滤文字
                webView.scrollTo(0, webView.getScrollY());
                webView.loadUrl(JsUtils.addJsMethod("filterWorld(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));

//                webView.loadUrl(JsUtils.addJsMethod("findMoblieImgLength(\"m-editor-content-body\")"));
                break;
            case R.string.office_publish:
                officePublishInit = true;
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassNameWithoutAfterClick(\"btn confirm official-confirm\")"));
                break;
            case R.string.new_floder:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"itemList-head-btn-item\")"));
                break;
            case R.string.floder_name://文件夹名称
                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium fileCreat-setting-panel-text-input\",\"test\")"));
                break;
            case R.string.tao_keepworker://淘管家
                webView.loadUrl(taoGuanjiaUrl);
//                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium fileCreat-setting-panel-text-input\",\"test\")"));
                break;
            case R.string.detail_1688://1688详情
//                webView.loadUrl("https://detail.1688.com/offer/587652823516.html?sk=consign");
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
                    aliCurrentPage = aliResutlArray.length / 2;
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
            case R.string.get_detail_1688://获取1688详情图片
                vu.getLocalMethod().setAliLimitPrice(-1);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(JsUtils.addJsMethod("getAliDetailInfo()"));
                        mHandler.removeCallbacks(this);
                    }
                }, 1000);
                break;
            case R.string.get_upload_pic://获取上传图片
                getDiff();
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickPosition == R.string.get_upload_pic) {
                            autoFragmentClick(R.string.get_pics_space_pic);
                        }
                        BaseApplication.getmHandler().removeCallbacks(this);

                    }
                }, 50000);


                break;
            case R.string.login_name://login
                webView.loadUrl(JsUtils.addJsMethod("login()"));
                break;
            case R.string.get_mobile_detail://生成手机详情
                webView.loadUrl(JsUtils.addJsMethod("comfirMobileDetail()"));
                break;
            case R.string.upload_pic://上传图片

//                CommonUtils.copyText("f871d1bb-3b5f-4206-8b64-7d9f8788bc57");
//                skuPicInfo = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    skuPicInfo.add("f871d1bb-3b5f-4206-8b64-7d9f8788bc57");
//                }
//                skuEditPicPos = 0;
//                webView.loadUrl(JsUtils.addJsMethod("editSKuPic(\"" + skuEditPicPos + "\")"));

                //=========================================================
                webView.loadUrl(JsUtils.addJsMethod("goSaleInfoArea()"));
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseApplication.getmHandler().removeCallbacks(this);
                        skuEditPicPos = 0;
                        ArrayList<String> compareResultPicList = biz.getCompareResultList();

//                        compareResultPicList.clear();
//                        compareResultPicList.add("abc\na9deb840-30fb-43ec-b582-9246d7174bd4");
//                        compareResultPicList.add("abc\na9deb840-30fb-43ec-b582-9246d7174bd4");
//                        compareResultPicList.add("abc\na9deb840-30fb-43ec-b582-9246d7174bd4");

                        LogUtils.e("上传数量:" + compareResultPicList.size());
                        if (compareResultPicList.size() < 1) {
                            ToastUtils.showToast("请选择上传图片");
                            return;
                        }
                        uploadCheck = true;
//                        CommonUtils.copyText(compareResultPicList.get(skuEditPicPos));
                        skuPicInfo = new ArrayList<>();
                        for (int i = 0; i < compareResultPicList.size(); i++) {
                            skuPicInfo.add(compareResultPicList.get(i).split("\n")[1]);
                        }
                        webView.loadUrl(JsUtils.addJsMethod("editSKuPic(\"" + skuEditPicPos + "\")"));
                    }
                }, 1000);

                break;
            case R.string.slide_record_switch://开关活动记录
                webView.setNeedDraw(!webView.isNeedDraw());
                ToastUtils.showToast(webView.isNeedDraw() ? "开启" : "关闭");
                break;
            case R.string.pic_input_click_record://图片输入框点击记录
                clickRecord(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X, Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y);
                break;
            case R.string.pic_select_click_record://图片选择点击记录
                clickRecord(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X, Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y);
                break;
            case R.string.pic_search_click_record://图片搜索
                clickRecord(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_X, Constants.PIC_SPACE_SEARCH_CLICK_DOWN_Y);
                break;
            case R.string.paste_click_record://粘贴点击记录
                clickRecord(Constants.PIC_SPACE_PASTE_CLICK_DOWN_X, Constants.PIC_SPACE_PASTE_CLICK_DOWN_Y);
                break;
            case R.string.edit_sku://编辑sku
                skuEditPos = 0;
                skuInfo = new ArrayList<>();
                ArrayList<String> compareResultList = biz.getCompareResultList();
                LogUtils.e("上传数量:" + compareResultList.size());
                for (int i = 0; i < compareResultList.size(); i++) {
                    skuInfo.add(compareResultList.get(i).split("\n")[0]);
                }
                if (skuInfo.size() > 0) {
                    webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\"" + "," + "\"next-input next-input-single next-input-medium clear color-dropdown-input\"" + ")"));
                }

//                skuEditPos = 0;
//                skuInfo = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    skuInfo.add("哥莫拉" + i);
//                }
//                webView.loadUrl(JsUtils.addJsMethod("clearSku(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\")"));
                break;
            case R.string.edit_price://编辑价格:
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
            case R.string.one_key_publish://一键发布
                aliResult = "";
                aliOneKeyPublish = true;
                autoFragmentClick(cacheAvailable ? R.string.detail_1688 : R.string.one_piece_send);
                break;
            case R.string.debug_switch://调试开关
                notAuto = !notAuto;
                ToastUtils.showToast(notAuto ? "调试开启" : "调试关闭");
                break;
            case R.string.sku_count://sku数量:

                skuEditCountPos = 0;
                skuEditCountList = new ArrayList<>();
//
                ArrayList<String> compareResultCountList = biz.getCompareResultList();
                LogUtils.e("上传数量:" + compareResultCountList.size());
                for (int i = 0; i < compareResultCountList.size(); i++) {
                    skuEditCountList.add(compareResultCountList.get(i).split("\n")[3]);
                }
                LogUtils.e("origin_prices:" + skuEditCountList.get(skuEditCountPos));
                webView.loadUrl(JsUtils.addJsMethod("setSkuCount(\"" + skuEditCountPos + "\",\"" + (Integer.parseInt(skuEditCountList.get(skuEditCountPos)) > 100 ? 100 : skuEditCountList.get(skuEditCountPos)) + "\")"));

                break;
            case R.string.click_moblie_detail:
                clickRecord(Constants.PUBLISH_MOBILE_DETAIL_X, Constants.PUBLISH_MOBILE_DETAIL_Y);
                break;
            case R.string.comfir_moblie_detail:
                clickRecord(Constants.PUBLISH_MOBILE_COMFIR_X, Constants.PUBLISH_MOBILE_COMFIR_Y);
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
            case R.string.pic_space_select_all:
                webView.loadUrl(JsUtils.addJsMethod("selectAllPic()"));
                break;
            case R.string.pic_space_click_record:
                clickRecord(Constants.PIC_SPACE_FIRST_CLICK_X, Constants.PIC_SPACE_FIRST_CLICK_Y);
                break;
            case R.string.pic_space_click:
                vu.getLocalMethod().picSpaceFirstClick();
                break;
            case R.string.folder_select_click_record:
                clickRecord(Constants.FOLDER_SELECT_CLICK_X, Constants.FOLDER_SELECT_CLICK_Y);
                break;
            case R.string.folder_comfir_click_record:
                clickRecord(Constants.FOLDER_COMFIR_CLICK_X, Constants.FOLDER_COMFIR_CLICK_Y);
                break;
            case R.string.move_folder:
                vu.getLocalMethod().folderMoveClick();
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
            case R.string.tao_guanjia_search:

                if (null == aliResutlArray) {
                    String[] aliTempResutlArray2 = aliResult.split("\n");
                    aliResutlArray = TaoUtils.getSingle(aliTempResutlArray2);
                }
                if (aliCurrentPage == -1) {
                    aliCurrentPage = 0;
                }
                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-search-lt-input\"" + ",\"" + aliResutlArray[aliCurrentPage] + "\")"));

                break;
            case R.string.tao_guanjia_to_publish_scene:

                webView.loadUrl(JsUtils.addJsMethod("getPublishItemId()"));
                break;
            case R.string.tao_guanjia_search_click:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassNamePosition(\"next-btn next-btn-primary next-btn-small\",\"1\")"));
                break;
            case R.string.record_switch:
                recordAvailable = !recordAvailable;
                vu.getLocalMethod().setRecordClickGoOn(recordAvailable);
                ToastUtils.showToast(recordAvailable ? "记录开启" : "记录关闭");
                break;
            case R.string.resetSku:
                webView.loadUrl(JsUtils.addJsMethod("resetSkuPic(0)"));
                break;
            case R.string.edit_detail_area:
                webView.loadUrl(JsUtils.addJsMethod("goEditDetailArea()"));
                break;
            case R.string.cache_available:
                cacheAvailable = !cacheAvailable;
                ToastUtils.showToast(cacheAvailable ? "开启" : "关闭");
                break;
            case R.string.cur_publish_time:
                getPublishDate();
//                for (int i = 0; i < 50; i++) {
//                    Long dateTime = Long.parseLong(beginTime) + i * singleSpaceTime;
//                    String fullDateFromat = DateUtil.getFullDateFromat(dateTime);
//                    LogUtils.e("date:" + fullDateFromat);
//                }

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
            case R.string.sku_pic_name:
                String[] picsName = SharedPreferencesUtils.getValue(Constants.GET_UPLOAD_PIC_NAMES).split("###");
                skuPicNamePos = 0;
                skuPicNameList = new ArrayList<>();
                LogUtils.e("上传数量:" + picsName.length);
                for (int i = 0; i < picsName.length; i++) {
                    skuPicNameList.add(picsName[i]);
                }
                if (skuPicNameList.size() > 0) {
                    webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuPicNamePos + "\",\"" + skuPicNameList.get(skuPicNamePos) + "\"" + "," + "\"sell-o-input sell-color-checkbox-item-remark\"" + ")"));
                }
                break;
            case R.string.autoDebug_switch:
                autoDebug = !autoDebug;
                ToastUtils.showToast(autoDebug ? "开" : "关");
                break;
            case R.string.save_draft:
                webView.loadUrl(JsUtils.addJsMethod("saveDraft()"));
                break;

            case R.string.picspace_clear_up:
                isReset = true;
                webView.getSettings().setJavaScriptEnabled(false);
                vu.blockNetIamge(webView, true);
                webView.loadUrl(picSpaceUrl);
                break;
            case R.string.leave_publish_page_record:
                webView.loadUrl(picSpaceUrl);
                clickRecord(Constants.PUBLISH_PAGE_LEAVE_X, Constants.PUBLISH_PAGE_LEAVE_Y);
                break;
            case R.string.get_publish_result:
                String draft = SharedPreferencesUtils.getValue(Constants.SAVE_DRAFT);
                String[] resultUrl = draft.split("\n");
                String titleResult1 = activity.getTitleResult();
                String[] split = titleResult1.split("\n");
                String draftResult = "";
                for (int i = 0; i < resultUrl.length; i++) {
                    if (resultUrl[i].contains("https")) {
                        draftResult = TextUtils.isEmpty(draftResult) ? (resultUrl[i] + "\n" + split[i]) : (draftResult + "\n" + (resultUrl[i] + "\n" + split[i]));
                    }
                }
                LogUtils.e("draft:\n" + draftResult);
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

            case R.string.model_number:
                webView.loadUrl(JsUtils.addJsMethod("setChildInputValueByClassName(\"next-col next-col-12 left-items\",0,2,\"20190622\")"));
                break;
            case R.string.go_draft_page:
//                webView.getSettings().setJavaScriptEnabled(false);

                webView.loadUrl(JsUtils.addJsMethod("clickChildElementByTagName(\"draft-item\",0,\"div\",0)"));
                break;
            case R.string.reload_draft_click_record:
                clickRecord(Constants.RELOAD_DRAFT_CLICK_X, Constants.RELOAD_DRAFT_CLICK_Y);
                webView.loadUrl(JsUtils.addJsMethod("clickChildElementByTagName(\"draft-item\",0,\"div\",0)"));
                break;
            case R.string.base_attr_select:
                webView.loadUrl(JsUtils.addJsMethod("checkTimingData()"));
//

//                if (attrClickSelectNamesPosition == -1) {
//                    webView.loadUrl(JsUtils.addJsMethod("goBaseInfoArea()"));
//                    attrClickSelectNamesPosition = 0;
//                } else {
//                    attrClickSelectNamesPosition++;
//                }
//                if (attrClickSelectNamesPosition == 0) {
//                    CommonUtils.copyText("other/其他");
//                }
//                if (attrClickSelectNamesPosition < attrClickSelectNames.length) {
//                    webView.loadUrl(JsUtils.addJsMethod("clickAttrElementsByClassNameAndInnerText(\"" + attrClickSelectNames[attrClickSelectNamesPosition] + "\")"));
//                } else {
//                    attrClickSelectNamesPosition = -1;
//                    autoFragmentClick(R.string.model_number);
//                }
                break;
        }
    }

    private void getDiff() {
        biz.resetPosition();
        SharedPreferencesUtils.putValue(Constants.GET_UPLOAD_PIC_NAMES, "");
        ToastUtils.showToast("对比开始");
        biz.getSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        try {
            if (vu.getLocalMethod().getPicSpaceUrlList().size() > 1 && vu.getLocalMethod().getAliDetailDataList().size() > 0) {
                biz.diffResult(vu.getLocalMethod().getAliDetailDataList(), vu.getLocalMethod().getPicSpaceUrlList(), new Ali1688Biz.DiffProgressListener() {
                    @Override
                    public void diffFinish() {
                        if (biz.getCompareResultList().size() > 0) {
                            webView.loadUrl(basePublish + itemId);
//                            autoFragmentClick(R.string.tao_keepworker);
//                            autoFragmentClick(R.string.tao_keepworker);
                        } else {
                            autoFragmentClick(R.string.nextpage);
                        }

                    }
                });
            } else {
                ToastUtils.showToast("get upload pic error");
                errorOcur(R.string.get_upload_pic);
            }

        } catch (Exception e) {

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
        if (clickPosition == R.string.detail_1688) {
            autoFragmentClick(R.string.get_detail_1688);
        }
        if (clickPosition == R.string.upload_pic) {

            if (uploadCheck) {
                uploadCheck = false;
            } else {
                vu.getLocalMethod().picSpaceInputClick(skuPicInfo.get(skuEditPicPos));
//                webView.loadUrl(JsUtils.addJsMethod("getPicFromSpaces()"));
            }
        }
        if (clickPosition == R.string.picspace_clear_up && isReset) {
            isReset = false;
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(picSpaceUrl);
            return;
        }
        if (clickPosition == R.string.one_click_shop) {
//            if (oneClickShopInit) {
//                oneClickShopInit = false;
//                BaseApplication.getmHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (clickPosition == R.string.one_click_shop) {
//                            autoFragmentClick(R.string.office_publish);
//                        }
//                        BaseApplication.getmHandler().removeCallbacks(this);
//                    }
//                }, 5500);
//
//                return;
//            }
            if (null == officePublishRunnable) {
                officePublishRunnable = new OfficePublishRunnable();
            }
            BaseApplication.getmHandler().postDelayed(officePublishRunnable, 1000);
        }
        if (clickPosition == R.string.office_publish) {
            if (officePublishInit) {
                officePublishInit = false;
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickPosition == R.string.office_publish) {
                            webView.loadUrl(JsUtils.addJsMethod("checkOfficPublishResult()"));
                        }
                        BaseApplication.getmHandler().removeCallbacks(this);

                    }
                }, 5500);

                return;
            }
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(JsUtils.addJsMethod("checkOfficPublishResult()"));
                    BaseApplication.getmHandler().removeCallbacks(this);
                }
            }, 1000);

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
            case R.string.get_upload_pic:
                autoFragmentClick(R.string.timing_publish);
                break;
            case R.string.go_draft_page:
                goDraftPageSave();

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
            case R.string.nextpage:
//                webView.loadUrl(JsUtils.addJsMethod("getAliPageCount()"));
                break;
            case R.string.pics_space://加载图片空间
                delayAutoFragmentClick(R.string.pic_space_search_pic);
//                autoFragmentClick(R.string.pic_space_search_pic);
//                delayAutoFragmentClick(R.string.get_pics_space_pic);
//                autoFragmentClick(R.string.get_pics_space_pic);
                break;
            case R.string.publish_scene://发布现场
//                autoFragmentClick(R.string.edit_sku);
                break;
            case R.string.detail_1688:
                if (aliOneKeyPublish) {
                    autoFragmentClick(R.string.get_detail_1688);
                }
                break;
            case R.string.tao_keepworker:
//                next-search-lt-input
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.removeCallbacks(this);
                        autoFragmentClick(R.string.tao_guanjia_search);
                    }
                }, 1500);
                break;

            case R.string.picspace_clear_up:
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        autoFragmentClick(R.string.pic_space_select_all);
                        BaseApplication.getmHandler().removeCallbacks(this);
                    }
                }, 1000);

                break;
            case R.string.pic_space_search_pic:
                autoFragmentClick(R.string.get_pics_space_pic);
                break;
        }
    }

    class OfficePublishRunnable implements Runnable {
        @Override
        public void run() {
            webView.loadUrl(JsUtils.addJsMethod("checkOfficeAvailable()"));
        }
    }


    private void goDraftPageSave() {
        String draft = SharedPreferencesUtils.getValue(Constants.SAVE_DRAFT);
        String title = (null == titlResultArray || aliCurrentPage == -1) ? "test" : titlResultArray[aliCurrentPage];
        draft = TextUtils.isEmpty(draft) ? oldUrl + "\n" + fullDateFromat : draft + "\n" + oldUrl + "\n" + fullDateFromat;
        LogUtils.e("draft:" + draft);
        SharedPreferencesUtils.putValue(Constants.SAVE_DRAFT, draft);
        webView.getSettings().setJavaScriptEnabled(false);
        autoFragmentClick(R.string.nextpage);
    }

    @Override
    public void afterGetJson(final String json) {
        LogUtils.e("afterGetJson_urlJson:" + json);
        LogUtils.e("pageIndex:" + pageIndex + "\n" + json);
        switch (clickPosition) {
            case R.string.one_click_shop:
                BaseApplication.getmHandler().removeCallbacks(officePublishRunnable);
                autoFragmentClick(R.string.office_publish);
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
            case R.string.pic_space_search_pic:
                loadSearchResult = true;
                webView.loadUrl(json);
                break;
            case R.string.resetSku:
//                webScrollToEnd();
                autoFragmentClick(R.string.filter_word);
                break;
            case R.string.tao_guanjia_to_publish_scene:
                itemId = json.split("###")[0];
                currentAliTitle = json.split("###")[1];
                autoFragmentClick(R.string.pics_space);
//                webView.loadUrl(basePublish + json);
                break;
            case R.string.go1688:

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
            case R.string.nextpage:

                break;
            case R.string.get_pics_space_pic:


                autoFragmentClick(R.string.get_upload_pic);
                break;
            case R.string.filter_word:
                webView.loadUrl(JsUtils.addJsMethod("showKeyboardAfterClick(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));
                break;
            case R.string.get_mobile_detail:
//                生成手机详情
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHandler.removeCallbacks(this);
//                        webView.loadUrl(JsUtils.addJsMethod("comfirMobileDetail()"));
//                    }
//                }, 1000);
                break;
            case R.string.upload_pic:

                uploadCheck = true;
                skuEditPicPos++;
                if (skuEditPicPos < ((skuPicInfo.size() < skuLimit ? skuPicInfo.size() : skuLimit))) {
                    webView.loadUrl(JsUtils.addJsMethod("editSKuPic(\"" + skuEditPicPos + "\")"));
                } else {
                    ToastUtils.showToast("图片sku结束");
                    autoFragmentClick(R.string.set_title);

                }

                break;
            case R.string.edit_sku:

                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X, KeyEvent.KEYCODE_DEL};
                        for (int i = 0; i < keyCodeArray.length; i++) {
                            try {
                                typeIn(keyCodeArray[i]);
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                skuEditPos++;
                                if (skuEditPos < (skuInfo.size() < skuLimit ? skuInfo.size() : skuLimit)) {
                                    webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\"" + "," + "\"next-input next-input-single next-input-medium clear color-dropdown-input\"" + ")"));
                                } else if (skuEditPos == (skuInfo.size() < skuLimit ? skuInfo.size() : skuLimit) && skuInfo.size() < 5) {
                                    webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuEditPos + "\",\"" + "" + "\"" + "," + "\"next-input next-input-single next-input-medium clear color-dropdown-input\"" + ")"));
                                } else {
                                    ToastUtils.showToast("文字sku结束");
                                    webScrollToEnd();
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mHandler.removeCallbacks(this);
                                            autoFragmentClick(R.string.edit_price);

                                        }
                                    }, 1000);
                                }
                            }
                        });
                    }
                });
                break;
            case R.string.sku_pic_name:

                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X, KeyEvent.KEYCODE_DEL};
                        for (int i = 0; i < keyCodeArray.length; i++) {
                            try {
                                typeIn(keyCodeArray[i]);
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                skuPicNamePos++;
                                if (skuPicNamePos < (skuPicNameList.size() < skuLimit ? skuPicNameList.size() : skuLimit)) {
                                    webView.loadUrl(JsUtils.addJsMethod("editSKu(\"" + skuPicNamePos + "\",\"" + skuPicNameList.get(skuPicNamePos) + "\"" + "," + "\"sell-o-input sell-color-checkbox-item-remark\"" + ")"));

                                } else {
                                    ToastUtils.showToast("sku图片名称结束");
                                    webScrollToEnd();
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mHandler.removeCallbacks(this);

                                            webView.loadUrl(JsUtils.addJsMethod("goBaseInfoArea()"));
//                                            delayAutoFragmentClick(R.string.base_attr_select);
                                            autoFragmentClick(R.string.ymd_input);
                                        }
                                    }, 1000);
                                }
                            }
                        });
                    }
                });
                break;
            case R.string.edit_price://
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
                                LogUtils.e("skuEditPricesPos:" + skuEditPricesPos);
                                if (skuEditPricesPos < (skuEditPricesList.size() < skuLimit ? skuEditPricesList.size() : skuLimit)) {
                                    double prices;
                                    if (Double.parseDouble(skuEditPricesList.get(skuEditPricesPos)) < Constants.ADD_PRIFILE) {
                                        prices = Double.parseDouble(skuEditPricesList.get(skuEditPricesPos)) * 2 + 10;
                                        prices = prices < vu.getLocalMethod().getAliLimitPrice() ? vu.getLocalMethod().getAliLimitPrice() : prices;
                                    } else {
                                        prices = Double.parseDouble(skuEditPricesList.get(skuEditPricesPos)) + Constants.ADD_PRIFILE + 10;
                                        prices = prices < vu.getLocalMethod().getAliLimitPrice() ? vu.getLocalMethod().getAliLimitPrice() : prices;
                                    }
                                    LogUtils.e("origin_prices:" + skuEditPricesList.get(skuEditPricesPos));
                                    webView.loadUrl(JsUtils.addJsMethod("setSkuPrice(\"" + skuEditPricesPos + "\",\"" + prices + "\")"));
                                } else {
                                    ToastUtils.showToast("价格sku结束");
                                    autoFragmentClick(R.string.sku_count);
                                }
                            }
                        });
                    }
                });

                break;
            case R.string.sku_count://
                biz.getSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Instrumentation inst = new Instrumentation();
                        for (int i = 0; i < 10; i++) {
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
                        }
                        inst.sendStringSync(json);
                        LogUtils.e(json);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                skuEditCountPos++;
                                if (skuEditCountPos < (skuEditCountList.size() < skuLimit ? skuEditCountList.size() : skuLimit)) {
                                    LogUtils.e("origin_prices:" + skuEditCountList.get(skuEditCountPos));
                                    webView.loadUrl(JsUtils.addJsMethod("setSkuCount(\"" + skuEditCountPos + "\",\"" + (Integer.parseInt(skuEditCountList.get(skuEditCountPos)) > 100 ? 100 : skuEditCountList.get(skuEditCountPos)) + "\")"));

                                } else {
                                    ToastUtils.showToast("sku库存结束");
                                    autoFragmentClick(R.string.set_title);
//                                    autoFragmentClick(R.string.set_title);
                                }
                            }
                        });
                    }
                });

                break;
            case R.string.edit_detail_area:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.removeCallbacks(this);
                        vu.getLocalMethod().generateMoblieDetail();

                    }
                }, 1000);

                break;
            case R.string.timing_publish:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-date-picker next-date-picker-medium next-date-picker-show-time\")"));
                break;
            case R.string.comfir_publish_click:
                if (fullDateFromat.equals(json)) {
                    LogUtils.e("fullDateFromat==json");
                } else {
                    autoFragmentClick(R.string.timing_publish);
                }

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
//                        autoFragmentClick(R.string.save_draft);
//                        autoFragmentClick(R.string.base_attr_select);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadUrl(JsUtils.addJsMethod("checkTimingData()"));
                            }
                        }, 1000);

                    }
                }
                break;
            case R.string.base_attr_select:
                if (attrClickSelectNamesPosition == 0) {
                    typeIn("other/");
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simuateClick(webView, ATTR_CLICK_X[attrClickSelectNamesPosition], ATTR_CLICK_Y[attrClickSelectNamesPosition]);
                        autoFragmentClick(R.string.base_attr_select);
                        mHandler.removeCallbacks(this);
                    }
                }, 1500);

                break;
            case R.string.pic_space_search_pic:
                webView.loadUrl(JsUtils.addJsMethod("getPicSpacesSpecUrl()"));
                break;


            case R.string.office_publish:
                autoFragmentClick(R.string.tao_keepworker);
                break;
            case R.string.get_detail_1688:
                autoFragmentClick(R.string.one_click_shop);

//                autoFragmentClick(R.string.pics_space);
                break;
            case R.string.upload_pic://上传图片
                CommonUtils.copyText(skuPicInfo.get(skuEditPicPos));
//                move to loadFinish
//                webView.loadUrl(JsUtils.addJsMethod("getPicFromSpaces()"));

                break;
            case R.string.timing_publish:
                autoFragmentClick(R.string.timing_publish_click);
                break;
            case R.string.tao_guanjia_search_click:
                autoFragmentClick(R.string.tao_guanjia_to_publish_scene);
                break;
            case R.string.get_mobile_detail:
                autoFragmentClick(R.string.edit_sku);
                break;
            case R.string.edit_detail_area:
//                autoFragmentClick(R.string.get_mobile_detail);
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseApplication.getmHandler().removeCallbacks(this);
                        autoFragmentClick(R.string.edit_sku);
                    }
                }, 2000);


                break;
            case R.string.pic_space_select_all:
                autoFragmentClick(R.string.pic_space_click);
                break;
            case R.string.pic_space_click:
                autoFragmentClick(R.string.move_folder);
                break;
            case R.string.move_folder:
                autoFragmentClick(R.string.pic_space_select_all);
                break;
            case R.string.save_draft:
                autoFragmentClick(R.string.go_draft_page);
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
            case R.string.model_number:
                webScrollToEnd();
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        autoFragmentClick(R.string.resetSku);
//                        autoFragmentClick(R.string.timing_publish);
                        BaseApplication.getmHandler().removeCallbacks(this);
                    }
                }, 1000);
                break;
            case R.string.tao_guanjia_search:
                autoFragmentClick(R.string.tao_guanjia_search_click);
                break;
            case R.string.filter_word:
                autoFragmentClick(R.string.edit_detail_area);
                break;
            case R.string.set_title:
                autoFragmentClick(R.string.sku_pic_name);
                break;
            case R.string.timing_publish_click:
                autoFragmentClick(R.string.comfir_publish_click);
                break;
            case R.string.comfir_publish_click:
                autoFragmentClick(R.string.comfir_publish_click);
                HMM_INPUT_FINISH = true;
                break;
            case R.string.ymd_input:
                webView.loadUrl(JsUtils.addJsMethod("goBaseInfoArea()"));
//                delayAutoFragmentClick(R.string.base_attr_select);
//                autoFragmentClick();
                autoFragmentClick(R.string.save_draft);
                break;

            case R.string.save_draft:

                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-btn next-btn-primary next-btn-medium action-btn\")"));
//                autoFragmentClick(R.string.resetSku);
                break;
            case R.string.pic_space_search_pic:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-btn next-btn-normal next-btn-medium bottom-btns-primary\")"));
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHandler.removeCallbacks(this);
//                    }
//                }, 2500);
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
                if (null == officePublishRunnable) {
                    officePublishRunnable = new OfficePublishRunnable();
                }
                BaseApplication.getmHandler().postDelayed(officePublishRunnable, 500);
                break;
            case R.string.base_attr_select:
                autoFragmentClick(R.string.base_attr_select);
                break;
            case R.string.tao_guanjia_to_publish_scene:
                //淘管家缺失ali
                autoFragmentClick(R.string.nextpage);
                break;
            case R.string.get_detail_1688:
//                autoFragmentClick(R.string.nextpage);
                break;
            case R.string.one_piece_send:
                if (!oldUrl.contains("www.baidu.com")) {
                    autoFragmentClick(R.string.detail_1688);
                }
                break;
            case R.string.get_pics_space_pic:
                if (webView.getUrl().contains("sucai.wangpu.taobao")) {
                    errorOcur(R.string.pics_space);
                } else if (webView.getUrl().contains("item.publish")) {
                    autoFragmentClick(R.string.timing_publish);

//                    autoFragmentClick(R.string.resetSku);
                }
                break;
            case R.string.office_publish:
                errorOcur(R.string.office_publish);
                break;
            case R.string.pic_space_select_all:
                autoFragmentClick(R.string.nextpage);
                break;
            case R.string.pic_space_search_pic:
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!loadSearchResult) {
                            autoFragmentClick(R.string.pic_space_search_pic);
                        }
                        BaseApplication.getmHandler().removeCallbacks(this);
                    }
                }, 60000);
                break;
        }
    }

    private void errorOcur(int get_pics_space_pic) {
        if (errorIndex == -1) {
            errorIndex = 0;
        } else if (errorIndex == 3) {
            errorIndex = -1;
            erroOpera(R.string.nextpage);
        } else {
            errorIndex++;
            autoFragmentClick(get_pics_space_pic);
        }
    }

    private void erroOpera(final int nextpage) {
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
