package com.example.moguhaian.easyshop.Utils;

import android.text.TextUtils;
import android.webkit.WebView;

import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Bean.SameStyleShopsBean;
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

    public static ArrayList<String> getNameSplitResult(String json) {
        ArrayList<String> list = new ArrayList<>();

        json = json.replace("\\", "");
        json = json.replace("\"", "");
        json = json.replace("/", "");
        json = json.replace("[", "");
        json = json.replace("]", "");
        json = json.replace("{", "");
        json = json.replace("\"", "");
//        String[] regex = {"classu003dHu003e", "u003cspanu003e", "u003cspanu003e","text:","rsKeywords"};
//        String[] regex2 = {"u003c", "u003cspan classu003dHu003e", ",raw_title:", ",isHighlight",",tagList"};
        String[] regex = {"text:","rsKeywords","classu003dHu003e", "u003cspanu003e", "u003cspanu003e"};
        String[] regex2 = {",isHighlight", ",tagList","u003c", "u003cspan classu003dHu003e", ",raw_title:"};
        for (int i = 0; i < regex.length; i++) {
            String result = regexMatcher(json, regex[i], regex2[i]);
            String[] resultSplit = result.split("@@@###");
            for (int j = 0; j < resultSplit.length; j++) {
                if (resultSplit[j].length() < 500) {
                    list.add(resultSplit[j]);
                }
            }
        }
        ArrayList<String> single = getSingle(list);
        return single;
//        for (int i = 0; i < single.size(); i++) {
//            single.get(i);
//            if (single.get(i).length() < 500) {
//                LogUtils.e("getNameSplitResult" + i + ":" + single.get(i) + "\nlength:" + single.get(i).length());
//            }
//        }
    }

    private static String regexMatcher(String json, String regex, String regex2) {
        String result = "";
//        Pattern basePattern = Pattern.compile("icon:title(.*?)totalRate");
//        Matcher baseMatcher = basePattern.matcher(json);
//        while (baseMatcher.find()) {
//        }
        Pattern pattern = Pattern.compile(regex + "(.*?)" + regex2);
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            String replace = matcher.group().replace(regex, "").replace(regex2, "");
//            LogUtils.e(matcher.group());
            if (!result.contains(replace) && !result.contains("raw_title") && !result.contains("raw_title") && result.length() < 500) {
                replace = replace.replace("u003cspan classu003dHu003e", "@@@###");
                replace = replace.replace("u003cspanu003e", "@@@###");
                if (regex.equals("rsKeywords")) {
                    replace = replace.replace(",", "@@@###");
                }
                result = TextUtils.isEmpty(result) ? replace : result + "@@@###" + replace;
            }
        }

        result = result.replace("@@@###@@@###", "@@@###");
        return result;
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
                String url = "https://s.taobao.com" + matcher.group().replace("samestyle:url:", "").replace(",similar", "").replace("u003d", "=").replace("u0026", "&");
                try {
                    String[] urlSplit = url.split("&nid");
                    list.add(urlSplit[0] + "&sort=sale-desc");
                } catch (Exception e) {
                    LogUtils.e("urlsplit_Exception:" + e.toString());
                }
            }
        }
        ArrayList<String> single = getSingle(list);
//        for (int i = 0; i < single.size(); i++) {
//            single.get(i);
//            LogUtils.e(single.get(i));
//        }
        return single;
    }

    public static String getJsoupJson(final WebView webView, final String url, String ip) throws IOException {

        String json = "";
        String[] ip_port = ip.split("/");
        Document document = Jsoup.connect(url).proxy(ip_port[0], Integer.parseInt(ip_port[1])).userAgent(Constants.UserAgentString).ignoreContentType(true).header("referer", "https://www.taobao.com/").get();
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


    public static ArrayList<String> getSingle(ArrayList list) {
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


    public static String getInitShop(String json) {
        String resultUrl = "";
        if (!TextUtils.isEmpty(json)) {
            String[] sameJson1 = json.split("recitem");
            String[] sameJson2 = sameJson1[1].split(",\"style\"");
            String jsonResult = sameJson2[0].replace("\":{\"status\"", "{\"status\"") + "}}";
            Gson gson = new Gson();
            SameStyleShopsBean sameStyleShopsBean = gson.fromJson(jsonResult, SameStyleShopsBean.class);
            List<SameStyleShopsBean.DataBean.ItemsBean> items = sameStyleShopsBean.getData().getItems();
            if (items.size() > 20) {
                String position5PayNums = items.get(7).getView_sales().replace("人付款", "");
                if (Integer.parseInt(position5PayNums) > 5) {
                    String minUrl = "";
                    String maxUrl = "";
                    String shopPhoto = "";
                    String shoptitle = "";
                    double minPrice = 10000;
                    double maxPrice = 0;
                    for (int i = 0; i < items.size(); i++) {
                        int positionPayNums = Integer.parseInt(items.get(i).getView_sales().replace("人付款", ""));
                        int commentCount = Integer.parseInt(items.get(i).getComment_count());
                        double viewPrice = Double.parseDouble(items.get(i).getView_price());
                        if (positionPayNums > 10 && commentCount > 2) {
                            if (minPrice > viewPrice) {
                                minPrice = viewPrice;
                                minUrl = "https:" + items.get(i).getDetail_url();
                                shopPhoto = items.get(i).getPic_url();
                                shoptitle = items.get(i).getTitle();
                            }
                            if (maxPrice < viewPrice) {
                                maxPrice = viewPrice;
                                maxUrl = "https:" + items.get(i).getDetail_url();
                            }

                        }

                    }
                    if (minPrice * 1.5 < maxPrice) {
                        if (!TextUtils.isEmpty(minUrl)) {
                            resultUrl = minUrl + "###" + shopPhoto + "###" + shoptitle;
                        }
                    }
                    LogUtils.e("resultUrl:" + resultUrl + "\n" + "maxUrl:" + maxUrl);
                }
            }
        }
        return resultUrl;
    }


    public static int[] getRandom(int min, int max, int n){
        if (n > max) {
            n = max - 1;
        }
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }




    public static float levenshtein(String str1,String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        //计算相似度
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        return similarity;
    }


    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

}
