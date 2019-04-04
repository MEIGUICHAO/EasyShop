package com.example.moguhaian.easyshop.Utils;

import com.example.moguhaian.easyshop.Base.BaseApplication;
import com.example.moguhaian.easyshop.Bean.ResultBean;
import com.example.moguhaian.easyshop.Bean.SameStyleTitleArrayBean;
import com.example.moguhaian.easyshop.Bean.SameSytleUrlBean;
import com.example.moguhaian.easyshop.Bean.SamestyleBean;
import com.example.moguhaian.easyshop.Bean.TemSameUrlBean;
import com.example.moguhaian.easyshop.Bean.TemTitleBean;
import com.example.moguhaian.easyshop.ResultBeanDao;
import com.example.moguhaian.easyshop.SameStyleTitleArrayBeanDao;
import com.example.moguhaian.easyshop.SameSytleUrlBeanDao;
import com.example.moguhaian.easyshop.SamestyleBeanDao;
import com.example.moguhaian.easyshop.TemSameUrlBeanDao;
import com.example.moguhaian.easyshop.TemTitleBeanDao;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoUtils {

    private static SamestyleBeanDao samestyleBeanDao;
    private static SameSytleUrlBeanDao sameSytleUrlBeanDao;
    private static SameStyleTitleArrayBeanDao sameStyleTitleArrayBeanDao;
    private static TemSameUrlBeanDao temSameUrlBeanDao;
    private static TemTitleBeanDao temTitleBeanDao;
    private static ResultBeanDao resultBeanDao;


    public static void initResultBeanDao() {
        if (null == resultBeanDao) {
            resultBeanDao = BaseApplication.getInstances().getDaoSession().getResultBeanDao();
        }
    }

    public static void insertResultBean(ResultBean bean) {
        initResultBeanDao();
        resultBeanDao.insertInTx(bean);
    }

    public static boolean isSearchResultNameExit(String lvResult, String shopName) {
        initResultBeanDao();
        List<ResultBean> list = resultBeanDao.queryBuilder().where(ResultBeanDao.Properties.LvResult.eq(lvResult), ResultBeanDao.Properties.ShopName.eq(shopName)).list();
        return list.size() > 0 ? true : false;
    }

    public static List<ResultBean> getResultList(String lvResult, String shopName) {
        initResultBeanDao();
//        return resultBeanDao.queryBuilder().list();
        return resultBeanDao.queryBuilder().where(ResultBeanDao.Properties.LvResult.eq(lvResult), ResultBeanDao.Properties.ShopName.eq(shopName)).list();
    }



    public static void initTemSameUrlBeanDao() {
        if (null == temSameUrlBeanDao) {
            temSameUrlBeanDao = BaseApplication.getInstances().getDaoSession().getTemSameUrlBeanDao();
        }
    }

    public static void initTemTitleBeanDao() {
        if (null == temTitleBeanDao) {
            temTitleBeanDao = BaseApplication.getInstances().getDaoSession().getTemTitleBeanDao();
        }
    }

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

    public static void insertSameStyleUrlBean(SameSytleUrlBean bean) {
        initSameStyleUrlBeanDao();
        sameSytleUrlBeanDao.insertInTx(bean);
    }


    public static void insertTemSameUrlBean(TemSameUrlBean bean) {
        initTemSameUrlBeanDao();
        temSameUrlBeanDao.insertInTx(bean);
    }

    public static boolean isTemSameUrlExist(String url) {
        initTemSameUrlBeanDao();
        List<TemSameUrlBean> list = temSameUrlBeanDao.queryBuilder().where(TemSameUrlBeanDao.Properties.Url.eq(url)).list();
        return list.size() > 0 ? true : false;
    }


    public static void insertTemTitleBeanDao(TemTitleBean bean) {
        initTemSameUrlBeanDao();
        temTitleBeanDao.insertInTx(bean);
    }

    public static boolean isTemSameTitleExist(String title) {
        initTemTitleBeanDao();
        List<TemTitleBean> list1 = temTitleBeanDao.queryBuilder().list();
        List<TemTitleBean> list = temTitleBeanDao.queryBuilder().where(TemTitleBeanDao.Properties.Title.eq(title)).list();
        return list.size() > 0 ? true : false;
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

    public static ArrayList<String> getSameStyleUrlList() {
        initSameStyleUrlBeanDao();
        ArrayList<String> urlList = new ArrayList<>();
        List<SameSytleUrlBean> beanList = sameSytleUrlBeanDao.queryBuilder().list();
        for (int i = 0; i < beanList.size(); i++) {
            String sameStyleUrl = beanList.get(i).getSameStyleUrl();
            LogUtils.e(sameStyleUrl);
            urlList.add(sameStyleUrl);
        }
        return urlList;
    }


    public static List<SameSytleUrlBean> getSameStyleUrlListByName(String name) {
        List<SamestyleBean> list = getSameStyleBeanByProductName(name);
        Long id = list.get(0).getId();
        initSameStyleUrlBeanDao();
        List<SameSytleUrlBean> urlBeanList = sameSytleUrlBeanDao.queryBuilder().where(SameSytleUrlBeanDao.Properties.ProductId.eq(id)).list();
        return urlBeanList;
    }



}
