package com.example.moguhaian.easyshop.Base;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class LocalMethod {

    Context mContext;

    public LocalMethod(Context c) {
        this.mContext = c;
    }

    @JavascriptInterface
    public void localGo() {

    }

}
