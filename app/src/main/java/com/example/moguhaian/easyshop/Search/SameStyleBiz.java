package com.example.moguhaian.easyshop.Search;

import android.text.TextUtils;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Bean.SameStyleShopsBean;
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

    public ArrayList<String> getSameStyleUrl() {
        return sameStyleUrl;
    }

    public void setSameStyleUrl(ArrayList<String> sameStyleUrl) {
        this.sameStyleUrl = sameStyleUrl;
    }

    private ArrayList<String> sameStyleUrl = new ArrayList<>();

    public void jsoupSameStyleList(final MyWebView webView, final String shopsUrl, final String ip, final JsoupParseListener listener) {

        singleThreadExecutor.execute(new Runnable() {


            @Override
            public void run() {
                String json = null;
                try {
                    json = TaoUtils.getJsoupJson(webView,shopsUrl,ip);
                    if (TextUtils.isEmpty(json)) {
                        listener.onFail(shopsUrl);
                        return;
                    }
                    sameStyleUrl = TaoUtils.getSameStyleUrl(json);
                    listener.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFail(shopsUrl);
                }
            }
        });
    }

    public void jsoupSameStyleJson(final MyWebView webView, final String url, final JsoupParseListener listener) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = TaoUtils.getJsoupJson(webView,url, "");
                    String[] sameJson1 = json.split("recitem");
                    String[] sameJson2 = sameJson1[1].split(",\"style\"");
                    String jsonResult = sameJson2[0].replace("\":{\"status\"", "{\"status\"") + "}}";
                    if (TextUtils.isEmpty(jsonResult)) {
                        listener.onFail(url);
                        return;
                    }
//                    LogUtils.e(jsonResult);
                    Gson gson = new Gson();
                    SameStyleShopsBean sameStyleShopsBean = gson.fromJson(jsonResult, SameStyleShopsBean.class);
                    List<SameStyleShopsBean.DataBean.ItemsBean> items = sameStyleShopsBean.getData().getItems();
                    if (items.size() > 20) {
                        String position5PayNums = items.get(7).getView_sales().replace("人付款", "");
                        if (Integer.parseInt(position5PayNums) > 5) {
                            String minUrl = "";
                            String maxUrl = "";
                            String resultUrl = "";
                            double minPrice = 10000;
                            double maxPrice = 0;
                            for (int i = 0; i < items.size(); i++) {
                                int positionPayNums = Integer.parseInt(items.get(i).getView_sales().replace("人付款", ""));
                                int commentCount = Integer.parseInt(items.get(i).getComment_count());
                                double viewPrice = Double.parseDouble(items.get(i).getView_price());
                                if (positionPayNums > 10 && commentCount > 2) {
                                    if (minPrice > viewPrice) {
                                        minPrice = viewPrice;
                                        minUrl = "https:" + items.get(i).getDetail_url();
                                    }
                                    if (maxPrice < viewPrice) {
                                        maxPrice = viewPrice;
                                        maxUrl = "https:" + items.get(i).getDetail_url();
                                    }

                                }

                            }
                            if (minPrice * 1.3 < maxPrice) {
                                resultUrl = minUrl;
                            }
                            LogUtils.e("resultUrl:" + resultUrl + "\n" + "maxUrl:" + maxUrl);
                        }
                    }
                    LogUtils.e("before_sleep:");
                    Thread.sleep(sleepTime);
                    LogUtils.e("after_sleep:");
                    listener.complete();

                } catch (final Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(e.toString());
                        }
                    });
                    listener.onFail(url);
                }

            }
        });
    }



}
