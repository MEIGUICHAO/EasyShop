package com.example.moguhaian.easyshop.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupUtils {

    public static void parseForm(String url,String tableName) {
//        ui-table ui-table-simple
        Document document= null;
        try {
            document = Jsoup.connect(url).get();
            Elements allElements = document.getAllElements();
            for (int i = 0; i < allElements.size(); i++) {
                String nodeName = allElements.get(i).nodeName();
                String className = allElements.get(i).className();
                String tagName = allElements.get(i).tagName();
                String data = allElements.get(i).data();
                LogUtils.e(i+"~~~~~~~~~~~~~~~~~~~~~~"+"nodeName:" + nodeName + ",className:" + className + ",tagName:" + tagName + ",data:" + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


}

}
