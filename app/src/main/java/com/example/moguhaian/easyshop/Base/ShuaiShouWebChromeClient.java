package com.example.moguhaian.easyshop.Base;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.R;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.ResUtil;
import com.example.moguhaian.easyshop.fragment.Ali1688Fragment;
import com.example.moguhaian.easyshop.listener.LoadFinishListener;
import com.example.moguhaian.easyshop.weidge.MyWebView;

public class ShuaiShouWebChromeClient extends WebChromeClient {

    LoadFinishListener listener;


    public void setOnLoadFinishListener(LoadFinishListener onLoadFinishListener) {
        listener = onLoadFinishListener;
    }

//    public boolean isNeedListener() {
//        return needListener;
//    }
//
//    public void setNeedListener(boolean needListener) {
//        this.needListener = needListener;
//    }

    private boolean needListener = true;
    /**
     * 处理alert弹出框
     */
    @Override
    public boolean onJsAlert(WebView view,String url,
                             String message,JsResult result) {
        //  mReusultText.setText("Alert:"+message);
			    //对alert的简单封装
				new AlertDialog.Builder((Activity) listener).
					setTitle("Alert").setMessage(message).setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						   //TODO
					   }
				}).create().show();
        result.confirm();
        return true;
    }

    /**
     * 处理confirm弹出框
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message,
                               JsResult result) {
        //对confirm的简单封装
//        new AlertDialog.Builder((Activity) listener).
//                setTitle("Confirm").setMessage(message).setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        //TODO
//                    }
//                }).create().show();
        result.confirm();
        return true;
        //如果采用下面的代码会另外再弹出个消息框，目前不知道原理
        //return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 处理prompt弹出框
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, JsPromptResult result) {
        result.confirm();
        return true;
//        result.confirm();
//        return super.onJsPrompt(view, url, message, message, result);
    }


//
//
//    @Override
//    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//        LogUtils.e("onJsConfirm");
//        result.confirm();
//        return true;
////        return super.onJsConfirm(view, url, message, result);
//    }
//
//    @Override
//    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//        LogUtils.e("onJsPrompt");
//        return super.onJsPrompt(view, url, message, defaultValue, result);
//    }

    @Override
    public void onProgressChanged(final WebView view, int newProgress) {
        view.loadUrl("javascript:" + BaseApplication.getInjectJS());
        super.onProgressChanged(view, newProgress);
        LogUtils.e("newProgress:" + newProgress);
        if ((newProgress == 100 && !view.getUrl().contains("detail.1688.com")) || (newProgress == 90 && view.getUrl().contains("detail.1688.com"))) {
            if (null != listener && needListener) {
                needListener = false;
                if (view instanceof MyWebView) {
                    if (listener instanceof Ali1688Fragment) {
                        if (((Ali1688Fragment) listener).clickPosition == R.string.upload_pic) {
                            LogUtils.e("upload_pic return");
                        } else {
                            LogUtils.e("onProgressChanged_100:" + ResUtil.getS(((Ali1688Fragment) listener).clickPosition));
                            view.scrollTo(0, ((MyWebView) view).getScrollYRange());
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
