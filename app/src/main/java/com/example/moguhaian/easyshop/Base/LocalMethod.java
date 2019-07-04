package com.example.moguhaian.easyshop.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class LocalMethod {

    private final MyWebView mWebView;
    Activity mContext;
    LoalMethodListener listener;
    private AfterClickRunnable afterClickRunnable;
    private ArrayList<String> picSpaceUrlList = new ArrayList<>();
    private boolean recordAvailable = true;

    public double getAliLimitPrice() {
        return AliLimitPrice;
    }

    public void setAliLimitPrice(double aliLimitPrice) {
        AliLimitPrice = aliLimitPrice;
    }

    private double AliLimitPrice;


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
    public ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


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
                mWebView.loadUrl(JsUtils.addJsMethod("getSrcByClassName(\"" + false + "\")"));
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
                LogUtils.e("src:" + src);
                LogUtils.e("shopName:" + shopName);
                LogUtils.e("shopPrice:" + shopPrice);
                LogUtils.e("shopCount:" + shopCount);
                //src地址、名字、价格、数量
//                String srcTemp = src.replace("32x32.jpg", "160x160.jpg");
//                LogUtils.e("get1688details src:\n" + srcTemp);
                String[] srcArray = src.split("###");
                String[] shopNameArray = shopName.split("###");
                String[] shopPriceArray = shopPrice.split("###");
                String[] shopCountArray = shopCount.split("###");
                for (int i = 0; i < srcArray.length; i++) {
                    aliDetailDataList.add(srcArray[i] + "\n" + shopNameArray[i] + "\n" + shopPriceArray[i] + "\n" + shopCountArray[i]);
                }
                listener.afterClick();
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
        LogUtils.e("showKeyboardB4Input");

        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }

                // “旋转”的拼音
                inputAvaliable();
                BaseApplication.getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        listener.inputFinish();
                    }
                });
            }
        });

    }

    private void inputAvaliable() {
        int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X, KeyEvent.KEYCODE_DEL};
        for (int i = 0; i < keyCodeArray.length; i++) {
            try {
                typeIn(keyCodeArray[i]);
                LogUtils.e("KeyEvent：" + keyCodeArray[i]);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void clearTitle(final String inputVale) {
        LogUtils.e("clearTitle");

        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }

                // “旋转”的拼音
//                int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_DEL};
                for (int i = 0; i < 60; i++) {
                    try {
                        typeIn(KeyEvent.KEYCODE_DEL);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                BaseApplication.getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
//                        listener.inputFinish();
                        mWebView.loadUrl(JsUtils.addJsMethod("setInputValue(\"next-input next-input-single next-input-medium fusion-input\"" + ",\"" + (inputVale) + "\")"));
                        BaseApplication.getmHandler().removeCallbacks(this);
                    }
                });
            }
        });

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
    public void inputContent(final String inputvalue) {
        LogUtils.e("inputContent:" + inputvalue);
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {

                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Instrumentation instrumentation = new Instrumentation();
                        String[] split = inputvalue.split("###");
                        for (int i = 0; i < split.length; i++) {
                            instrumentation.sendStringSync(split[i]);
                            instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
                        }
                        BaseApplication.getmHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listener.inputFinish();
                                BaseApplication.getmHandler().removeCallbacks(this);
                            }
                        }, 500);
                    }
                });
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
    public void getLimitPrice(final String limitPrice) {
        BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("limitPrice:" + limitPrice);
                if (!TextUtils.isEmpty(limitPrice)) {
                    AliLimitPrice = Double.parseDouble(limitPrice);
                } else {
                    AliLimitPrice = -1;
                }
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
    public void picSpaceInputClick(final String pic) {


        LogUtils.e("picSpaceInputClick");

        if (judeClickRecordEmpty(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X, Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y, "PIC_SPACE_INPUT empty")) {
            return;
        }
        GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y)));
//        LogUtils.e("图片输入框点击");
//
//        if (judeClickRecordEmpty(Constants.PIC_SPACE_INPUT_CLICK_DOWN_X, Constants.PIC_SPACE_INPUT_CLICK_DOWN_Y, "PIC_SPACE_INPUT empty")) {
//            return;
//        }
        if (judeClickRecordEmpty(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X, Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y, "PIC_SPACE_SELECT empty"))
            return;
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {

                LogUtils.e("picSpaceInputClick:"+pic);
                Instrumentation inst = new Instrumentation();
                inst.sendStringSync(pic);
                LogUtils.e(pic);
                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
                hideKeybord();

                BaseApplication.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("base1", "11111111111111");

//                        Looper.prepare();
//                        Looper.loop();

                        BaseApplication.getmHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("base1", "2222222222");
                                GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y)));

                                BaseApplication.getmHandler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Log.e("base1", "33333333333333");
                                        listener.afterGetJson("picSelectClick");
                                        BaseApplication.getmHandler().removeCallbacks(this);
                                    }
                                }, 1000);
                                BaseApplication.getmHandler().removeCallbacks(this);
                            }
                        }, 1000);
                        BaseApplication.getmHandler().removeCallbacks(this);
                    }
                }, 1000);

//                BaseApplication.getmHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        picSelectClick();
//
//                        BaseApplication.getmHandler().removeCallbacks(this);
//                    }
//                }, 1000);

            }
        });




    }

    public void hideKeybord() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mWebView.getWindowToken(), 0);
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
        final Instrumentation instrumentation = new Instrumentation();

        LogUtils.e("clickPublishTime:" + ymd + hmm);

        if (judeClickRecordEmpty(Constants.TIME_CLICK_YMD_X, Constants.TIME_CLICK_YMD_Y, "TIME_CLICK_YMD empty"))
            return;
        if (judeClickRecordEmpty(Constants.TIME_CLICK_HMM_X, Constants.TIME_CLICK_HMM_Y, "TIME_CLICK_HMM empty"))
            return;
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_Y)));
            hideKeybord();
            LogUtils.e("年月日点击");
            LogUtils.e("TIME_CLICK_YMD_X:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_X));
            LogUtils.e("TIME_CLICK_YMD_Y:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_YMD_Y));
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < 15; i++) {
                        try {
                            typeIn(KeyEvent.KEYCODE_DEL);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instrumentation.sendStringSync(ymd);
                    inputAvaliable();
                    hideKeybord();
                    listener.inputFinish();


//                    BaseApplication.getmHandler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X))) {
//                                if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y))) {
//                                    LogUtils.e("TIME_CLICK_HMM_X:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X));
//                                    LogUtils.e("TIME_CLICK_HMM_Y:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y));
//                                    GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y)));
//                                    hideKeybord();
//                                    LogUtils.e("时分秒点击");
//                                }
//                            }
//                            singleThreadExecutor.execute(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    for (int i = 0; i < 15; i++) {
//                                        try {
//                                            typeIn(KeyEvent.KEYCODE_DEL);
//                                            Thread.sleep(100);
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                    instrumentation.sendStringSync(hmm);
//                                    inputAvaliable();
//                                    hideKeybord();
//                                    BaseApplication.getmHandler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            listener.inputFinish();
//                                            BaseApplication.getmHandler().removeCallbacks(this);
//                                        }
//                                    }, 2500);
//                                }
//                            });
//
//                            BaseApplication.getmHandler().removeCallbacks(this);
//
//                        }
//                    }, 5000);


                }
            });

        }

    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void clickPublishTimeHmm(final String ymd, final String hmm) {
        final Instrumentation instrumentation = new Instrumentation();

        LogUtils.e("clickPublishTime:" + ymd + hmm);

        if (judeClickRecordEmpty(Constants.TIME_CLICK_HMM_X, Constants.TIME_CLICK_HMM_Y, "TIME_CLICK_HMM empty"))
            return;
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y)));
            hideKeybord();
            LogUtils.e("时分秒点击");
            LogUtils.e("TIME_CLICK_HMM_X:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_X));
            LogUtils.e("TIME_CLICK_HMM_Y:" + SharedPreferencesUtils.getValue(Constants.TIME_CLICK_HMM_Y));
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < 15; i++) {
                        try {
                            typeIn(KeyEvent.KEYCODE_DEL);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instrumentation.sendStringSync(hmm);
                    inputAvaliable();
                    hideKeybord();
                    listener.inputFinish();


                }
            });

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
                singleThreadExecutor.execute(new Runnable() {
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
                    }
                });

            }
        }, 1500);
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void clickPublishTimeComfir() {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_X)) && !TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_Y))) {
            GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.TIME_CLICK_COMFIR_Y)));
            listener.afterClick();
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
            threadSleep();
            BaseApplication.getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl(JsUtils.addJsMethod("clickElementsByClassName(\"sele-button move\")"));
                    BaseApplication.getmHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            threadSleep();
                            listener.afterClick();
                            BaseApplication.getmHandler().removeCallbacks(this);
                        }
                    }, 1000);
                    BaseApplication.getmHandler().removeCallbacks(this);
                }
            }, 1000);
        }

    }

    private void threadSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void picSelectClick() {
        GestureTouchUtils.simulateClick(mWebView, (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_X)), (int) Float.parseFloat(SharedPreferencesUtils.getValue(Constants.PIC_SPACE_SELECT_CLICK_DOWN_Y)));
        LogUtils.e("图片选择点击");
        threadSleep();
        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.afterGetJson("picSelectClick");
                BaseApplication.getmHandler().removeCallbacks(this);
            }
        }, 1000);
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
                            BaseApplication.getmHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listener.afterClick();
                                    BaseApplication.getmHandler().removeCallbacks(this);
                                }
                            }, 2000);
                            BaseApplication.getmHandler().removeCallbacks(this);

                        }
                    }
                }
            }, 1000);
        }

    }

}
