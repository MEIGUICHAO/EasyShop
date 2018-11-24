package com.example.moguhaian.easyshop.Base;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.moguhaian.easyshop.Utils.LogUtils;

import static android.content.ContentValues.TAG;

public class LocalMethod {

    Context mContext;

    public LocalMethod(Context c) {
        this.mContext = c;
    }


    @JavascriptInterface
    public void JI_LOG(String content)
    {
        LogUtils.e( "JI_LOG: " + content);
    }


    @JavascriptInterface
    public void localGo() {

    }


    @JavascriptInterface
    public void JI_showToast(String content)
    {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void JsLog(String string) {
        LogUtils.e(string);
    }

}
