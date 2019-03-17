package com.example.moguhaian.easyshop.Search;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;

import java.util.ArrayList;

public class SameStyleBiz extends BaseBiz {

    public void jsoupSameStyleList(final String shopsUrl, JsoupParseListener listener) {

        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = TaoUtils.getJsoupJson(shopsUrl);
                ArrayList<String> sameStyleUrl = TaoUtils.getSameStyleUrl(json);
            }
        });
    }

    public void jsoupSameStyleJson(final String url) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = TaoUtils.getJsoupJson(url);
                String[] sameJson1 = json.split("recitem");
                String[] sameJson2 = sameJson1[1].split(",\"style\"");
                String jsonResult = sameJson2[0].replace("\":{\"status\"", "{\"status\"")+"}}";
                LogUtils.e(jsonResult);

            }
        });
    }



}
