package com.example.moguhaian.easyshop.Utils;

import android.util.Log;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Bean.SameStyleTitleArrayBean;
import com.example.moguhaian.easyshop.Bean.SameSytleUrlBean;
import com.example.moguhaian.easyshop.Bean.SamestyleBean;
import com.example.moguhaian.easyshop.SameStyleTitleArrayBeanDao;
import com.example.moguhaian.easyshop.SameSytleUrlBeanDao;
import com.example.moguhaian.easyshop.SamestyleBeanDao;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoUtils {

    private static SamestyleBeanDao samestyleBeanDao;
    private static SameSytleUrlBeanDao sameSytleUrlBeanDao;
    private static SameStyleTitleArrayBeanDao sameStyleTitleArrayBeanDao;

    public static void initSameStyleBeanDao() {
        if (null == samestyleBeanDao) {
            samestyleBeanDao = BaseApplication.getInstances().getDaoSession().getSamestyleBeanDao();
        }
    }


    private static void initSameStyleUrlBeanDao() {
        if (null == sameSytleUrlBeanDao) {
            sameSytleUrlBeanDao = BaseApplication.getInstances().getDaoSession().getSameSytleUrlBeanDao();
        }
    }


    private static void initSameStyleTitleArrayBeanDao() {
        if (null == sameStyleTitleArrayBeanDao) {
            sameStyleTitleArrayBeanDao = BaseApplication.getInstances().getDaoSession().getSameStyleTitleArrayBeanDao();
        }
    }

    public static List<SamestyleBean> getSameStyleBeanByProductName(String name) {
        initSameStyleBeanDao();
        return samestyleBeanDao.queryBuilder().where(SamestyleBeanDao.Properties.ProductNames.eq(name)).list();
    }

    public static void insertSameStyleBean(SamestyleBean bean) {
        initSameStyleBeanDao();
        samestyleBeanDao.insert(bean);
    }

    public static void insertSameStyleUrlBean(ArrayList<SameSytleUrlBean> bean) {
        initSameStyleUrlBeanDao();
        sameSytleUrlBeanDao.insertInTx(bean);
    }


    public static void insertSameStyleTitleArrayBean(SameStyleTitleArrayBean bean) {
        initSameStyleTitleArrayBeanDao();
        sameStyleTitleArrayBeanDao.insert(bean);
    }


    public static boolean isUrlExist(String url, long id) {
        initSameStyleUrlBeanDao();
        List<SameSytleUrlBean> list = sameSytleUrlBeanDao.queryBuilder().where(SameSytleUrlBeanDao.Properties.SameStyleUrl.eq(url),SameSytleUrlBeanDao.Properties.ProductId.eq(id)).list();
        return list.size() > 0 ? true : false;
    }

    public static void getUrlList() {
        initSameStyleUrlBeanDao();
        List<SameSytleUrlBean> beanList = sameSytleUrlBeanDao.queryBuilder().list();
        for (int i = 0; i < beanList.size(); i++) {
            String sameStyleUrl = beanList.get(i).getSameStyleUrl();
            LogUtils.e(sameStyleUrl);
        }
    }



}
