package com.example.moguhaian.easyshop.Search;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Bean.SameStyleShopsBean;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SameStyleBiz extends BaseBiz {
    public ArrayList<String> getSameStyleUrl() {
        return sameStyleUrl;
    }

    public void setSameStyleUrl(ArrayList<String> sameStyleUrl) {
        this.sameStyleUrl = sameStyleUrl;
    }

    private ArrayList<String> sameStyleUrl;

    public void jsoupSameStyleList(final String shopsUrl, final JsoupParseListener listener) {

        singleThreadExecutor.execute(new Runnable() {


            @Override
            public void run() {
                String json = null;
                try {
                    json = TaoUtils.getJsoupJson(shopsUrl);
                    sameStyleUrl = TaoUtils.getSameStyleUrl(json);
                    listener.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFail(shopsUrl);
                }
            }
        });
    }

    public void jsoupSameStyleJson(final String url, final JsoupParseListener listener) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = TaoUtils.getJsoupJson(url);
                    String[] sameJson1 = json.split("recitem");
                    String[] sameJson2 = sameJson1[1].split(",\"style\"");
                    String jsonResult = sameJson2[0].replace("\":{\"status\"", "{\"status\"") + "}}";
                    LogUtils.e(jsonResult);
                    Gson gson = new Gson();
                    SameStyleShopsBean sameStyleShopsBean = gson.fromJson(jsonResult, SameStyleShopsBean.class);
                    List<SameStyleShopsBean.DataBean.ItemsBean> items = sameStyleShopsBean.getData().getItems();
                    if (items.size() > 20) {
                        String position5PayNums = items.get(5).getView_sales().replace("人付款", "");
                        if (Integer.parseInt(position5PayNums) > 5) {
                            for (int i = 0; i < items.size(); i++) {

                            }

                        }
                    }

                } catch (final Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(e.toString());
                        }
                    });
                }

            }
        });
    }



}
