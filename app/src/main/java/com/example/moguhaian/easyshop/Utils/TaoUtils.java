package com.example.moguhaian.easyshop.Utils;

import android.text.TextUtils;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Bean.TBSameStyleBean;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaoUtils {


    public static void getSameStyleInfoBean(String json) {
        Gson gson = new Gson();
        TBSameStyleBean tbSameStyleBean = gson.fromJson(getSameStyleInfoJson(json), TBSameStyleBean.class);
        List<TBSameStyleBean.ModsBean.RecitemBean.DataBeanXX.ItemsBean> items = tbSameStyleBean.getMods().getRecitem().getData().getItems();
        for (int i = 0; i < items.size(); i++) {
            LogUtils.e(items.get(i).getTitle());
        }
    }

    private static String getSameStyleInfoJson(String json) {
        String mJson = "";
        String regex = "g_page_config =(.*?);";
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            mJson = matcher.group();
        }
        return mJson.replace("g_page_config =", "").replace(";", "");
    }

    public static void getNameSplitResult(String json) {
        ArrayList<String> list = new ArrayList<>();

        json = json.replace("\\", "");
        String regex = "classu003dHu003e(.*?)u003c";
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            list.add(matcher.group().replace("classu003dHu003e", "").replace("u003c",""));
            LogUtils.e(matcher.group());
        }
        ArrayList<String> single = getSingle(list);
        for (int i = 0; i < single.size(); i++) {
            single.get(i);
            LogUtils.e(single.get(i));
        }
    }

    public static ArrayList<String> getSameStyleUrl(String json) {

        ArrayList<String> list = new ArrayList<>();

        json = json.replace("\\", "");
        json = json.replace("\"", "");
        json = json.replace("{", "");
        json = json.replace("}", "");
        String regex = "samestyle:url:(.*?),similar";
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            if (!"samestyle:url:,similar".equals(matcher.group())) {
                list.add("https://s.taobao.com" + matcher.group().replace("samestyle:url:", "").replace(",similar", "").replace("u003d", "=").replace("u0026", "&") + "&sort=sale-desc");
                LogUtils.e(matcher.group());
            }
        }
        ArrayList<String> single = getSingle(list);
        for (int i = 0; i < single.size(); i++) {
            single.get(i);
            LogUtils.e(single.get(i));
        }
        return list;
    }

    public static String getJsoupJson(final WebView webView, final String url, String ip) throws IOException {

        String json = "";
        String[] ip_port = ip.split("/");
        Document document = Jsoup.connect(url).proxy(ip_port[0], Integer.parseInt(ip_port[1])).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
//        Document document = Jsoup.connect(url).cookie("Cookie", SharedPreferencesUtils.getValue(Constants.Cookies)).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
//        Document document = Jsoup.connect(url).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
//            Document document = Jsoup.connect(url).cookie("Cookie", "t=09739d8b9ea2481146e732e9f3c29613; cookie2=18ebce4230b2907136b111c94bb425f0; v=0; _tb_token_=e7a48e5e3fefe").userAgent(Constants.UserAgentString).ignoreContentType(true).get();
        Elements script = document.getElementsByTag("script");
        LogUtils.e("document:" + document.toString());
        for (Element ele : script) {
            if (ele.data().contains("g_page_config")) {
                json = ele.data();
            }
        }

        if (json.contains("亲，小二正忙，滑动一下马上回来") || TextUtils.isEmpty(json)) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(url);
                }
            });
            ToastUtils.showToast("小二来了！！！");
        }
        return json;
    }

    public static void getCookieFromWv(String json) {

        String cookie = " v=0";
        String regex1 = "; t=(.*?); ";
        String regex2 = "; cookie2(.*?); ";
        String regex3 = "; _tb_token_(.*?); ";
        String[] regexs = {regex1, regex2, regex3};
        for (int i = 0; i < regexs.length; i++) {
            Pattern pattern = Pattern.compile (regexs[i]);
            Matcher matcher = pattern.matcher(json);
            while (matcher.find()) {
                cookie = cookie + matcher.group();
                LogUtils.e(matcher.group());
            }
        }
        cookie = cookie.replace("; ;", ";");
        LogUtils.e(cookie);
        SharedPreferencesUtils.putValue(Constants.Cookies, json);
    }


    private static ArrayList<String> getSingle(ArrayList list) {
        ArrayList tempList = new ArrayList();                    //1,创建新集合
        Iterator it = list.iterator();                            //2,根据传入的集合(老集合)获取迭代器

        while (it.hasNext()) {                                    //3,遍历老集合
            Object obj = it.next();                                //记录住每一个元素
            if (!tempList.contains(obj)) {                        //如果新集合中不包含老集合中的元素
                tempList.add(obj);                                //将该元素添加
            }
        }

        return tempList;
    }
}
