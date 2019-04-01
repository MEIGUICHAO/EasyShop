package com.example.moguhaian.easyshop.Search;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Bean.SameStyleShopsBean;
import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SameStyleBiz extends BaseBiz {

    private int sleepTime = 5000;
    private String[] sortTypeArray;

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public ArrayList<String> getSameUrlList() {
        return sameUrlList;
    }

    public void setSameUrlList(ArrayList<String> sameUrlList) {
        this.sameUrlList = sameUrlList;
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(ArrayList<String> titleList) {
        this.titleList = titleList;
    }

    public boolean isGetSameUrlBegin() {
        return isGetSameUrlBegin;
    }

    public void setGetSameUrlBegin(boolean getSameUrlBegin) {
        isGetSameUrlBegin = getSameUrlBegin;
    }

    public ArrayList<String> getMinUrlList() {
        return minUrlList;
    }

    public void setMinUrlList(ArrayList<String> minUrlList) {
        this.minUrlList = minUrlList;
    }

    public int getFunctionIndex() {
        return functionIndex;
    }

    public void setFunctionIndex(int functionIndex) {
        this.functionIndex = functionIndex;
    }

    public int getSameUrlIndex() {
        return sameUrlIndex;
    }

    public void setSameUrlIndex(int sameUrlIndex) {
        this.sameUrlIndex = sameUrlIndex;
    }

    private ArrayList<String> sameUrlList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private boolean isGetSameUrlBegin = false;
    private ArrayList<String> minUrlList = new ArrayList<>();
    private int functionIndex = -1;
    private int sameUrlIndex = -1;
    private String[] pageArray;
    private int pageIndedx = 0;
    private int srotTypeIndex = 0;


    @Override
    public void initWebView(WebView wv, Activity activity) {
        super.initWebView(wv, activity);
        pageArray = activity.getResources().getStringArray(R.array.page_index);
        sortTypeArray = activity.getResources().getStringArray(R.array.sort_type);
    }

    public void getSameUrl(String json, final String url) {
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
                    String loadUrl = url + "&" + sortTypeArray[srotTypeIndex] + "&" + pageArray[pageIndedx];
                    LogUtils.e("loadUrl:" + loadUrl);
                    webView.loadUrl(loadUrl);
                    pageIndedx++;
                } else {
                    pageIndedx = 0;
                    srotTypeIndex++;
                    if (srotTypeIndex < sortTypeArray.length) {
                        String loadUrl = url + "&" + sortTypeArray[srotTypeIndex] + "&" + pageArray[pageIndedx];
                        pageIndedx++;
                        LogUtils.e("loadUrl:" + loadUrl);
                        webView.loadUrl(loadUrl);
                    } else {
                        isGetSameUrlBegin = false;
                    }
                }

            }
        }, Constants.DELAY_TIME);
    }

    public void getInitShop(String json) {
        sameUrlIndex++;
        String initShop = TaoUtils.getInitShop(json);
        if (!TextUtils.isEmpty(initShop)) {
            minUrlList.add(initShop);
        }
        if (sameUrlIndex < sameUrlList.size()) {
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        webView.loadUrl(sameUrlList.get(sameUrlIndex));
                        LogUtils.e("size:" + sameUrlList.size() + ",progress:" + sameUrlIndex);
                    } catch (Exception e) {
                        LogUtils.e("Exception:" + e.toString());
                    }
                }
            }, Constants.DELAY_TIME);
        }
    }
}
