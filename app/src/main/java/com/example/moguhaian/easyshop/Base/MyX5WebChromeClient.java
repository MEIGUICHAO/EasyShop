package com.example.moguhaian.easyshop.Base;

import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.ResUtil;
import com.example.moguhaian.easyshop.X5.X5WebView;
import com.example.moguhaian.easyshop.fragment.Ali1688Fragment;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class MyX5WebChromeClient extends WebChromeClient {

    LoadFinishListener listener;

//    public boolean isNeedListener() {
//        return needListener;
//    }
//
//    public void setNeedListener(boolean needListener) {
//        this.needListener = needListener;
//    }

    private boolean needListener = true;

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    public void setOnLoadFinishListener(LoadFinishListener onLoadFinishListener) {
        listener = onLoadFinishListener;
    }



    @Override
    public void onProgressChanged(final WebView view, int newProgress) {
        view.loadUrl("javascript:" + BaseApplication.getInjectJS());
        super.onProgressChanged(view, newProgress);
        if (newProgress == 100) {
            if (null != listener && needListener) {
                needListener = false;
                if (view instanceof X5WebView) {
                    if (listener instanceof Ali1688Fragment) {
                        if (((Ali1688Fragment) listener).clickPosition == R.string.upload_pic) {
                            LogUtils.e("upload_pic return");
                        } else {
                            LogUtils.e("onProgressChanged_100:"+ ResUtil.getS(((Ali1688Fragment) listener).clickPosition));
                            view.scrollTo(0, ((X5WebView) view).getScrollYRange());
                        }

                    }

                }
                BaseApplication.getmHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        listener.loadFinish(view, view.getUrl());
                    }
                });
            }
        } else {
            needListener = true;
        }
    }
}
