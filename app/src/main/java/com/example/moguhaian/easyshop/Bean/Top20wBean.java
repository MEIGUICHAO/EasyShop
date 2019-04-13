package com.example.moguhaian.easyshop.Bean;


import top.eg100.code.excel.jxlhelper.annotations.ExcelContent;
import top.eg100.code.excel.jxlhelper.annotations.ExcelSheet;

@ExcelSheet(sheetName = "用户表")
public class Top20wBean {

    @ExcelContent(titleName = "关键词")
    private String keyWord;

    @ExcelContent(titleName = "一级类目")
    private String lv1;

    @ExcelContent(titleName = "二级类目")
    private String lv2;

    @ExcelContent(titleName = "三级类目")
    private String lv3;

}
