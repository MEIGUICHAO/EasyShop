package com.example.moguhaian.easyshop.Base;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.example.moguhaian.easyshop.Bean.SameStyleTitleArrayBean;
import com.example.moguhaian.easyshop.SamestyleBeanDao;
import com.example.moguhaian.easyshop.Utils.GreenDaoUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Bean.SameSytleUrlBean;
import com.example.moguhaian.easyshop.Bean.SamestyleBean;

import java.util.ArrayList;
import java.util.List;

public class LocalMethod {

    Context mContext;

    public LocalMethod(Context c) {
        this.mContext = c;
    }


    @JavascriptInterface
    public void JI_LOG(String content) {
        LogUtils.e("JI_LOG: " + content);
    }


    @JavascriptInterface
    public void localGo() {

    }


    @JavascriptInterface
    public void insertSameStyleUrls(String name, String[] urls) {

        List<SamestyleBean> list = GreenDaoUtils.getSameStyleBeanByProductName(name);
        if (list.size() < 1) {
            SamestyleBean bean = new SamestyleBean();
            bean.setProductNames(name);
            GreenDaoUtils.insertSameStyleBean(bean);
        }
        SamestyleBean samestyleBean = GreenDaoUtils.getSameStyleBeanByProductName(name).get(0);
        ArrayList<SameSytleUrlBean> samestyleBeanArrayList = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            LogUtils.e(url);
            SameSytleUrlBean sameSytleUrlBean = new SameSytleUrlBean();
            sameSytleUrlBean.setProductId(samestyleBean.getId());
            sameSytleUrlBean.setSameStyleUrl(url);
            samestyleBeanArrayList.add(sameSytleUrlBean);
        }
        GreenDaoUtils.insertSameStyleUrlBean(samestyleBeanArrayList);
//        insertSameStyleUrlBean(name, url);
    }

    private void insertSameStyleUrlBean(String name, String url) {


//        if (!GreenDaoUtils.isUrlExist(url,samestyleBean.getId())) {
//        }
    }


    @JavascriptInterface
    public void JsLog(String string) {
        LogUtils.e(string);
    }

}
