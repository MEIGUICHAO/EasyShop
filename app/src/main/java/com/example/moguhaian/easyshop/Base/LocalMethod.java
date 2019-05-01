package com.example.moguhaian.easyshop.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;

import com.example.moguhaian.easyshop.Bean.SameSytleUrlBean;
import com.example.moguhaian.easyshop.Bean.SamestyleBean;
import com.example.moguhaian.easyshop.Utils.GestureTouchUtils;
import com.example.moguhaian.easyshop.Utils.GreenDaoUtils;
import com.example.moguhaian.easyshop.Utils.JsUtils;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.listener.LoalMethodListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class LocalMethod {

    private final MyWebView mWebView;
    Activity mContext;
    LoalMethodListener listener;
    private AfterClickRunnable afterClickRunnable;
    private String[] srcArray;
    private ArrayList<String> picSpaceUrlList = new ArrayList<>();

    public ArrayList<String> getPicSpaceUrlList() {
        return picSpaceUrlList;
    }

    public ArrayList<String> getPicSpaceTitleList() {
        return picSpaceTitleList;
    }

    private ArrayList<String> picSpaceTitleList = new ArrayList<>();


    public void resetPicspaceList() {
        picSpaceUrlList.clear();
        picSpaceTitleList.clear();
    }

    public String[] getSrcArray() {
        return srcArray;
    }

    public String[] getShopNameArray() {
        return shopNameArray;
    }

    private String[] shopNameArray;

    public int getPagingNum() {
        return pagingNum;
    }


    private int pagingNum = 0;



    public LocalMethod(Activity c, MyWebView webView) {
        this.mContext = c;
        mWebView = webView;
    }

    public void setLocalMethodListener(LoalMethodListener localMethodListener) {
        listener = localMethodListener;
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void afterClick() {
        afterClickRunnable = new AfterClickRunnable();
        BaseApplication.getmHandler().postDelayed(afterClickRunnable, 3000);
    }

    class AfterClickRunnable implements Runnable {
        @Override
        public void run() {
            if (null != listener) {
                LogUtils.e("afterClick:run");
                listener.afterClick();
                BaseApplication.getmHandler().removeCallbacks(this);
            }
        }
    }



    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void addPicSpaceResult(final String pics, final String titles) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                String[] picsArray = pics.split("\n");
                String[] titlesArray = titles.split("\n");
                for (int i = 0; i < picsArray.length; i++) {
                    picSpaceUrlList.add(picsArray[i]);
                    picSpaceTitleList.add(titlesArray[i]);
                }
            }
        });
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void next() {
        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BaseApplication.getmHandler().removeCallbacks(this);
                mWebView.loadUrl(JsUtils.addJsMethod("getSrcByClassName()"));
            }
        }, 1000);
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void get1688details(final String src,final String shopName) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                srcArray = src.split("###");
                shopNameArray = shopName.split("###");
                LogUtils.e("src: " + src);
                LogUtils.e("shopName: " + shopName);
            }
        });
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void JI_LOG(final String content) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("JI_LOG: " + content);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void setPagingNum(String num) {
        pagingNum = Integer.parseInt(num);
    }




    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void showKeyboardB4Input() {
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    Thread.sleep( 1000 );
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                // “旋转”的拼音
                int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X,KeyEvent.KEYCODE_DEL};
                for (int i = 0; i < keyCodeArray.length; i++) {
                    try {
                        typeIn(keyCodeArray[i]);
                        Thread.sleep( 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start( );

//        BaseApplication.getmHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                BaseApplication.getmHandler().removeCallbacks(this);
//                int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X, KeyEvent.KEYCODE_DEL};
//                for (int k = 0; k < keyCodeArray.length; k++) {
//                    typeIn(keyCodeArray[k]);
//                    try {
//                        Thread.sleep( 400 );
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, 1500);
    }


    public void typeIn( final int KeyCode ){
        try {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync( KeyCode );
        } catch (Exception e) {
            Log.e("Exception：", e.toString());
        }
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void getJsonData(final String content) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.afterGetJson(content);
            }
        });

    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void slideTouch() {

        try {
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!mWebView.isNeedDraw()) {
                            GestureTouchUtils.simulateScroll(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_DOWN_Y)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_UP_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.SLIDE_UP_Y)), 500, GestureTouchUtils.HIGH);
                        }
                    } catch (Exception e) {
                        ToastUtils.showToast("小二来了！！！");
                    }
                    BaseApplication.getmHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (!mWebView.isNeedDraw()) {
                                    GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.CLICK_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.CLICK_DOWN_Y)));
                                }
                            } catch (Exception e) {
                                ToastUtils.showToast("小二来了！！！");
                            }
                            BaseApplication.getmHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mWebView.loadUrl(JsUtils.addJsMethod("getDocument()"));
                                }
                            }, 3000);

                        }
                    }, 3000);
                }
            }, 3000);

        } catch (Exception e) {
            ToastUtils.showToast("小二来了！！！");
        }


    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void insertSameStyleUrls(String name, String[] urls) {

        List<SamestyleBean> list = GreenDaoUtils.getSameStyleBeanByProductName(name);
        if (list.size() < 1) {
            SamestyleBean bean = new SamestyleBean();
            bean.setProductNames(name);
            GreenDaoUtils.insertSameStyleBean(bean);
        }
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i].split("&nid=")[0] + Constants.SaleDescSort;
            insertSameStyleUrlBean(name, url);
        }
        LogUtils.e("find complete");
//
    }

    private void insertSameStyleUrlBean(String name, String url) {
        SamestyleBean samestyleBean = GreenDaoUtils.getSameStyleBeanByProductName(name).get(0);
        if (!GreenDaoUtils.isUrlExist(url,samestyleBean.getId())) {
            SameSytleUrlBean sameSytleUrlBean = new SameSytleUrlBean();
            sameSytleUrlBean.setProductId(samestyleBean.getId());
            sameSytleUrlBean.setSameStyleUrl(url);
            GreenDaoUtils.insertSameStyleUrlBean(sameSytleUrlBean);
        }
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void JsLog(String string) {
        LogUtils.e(string);
    }

}
