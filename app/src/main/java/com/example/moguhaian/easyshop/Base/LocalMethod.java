package com.example.moguhaian.easyshop.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;

import com.example.moguhaian.easyshop.Bean.SameSytleUrlBean;
import com.example.moguhaian.easyshop.Bean.SamestyleBean;
import com.example.moguhaian.easyshop.Utils.CommonUtils;
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

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class LocalMethod {

    private final MyWebView mWebView;
    Activity mContext;
    LoalMethodListener listener;
    private AfterClickRunnable afterClickRunnable;
    private ArrayList<String> picSpaceUrlList = new ArrayList<>();
    private boolean recordAvailable = true;


    public ArrayList<String> getPicSpaceUrlList() {
        return picSpaceUrlList;
    }


    public void resetPicspaceList() {
        picSpaceUrlList.clear();
    }


    public ArrayList<String> getAliDetailDataList() {
        return aliDetailDataList;
    }

    private ArrayList<String> aliDetailDataList = new ArrayList<>();

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

    public void setRecordClickGoOn(boolean available) {
        recordAvailable = available;
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
                    picSpaceUrlList.add(picsArray[i] + "\n" + titlesArray[i]);
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
    public void get1688details(final String src, final String shopName, final String shopPrice, final String shopCount) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                aliDetailDataList.clear();
                //src地址、名字、价格、数量
                String[] srcArray = src.split("###");
                String[] shopNameArray = shopName.split("###");
                String[] shopPriceArray = shopPrice.split("###");
                String[] shopCountArray = shopCount.split("###");
                for (int i = 0; i < srcArray.length; i++) {
                    aliDetailDataList.add(srcArray[i] + "\n" + shopNameArray[i] + "\n" + shopPriceArray[i] + "\n" + shopCountArray[i]);
                }
                listener.afterGetJson("");
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                // “旋转”的拼音
                int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X, KeyEvent.KEYCODE_DEL};
                for (int i = 0; i < keyCodeArray.length; i++) {
                    try {
                        typeIn(keyCodeArray[i]);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                BaseApplication.getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        listener.inputFinish();
                    }
                });

            }
        }).start();

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


    public void typeIn(final int KeyCode) {
        try {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync(KeyCode);
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
    public void errorOccur() {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.errorOccur();
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
        if (!GreenDaoUtils.isUrlExist(url, samestyleBean.getId())) {
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


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void picSpaceInputClick() {


        LogUtils.e("picSpaceInputClick");

        if (judeClickRecordEmpty(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X, Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y, "PIC_SPACE_INPUT empty")) {
            return;
        }
        GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y)));

        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                CommonUtils.pasteToResult();
                BaseApplication.getmHandler().removeCallbacks(this);
                if (judeClickRecordEmpty(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X, Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y, "PIC_SPACE_INPUT empty")) {
                    return;
                }

                if (judeClickRecordEmpty(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_X, Constants.PIC_SPACE_SEARCH_CLICK_DOWN_Y, "PIC_SPACE_SEARCH empty"))
                    return;
                if (judeClickRecordEmpty(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X, Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y, "PIC_SPACE_SELECT empty"))
                    return;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        String str = CommonUtils.pasteToResult();
                        Instrumentation inst = new Instrumentation();
                        inst.sendStringSync(str);
                        LogUtils.e(str);

                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X))) {
                            if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_X))) {
                                GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SEARCH_CLICK_DOWN_Y)));
                                BaseApplication.getmHandler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        picSelectClick();
                                        BaseApplication.getmHandler().removeCallbacks(this);
                                    }
                                }, 1500);
                            }
                        }
                        Looper.loop();
                    }
                }).start();
            }
        }, 500);


    }

    private boolean judeClickRecordEmpty(String picSpaceInputClickDownX, String picSpaceInputClickDownY, String s) {

        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(picSpaceInputClickDownX)) || !TextUtils.isEmpty(SharedPreferencesUtils.getValue(picSpaceInputClickDownY))) {
            LogUtils.e(picSpaceInputClickDownX + ":" + SharedPreferencesUtils.getValue(picSpaceInputClickDownX));
            LogUtils.e(picSpaceInputClickDownY + ":" + SharedPreferencesUtils.getValue(picSpaceInputClickDownY));
        }
        if (!recordAvailable) {
            return true;
        }
        if (TextUtils.isEmpty(SharedPreferencesUtils.getValue(picSpaceInputClickDownX)) || TextUtils.isEmpty(SharedPreferencesUtils.getValue(picSpaceInputClickDownY))) {
            LogUtils.e("empty!!!!!" + picSpaceInputClickDownX);
            LogUtils.e("empty!!!!!" + picSpaceInputClickDownY);
            ToastUtils.showToast(s);
            return true;
        }
        return false;
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void generateMoblieDetail() {

        LogUtils.e("generateMoblieDetail");

        if (judeClickRecordEmpty(Constants.PUBLISH_MOBILE_DETAIL_X, Constants.PUBLISH_MOBILE_DETAIL_Y, "PUBLISH_MOBILE_DETAIL empty"))
            return;
        if (judeClickRecordEmpty(Constants.PUBLISH_MOBILE_COMFIR_X, Constants.PUBLISH_MOBILE_COMFIR_Y, "PUBLISH_MOBILE_COMFIR empty"))
            return;
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_DETAIL_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_DETAIL_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_DETAIL_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_DETAIL_Y)));
            LogUtils.e("PUBLISH_MOBILE_DETAIL_X:" + SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_DETAIL_X));
            LogUtils.e("PUBLISH_MOBILE_DETAIL_Y:" + SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_DETAIL_Y));
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_COMFIR_X))) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_COMFIR_Y))) {
                            LogUtils.e("PUBLISH_MOBILE_COMFIR_X:" + SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_COMFIR_X));
                            LogUtils.e("PUBLISH_MOBILE_COMFIR_Y:" + SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_COMFIR_Y));
                            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_COMFIR_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PUBLISH_MOBILE_COMFIR_Y)));
                            listener.afterClick();
                        }
                    }
                }
            }, 1000);
        }

    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void clickPublishTime(final String ymd, final String hmm) {


        LogUtils.e("clickPublishTime");
        LogUtils.e("ymd:" + ymd);
        LogUtils.e("hmm:" + hmm);

        if (judeClickRecordEmpty(Constants.TIME_CLICK_YMD_X, Constants.TIME_CLICK_YMD_Y, "TIME_CLICK_YMD empty"))
            return;
        if (judeClickRecordEmpty(Constants.TIME_CLICK_HMM_X, Constants.TIME_CLICK_HMM_Y, "TIME_CLICK_HMM empty"))
            return;
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_Y)));
            LogUtils.e("TIME_CLICK_YMD_X:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_X));
            LogUtils.e("TIME_CLICK_YMD_Y:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_Y));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Instrumentation instrumentation = new Instrumentation();
                    for (int i = 0; i < 15; i++) {
                        try {
                            typeIn(KeyEvent.KEYCODE_DEL);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    instrumentation.sendStringSync(ymd);
//                    BaseApplication.getmHandler().post(new )
                    clickPublishTimeComfir();
                    BaseApplication.getmHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            BaseApplication.getmHandler().removeCallbacks(this);
                            mWebView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"next-date-picker next-date-picker-medium next-date-picker-show-time\")"));
//                            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_Y)));
                            clickHMMTime(hmm);
                        }
                    }, 500);

                }
            }).start();

        }

    }

    private void clickHMMTime(final String hmm) {

        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                BaseApplication.getmHandler().removeCallbacks(this);
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X))) {
                    if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y))) {
                        LogUtils.e("TIME_CLICK_HMM_X:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X));
                        LogUtils.e("TIME_CLICK_HMM_Y:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y));
                        GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y)));
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Instrumentation instrumentation = new Instrumentation();
                        for (int i = 0; i < 15; i++) {
                            try {
                                typeIn(KeyEvent.KEYCODE_DEL);
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        instrumentation.sendStringSync(hmm);
                        clickPublishTimeComfir();
//                                    BaseApplication.getmHandler().post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            listener.inputFinish();
//                                        }
//                                    });
                    }
                }).start();

            }
        }, 1500);
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void clickPublishTimeComfir() {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_Y)));
            LogUtils.e("TIME_CLICK_COMFIR_X:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_X));
            LogUtils.e("TIME_CLICK_COMFIR_Y:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_Y));

        }

    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void picSpaceFirstClick() {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_FIRST_CLICK_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_FIRST_CLICK_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_FIRST_CLICK_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_FIRST_CLICK_Y)));
            LogUtils.e("PIC_SPACE_FIRST_CLICK_X:" + SharedPreferencesUtils.getValue(Constants.PIC_SPACE_FIRST_CLICK_X));
            LogUtils.e("PIC_SPACE_FIRST_CLICK_Y:" + SharedPreferencesUtils.getValue(Constants.PIC_SPACE_FIRST_CLICK_Y));
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"sele-button move\")"));
                }
            }, 1000);
        }

    }

    private void picSelectClick() {
        GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y)));
        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.afterGetJson("picSelectClick");
                BaseApplication.getmHandler().removeCallbacks(this);
            }
        }, 2500);
    }

    public void folderMoveClick() {

        LogUtils.e("folderMoveClick");
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.FOLDER_SELECT_CLICK_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.FOLDER_SELECT_CLICK_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.FOLDER_SELECT_CLICK_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.FOLDER_SELECT_CLICK_Y)));
            LogUtils.e("FOLDER_SELECT_CLICK_X:" + SharedPreferencesUtils.getValue(Constants.FOLDER_SELECT_CLICK_X));
            LogUtils.e("FOLDER_SELECT_CLICK_Y:" + SharedPreferencesUtils.getValue(Constants.FOLDER_SELECT_CLICK_Y));
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.FOLDER_COMFIR_CLICK_X))) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.FOLDER_COMFIR_CLICK_Y))) {
                            LogUtils.e("FOLDER_COMFIR_CLICK_X:" + SharedPreferencesUtils.getValue(Constants.FOLDER_COMFIR_CLICK_X));
                            LogUtils.e("FOLDER_COMFIR_CLICK_Y:" + SharedPreferencesUtils.getValue(Constants.FOLDER_COMFIR_CLICK_Y));
                            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.FOLDER_COMFIR_CLICK_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.FOLDER_COMFIR_CLICK_Y)));
                        }
                    }
                }
            }, 1000);
        }

    }

}
