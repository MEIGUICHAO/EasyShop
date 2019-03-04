package com.example.moguhaian.easyshop.biz;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Utils.JsUtils;

public class SelectionBiz extends BaseBiz {

    public void dropDown() {
//        widget-multi-dropdown
        JsUtils.addJsMethod(" \"findElementsByClassName(\" + \"multi-dropdown J_MutliSelect\" +\")\"");

    }
}
