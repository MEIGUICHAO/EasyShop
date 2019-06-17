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

public class Ali1688Fragment extends BaseFragment<Ali1688Vu, Ali1688Biz> implements LoadFinishListener, LoalMethodListener {

    @BindView(R.id.webView)
    MyWebView webView;
    Unbinder unbinder;
    private int[] items = {R.string.go1688, R.string.one_piece_send, R.string.nextpage, R.string.one_click_shop, R.string.login, R.string.pics_space, R.string.get_pics_space_pic, R.string.publish_scene, R.string.filter_word, R.string.office_publish
            , R.string.new_floder, R.string.floder_name, R.string.tao_keepworker, R.string.detail_1688, R.string.get_detail_1688, R.string.get_upload_pic, R.string.login_name, R.string.get_mobile_detail, R.string.upload_pic, R.string.slide_record_switch
            , R.string.pic_input_click_record, R.string.pic_select_click_record, R.string.pic_search_click_record, R.string.paste_click_record, R.string.edit_sku, R.string.edit_price, R.string.one_key_publish, R.string.sku_count, R.string.click_moblie_detail, R.string.comfir_moblie_detail
            , R.string.timing_publish, R.string.ymd_click_record, R.string.hmm_click_record, R.string.timing_publish_click, R.string.comfir_publish_click_record, R.string.comfir_publish_click, R.string.pic_space_select_all, R.string.pic_space_click_record, R.string.pic_space_click, R.string.folder_select_click_record
            , R.string.folder_comfir_click_record, R.string.move_folder, R.string.set_title,R.string.tao_guanjia_search,R.string.tao_guanjia_to_publish_scene,R.string.tao_guanjia_search_click,R.string.record_switch,R.string.resetSku,R.string.edit_detail_area};

    private int pageIndex = 0;
    //    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch#beginPage=2&offset=0
//    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch#beginPage=2&offset=0
//    https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch#beginPage=4&offset=0
    private String urlOrigin = "https://s.1688.com/selloffer/offer_search.htm?descendOrder=true&sortType=va_rmdarkgmv30rt&uniqfield=userid&keywords=%CE%A2%B2%A8%C2%AF%D6%C3%CE%EF%BC%DC&netType=1%2C11&n=y&from=taoSellerSearch";
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
    private int skuLimit = 5;
    private int aliCurrentPage = -1;
    private int aliMaxPage = -1;
    private boolean notAuto = false;

    private boolean debug = true;
    private ArrayList<Object> skuEditCountList;
    private int skuEditCountPos;
    private String[] aliResutlArray;
    private boolean recordAvailable = true;
    private boolean uploadCheck;


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

    public void autoFragmentClick(int position) {
        if (!notAuto && aliOneKeyPublish) {
            LogUtils.e("autoFragmentClick:" + ResUtil.getS(position));
            clickPosition = position;
            rightClickSwitch(position);
        }
    }

    @Override
    public void fragmentRightClick(int position) {
        super.fragmentRightClick(position);
        clickPosition = items[position];
        rightClickSwitch(items[position]);

    }

    private void rightClickSwitch(int item) {
        switch (item) {
            case R.string.go1688:
//                biz.getWebViewClient().setNeedListener(true);
//                pageIndex = 1;
//
                webView.loadUrl(urlOrigin);
//                webView.loadUrl("https://item.publish.taobao.com/sell/publish.htm?catId=124392001&itemId=593358799794");
//                webView.loadMyUrl("https://item.publish.taobao.com/sell/publish.htm?catId=124392001&itemId=593358799794",Constants.CSS_FILE_NAME);

                break;
            case R.string.one_piece_send:
                aliResult = "";
                isInit = true;
                webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
                break;
            case R.string.nextpage:
                LogUtils.e("aliResult:" + aliResult);
//                webView.loadUrl(JsUtils.addJsMethod("getAliPageCount()"));
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
            case R.string.pics_space://图片空间
                webView.loadUrl(picSpaceUrl);
                break;
            case R.string.get_pics_space_pic://获取图片空间图片
                vu.getLocalMethod().resetPicspaceList();
                webView.loadUrl(JsUtils.addJsMethod("getSrcByClassName()"));
                break;
            case R.string.publish_scene://发布现场
                webView.loadUrl("https://item.publish.taobao.com/sell/publish.htm?catId=124392001&itemId=593358799794");
                break;
            case R.string.filter_word://过滤文字
                webView.scrollTo(0, webView.getScrollY());
                webView.loadUrl(JsUtils.addJsMethod("filterWorld(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));

//                webView.loadUrl(JsUtils.addJsMethod("findMoblieImgLength(\"m-editor-content-body\")"));
                break;
            case R.string.office_publish:
                webView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"btn confirm official-confirm\")"));
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
                String[] aliTempResutlArray = aliResult.split("\n");
                LogUtils.e("aliTempResutlArray:" + aliTempResutlArray.length);
                aliResutlArray = TaoUtils.getSingle(aliTempResutlArray);
                LogUtils.e("aliResutlArray:" + aliResutlArray.length);
                aliCurrentPage = 0;
                webView.loadUrl(aliResutlArray[aliCurrentPage]);
                break;
            case R.string.get_detail_1688://获取1688详情图片
                webView.loadUrl(JsUtils.addJsMethod("getSrcAttrByTagName(\"table-sku\",\"alt\")"));
                break;
            case R.string.get_upload_pic://获取上传图片

                ToastUtils.showToast("对比开始");
                biz.diffResult(vu.getLocalMethod().getAliDetailDataList(), vu.getLocalMethod().getPicSpaceUrlList(), new Ali1688Biz.DiffProgressListener() {
                    @Override
                    public void diffFinish() {
                        autoFragmentClick(R.string.tao_keepworker);
                    }
                });
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
                webScrollToEnd();
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
                    webView.loadUrl(JsUtils.addJsMethod("clearSku(\"" + skuEditPos + "\",\"" + skuInfo.get(skuEditPos) + "\")"));
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
                aliOneKeyPublish = true;
                autoFragmentClick(R.string.one_piece_send);
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
                webView.loadUrl(JsUtils.addJsMethod("setSkuCount(\"" + skuEditCountPos + "\",\"" + skuEditCountList.get(skuEditCountPos) + "\")"));

                 break;
            case R.string.click_moblie_detail:
                clickRecord(Constants.PUBLISH_MOBILE_DETAIL_X, Constants.PUBLISH_MOBILE_DETAIL_Y);
                break;
            case R.string.comfir_moblie_detail:
                clickRecord(Constants.PUBLISH_MOBILE_COMFIR_X, Constants.PUBLISH_MOBILE_COMFIR_Y);
                break;
            case R.string.timing_publish:
                webView.loadUrl(JsUtils.addJsMethod("clickChildElementByTagName(\"sell-o-radio\",\"7\",\"input\",\"1\")"));
                break;
            case R.string.ymd_click_record:
                clickRecord(Constants.TIME_CLICK_YMD_X, Constants.TIME_CLICK_YMD_Y);
                break;
            case R.string.hmm_click_record:
                clickRecord(Constants.TIME_CLICK_HMM_X, Constants.TIME_CLICK_HMM_Y);
                break;
            case R.string.timing_publish_click:
                vu.getLocalMethod().clickPublishTime("2019-06-28", "07:07:07");
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
                webView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium fusion-input\",\"test\")"));
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
        }
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
        if (clickPosition == R.string.upload_pic) {

            if (uploadCheck) {
                uploadCheck = false;
            } else {
                webView.loadUrl(JsUtils.addJsMethod("getPicFromSpaces()"));
            }
        }

        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (oldUrl.equals(url)) {
            return;
        }
        oldUrl = url;
        webScrollToEnd();

        switch (clickPosition) {
            case R.string.go1688:

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
                webView.loadUrl(JsUtils.addJsMethod("getAliPageCount()"));
                break;
            case R.string.pics_space://加载图片空间
                autoFragmentClick(R.string.get_pics_space_pic);
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
            case R.string.tao_guanjia_to_publish_scene:
                autoFragmentClick(R.string.resetSku);
                break;
        }
    }

    @Override
    public void afterGetJson(final String json) {
        LogUtils.e("afterGetJson_urlJson:" + json);
        LogUtils.e("pageIndex:" + pageIndex + "\n" + json);
        switch (clickPosition) {
            case R.string.resetSku:
                webScrollToEnd();
                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseApplication.getmHandler().removeCallbacks(this);
                        autoFragmentClick(R.string.upload_pic);
                    }
                }, 2000);
                break;
            case R.string.tao_guanjia_to_publish_scene:
                webView.loadUrl(basePublish + json);
                break;
            case R.string.go1688:

                break;
            case R.string.one_piece_send:
                LogUtils.e("afterGetJson_urlJson:" + "pageIndex\n" + json);
                aliResult = TextUtils.isEmpty(aliResult) ? json : aliResult + "\n" + json;
                if (isInit) {
                    isInit = false;

                    if (pageIndex < (debug ? 1 : vu.getLocalMethod().getPagingNum())) {
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
//                if (aliCurrentPage == -1) {
//                    aliCurrentPage = 1;
//                    aliMaxPage = Integer.parseInt(json);
//                } else if (aliCurrentPage == aliMaxPage) {
//                    aliCurrentPage = -1;
//                    aliMaxPage = -1;
//                } else {
//                    aliCurrentPage++;
//                    webView.loadUrl(urlOrigin + aliPageTag.replace("placeTag", aliCurrentPage + ""));
//                }
                break;
            case R.string.get_pics_space_pic:
                autoFragmentClick(R.string.get_upload_pic);
                break;
            case R.string.filter_word:
                webView.loadUrl(JsUtils.addJsMethod("showKeyboardAfterClick(\"cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\")"));
                break;
            case R.string.get_detail_1688:
                autoFragmentClick(R.string.pics_space);
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
                if (skuEditPicPos < (debug ? (skuPicInfo.size() < skuLimit ? skuPicInfo.size() : skuLimit) : skuPicInfo.size())) {
                    webView.loadUrl(JsUtils.addJsMethod("editSKuPic(\"" + skuEditPicPos + "\")"));
                } else {
                    ToastUtils.showToast("图片sku结束");
                    autoFragmentClick(R.string.edit_sku);

                }

                break;
            case R.string.edit_sku:

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
                                if (skuEditPricesPos < (skuEditPricesList.size() < skuLimit ? skuEditPricesList.size() : skuLimit)) {
                                    double prices = Double.parseDouble(skuEditPricesList.get(skuEditPricesPos)) * 2 + 10;
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
                                    autoFragmentClick(R.string.set_title);
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
        }
    }

    private void webScrollToEnd() {
        webView.scrollTo(0, webView.getScrollYRange());
    }

    @Override
    public void afterClick() {
//        webView.scrollTo(0, webView.getScrollYRange());
        LogUtils.e("afterClick:" + ResUtil.getS(clickPosition));
        webView.loadUrl(JsUtils.addJsMethod("getAliTao()"));
        switch (clickPosition) {
            case R.string.upload_pic://上传图片
                CommonUtils.copyText(skuPicInfo.get(skuEditPicPos));
//                move to loadFinish
//                webView.loadUrl(JsUtils.addJsMethod("getPicFromSpaces()"));

                break;
            case R.string.timing_publish:
                webView.loadUrl(JsUtils.addJsMethod("setChildInputValueByClassName(\"next-date-picker next-date-picker-medium next-date-picker-show-time\",0,0,\"2019-06-15 00:00:01\")"));

                break;
            case R.string.tao_guanjia_search_click:
                autoFragmentClick(R.string.tao_guanjia_to_publish_scene);
                break;
            case R.string.get_mobile_detail:
                autoFragmentClick(R.string.upload_pic);
                break;
            case R.string.filter_word:
                autoFragmentClick(R.string.edit_detail_area);

                break;
            case R.string.edit_detail_area:
//                autoFragmentClick(R.string.get_mobile_detail);

                break;
        }
    }

    @Override
    public void inputFinish() {
        switch (clickPosition) {
            case R.string.tao_guanjia_search:
                autoFragmentClick(R.string.tao_guanjia_search_click);
                break;
        }
    }

    @Override
    public void errorOccur() {

        switch (clickPosition) {
            case R.string.tao_guanjia_to_publish_scene:
                //淘管家缺失ali
                autoFragmentClick(R.string.tao_guanjia_search);
                break;
        }
    }
}
