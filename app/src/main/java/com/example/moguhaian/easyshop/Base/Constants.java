package com.example.moguhaian.easyshop.Base;

public interface Constants {

//    public String Top20wUrl = "http://www.taocece.com/ztctop20wmobile";
    public String taosjLoginUrl = "http://login.taosj.com/?redirectURL=http%3A%2F%2Fwww.taosj.com%2Ftool%2Ftool.htm%23%2Foptimize_market%2Ftitle%2F";
    public String SelectionUrl = "https://www.taosj.com/tool/tool.htm#/optimize_market/keyword/";
    public String Top20wUrl = "https://taodaxiang.com/top20w/view";
    public String MY_INDEX = "###MYINDEX###";
    public String SORT_TYPE = "###SORT_TYPE###";
    public String todaySales = "https://s.taobao.com/search?ie=utf8&tab=vsearch&initiative_id=staobaoz_20180514&js=1&imgfile=&q=%E6%B4%97%E9%9D%A2%E5%A5%B6&suggest=history_2&_input_charset=utf-8&wq=&suggest_query=&source=suggest&sort=renqi-desc&bcoffset=0&p4ppushleft=%2C44&s=0";
    public String TaoBaoSearchUrl = "https://s.taobao.com/search?initiative_id=tbindexz_20170306&ie=utf8&spm=a21bo.2017.201856-taobao-item.2&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all&imgfile=&q=" + MY_INDEX + "&suggest=history_1&_input_charset=utf-8&wq=&suggest_query=&source=suggest&sort=" + SORT_TYPE;
    public String SaleDescSort = "&sort=sale-desc";
//    ""
//    模拟器
//    public String UserAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36";
//    手机
    public String UserAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
//    edge
//    public String UserAgentString = "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134";
//    ie
//    public String UserAgentString = "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
    String searchUrl1 = "https://s.taobao.com/search?&initiative_id=tbindexz_20170306&ie=utf8&spm=a21bo.2017.201856-taobao-item.2&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all&imgfile=&q=";
    String searchUrl2 = "&suggest=0_1&_input_charset=utf-8&wq=%E7%A7%AF%E6%9C%A8&suggest_query=%E7%A7%AF%E6%9C%A8&source=suggest";

    String BAIDU = "https://www.baidu.com";



//    https://www.taosj.com/tool/tool.htm#/optimize_market/keyword/?keyword=%E5%8F%98%E5%BD%A2&pageNo=1&pageSize=100
//    https://www.taosj.com/tool/tool.htm#/optimize_market/keyword/
//    18620587647 m123456
//    搜索指数-在线宝贝数


//    https://www.taosj.com/tool/ztc.htm#wordPrecise?word=%E6%B0%B4%E5%BC%B9&decorateWords=&excludeWords=
//    直通车选词




//    店查查 词可查关键词


    int DELAY_TIME = 2400;




    String EASYSHOP = "EASYSHOP";

    String Cookies = "Cookies";
    String SLIDE_DOWN_X = "SLIDE_DOWN_X";

    String SLIDE_DOWN_Y = "SLIDE_DOWN_Y";
    String SLIDE_UP_X = "SLIDE_UP_X";
    String SLIDE_UP_Y = "SLIDE_UP_Y";
    String CLICK_DOWN_X = "CLICK_DOWN_X";
    String CLICK_DOWN_Y = "CLICK_DOWN_Y";

    String PIC_SPACE_INPUT_CLICK_DOWN_X = "PIC_SPACE_INPUT_CLICK_DOWN_X";
    String PIC_SPACE_INPUT_CLICK_DOWN_Y = "PIC_SPACE_INPUT_CLICK_DOWN_Y";
    String PIC_SPACE_SELECT_CLICK_DOWN_X = "PIC_SPACE_SELECT_CLICK_DOWN_X";
    String PIC_SPACE_SELECT_CLICK_DOWN_Y = "PIC_SPACE_SELECT_CLICK_DOWN_Y";
    String PIC_SPACE_SEARCH_CLICK_DOWN_X = "PIC_SPACE_SEARCH_CLICK_DOWN_X";
    String PIC_SPACE_SEARCH_CLICK_DOWN_Y = "PIC_SPACE_SEARCH_CLICK_DOWN_Y";
    String PIC_SPACE_PASTE_CLICK_DOWN_X = "PIC_SPACE_PASTE_CLICK_DOWN_X";
    String PIC_SPACE_PASTE_CLICK_DOWN_Y = "PIC_SPACE_PASTE_CLICK_DOWN_Y";

    String PUBLISH_MOBILE_DETAIL_X = "PUBLISH_MOBILE_DETAIL_X";
    String PUBLISH_MOBILE_DETAIL_Y = "PUBLISH_MOBILE_DETAIL_Y";
    String PUBLISH_MOBILE_COMFIR_X = "PUBLISH_MOBILE_COMFIR_X";
    String PUBLISH_MOBILE_COMFIR_Y = "PUBLISH_MOBILE_COMFIR_Y";

    String TIME_CLICK_YMD_X = "TIME_CLICK_YMD_X";
    String TIME_CLICK_YMD_Y = "TIME_CLICK_YMD_Y";

    String TIME_CLICK_HMM_X = "TIME_CLICK_HMM_X";
    String TIME_CLICK_HMM_Y = "TIME_CLICK_HMM_Y";

    String TIME_CLICK_COMFIR_X = "TIME_CLICK_COMFIR_X";
    String TIME_CLICK_COMFIR_Y = "TIME_CLICK_COMFIR_Y";

    String PIC_SPACE_FIRST_CLICK_X = "PIC_SPACE_FIRST_CLICK_X";
    String PIC_SPACE_FIRST_CLICK_Y = "PIC_SPACE_FIRST_CLICK_Y";

    String FOLDER_SELECT_CLICK_X = "FOLDER_SELECT_CLICK_X";
    String FOLDER_SELECT_CLICK_Y = "FOLDER_SELECT_CLICK_Y";

    String RELOAD_DRAFT_CLICK_X = "RELOAD_DRAFT_CLICK_X";
    String RELOAD_DRAFT_CLICK_Y = "RELOAD_DRAFT_CLICK_Y";

    String FOLDER_COMFIR_CLICK_X = "FOLDER_COMFIR_CLICK_X";
    String FOLDER_COMFIR_CLICK_Y = "FOLDER_COMFIR_CLICK_Y";
    String CSS_FILE_NAME = "MyCSS.css";
    String TB_TITLE_RESULT = "TB_TITLE_RESULT";
    String ALI_SHOP_RESULT = "ALI_SHOP_RESULT";
    String ALI_CURRENT_PAGE = "ALI_CURRENT_PAGE";
    String GET_UPLOAD_PIC_NAMES = "GET_UPLOAD_PIC_NAMES";
    String PUBLISH_PAGE_LEAVE_X = "PUBLISH_PAGE_LEAVE_X";
    String PUBLISH_PAGE_LEAVE_Y = "PUBLISH_PAGE_LEAVE_Y";
    String SAVE_DRAFT = "SAVE_DRAFT";
}
