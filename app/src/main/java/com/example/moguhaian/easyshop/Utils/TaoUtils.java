package com.example.moguhaian.easyshop.Utils;

import android.util.Log;

import com.example.moguhaian.easyshop.Bean.TBSameStyleBean;
import com.google.gson.Gson;

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
