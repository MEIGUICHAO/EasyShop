package com.example.moguhaian.easyshop.Bean;


import top.eg100.code.excel.jxlhelper.annotations.ExcelContent;
import top.eg100.code.excel.jxlhelper.annotations.ExcelSheet;

@ExcelSheet(sheetName = "用户表")
public class Top20wBean {

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLv1() {
        return lv1;
    }

    public void setLv1(String lv1) {
        this.lv1 = lv1;
    }

    public String getLv2() {
        return lv2;
    }

    public void setLv2(String lv2) {
        this.lv2 = lv2;
    }

    public String getLv3() {
        return lv3;
    }

    public void setLv3(String lv3) {
        this.lv3 = lv3;
    }

    @ExcelContent(titleName = "关键词")
    private String keyWord;

    @ExcelContent(titleName = "一级类目")
    private String lv1;

    @ExcelContent(titleName = "二级类目")
    private String lv2;

    @ExcelContent(titleName = "三级类目")
    private String lv3;

}
