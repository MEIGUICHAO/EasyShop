package com.example.moguhaian.easyshop.Search;

import android.text.TextUtils;

import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.TaoUtils;
import com.example.moguhaian.easyshop.listener.JsoupParseListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SameStyleBiz extends BaseBiz {

    public void jsoupShopList(final String shopsUrl, JsoupParseListener listener) {

        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = "";
                try {
//                    if (TextUtils.isEmpty(SharedPreferencesUtils.getValue(Constants.Cookies))) {
//                        TBLogin();
//                    }
                    Document document = Jsoup.connect(shopsUrl).cookie("Cookie", SharedPreferencesUtils.getValue(Constants.Cookies)).userAgent(Constants.UserAgentString).ignoreContentType(true).get();
//                    Connection connect = Jsoup.connect(shopsUrl);
//                    Map<String, String> header = new HashMap<String, String>();
////                    header.put("Host", "https://s.taobao.com");
//                    header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
//                    header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//                    header.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
//                    header.put("cookie", "t=03d0637ca1a3c145d24b0097443032e8; cna=LahyFBm1X18CAcppiMo5s829; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; tg=0; miid=624123301571999455; enc=p6xjZvXhqsO9PBWmcO9HXwQ2mIqv8gAdWOYnp4sReYFRk0bfd%2BeoqhrRRRIMQd5Ntp3goeRKk1pkrHBtPu%2FmcA%3D%3D; UM_distinctid=168e53411533df-044b688f7e6024-b781636-1fa400-168e5341154f2; l=bBEtBk5RvPC-wfJYBOfiVuIRbibOZIdf1sPzw4ZftICP9e11-2aRWZZD67YBC3GVa6IvJ3z5HU79B0Towy4Eg; cookie2=1c2ad847b5516690a8c50a1fba32841c; _tb_token_=ee3b09e3f6e98; linezing_session=Ze4Ryn48qkt1u25kHuLbLOgn_1552292085562Ovwn_2; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; alitrackid=item.taobao.com; swfstore=72717; lastalitrackid=www.taobao.com; _cc_=W5iHLLyFfA%3D%3D; _m_h5_tk=59956aa43587b89c45bec3fce86a3df3_1552536345754; _m_h5_tk_enc=6854c28367b2d44e1349b217a85f267f; ali_ab=116.25.41.185.1552527712178.4; whl=-1%260%260%261552532851506; mt=ci=0_0; v=0; JSESSIONID=3413775AD35A3C7ED34831C5766F9D03; isg=BLW1dsJoBf1J82YkXbSmc6XrxDGvmgIIHkIA8Tfavix4DtYA8oJ5FMP4XNI4ToH8");
//                    connect.data(header);
//                    Document document = connect.get();

//                    Elements j_clickStat = document.getElementsByClass("info1__itemname");
//                    LogUtils.e("J_ClickStat:" + j_clickStat.size() + "");
                    Elements script = document.getElementsByTag("script");
                    for (Element ele : script) {
                        if (ele.data().contains("g_page_config")) {
                            json = ele.data();
                        }
                    }
                    json = json.replace("\\", "");
                    LogUtils.e("json:" + json);
                    TaoUtils.getSameStyleUrl(json);
//                    Elements trace_pid = document.getElementsByClass("trace-pid");
//                    LogUtils.e("trace_pid:" + trace_pid.size() + "");
                } catch (IOException e) {
                    LogUtils.e("printStackTrace:" + e.getMessage());

                }
            }
        });
    }
}
