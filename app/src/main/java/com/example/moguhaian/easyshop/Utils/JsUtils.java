package com.example.moguhaian.easyshop.Utils;

public class JsUtils {


    public static String addJsMethod(String jsMethod) {

        return buildJsMethod(initMethod(jsMethod));

    }


    /**
     * 注入需自动执行的JS代码
     */
    protected static String initMethod(String code) {
        return "function doAutoTest() { " + code + "}";
    }


    /**
     * 组装整个JS代码
     */
    protected static String buildJsMethod(String logicStr) {
        String js = "var newscript = document.createElement(\"script\");" + "newscript.text = window.onload=doAutoTest();" + logicStr + "document.body.appendChild(newscript);";
        return js;
    }
}
