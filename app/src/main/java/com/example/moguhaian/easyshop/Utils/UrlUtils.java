package com.example.moguhaian.easyshop.Utils;

import com.example.moguhaian.easyshop.Base.Constants;

public class UrlUtils {
    public static String setQueryWord(String name) {
        String url = Constants.TaoBaoSearchUrl.replace(Constants.MY_INDEX, name).replace(Constants.SORT_TYPE, "renqi");
        return url;
    }
}
