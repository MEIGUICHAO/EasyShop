package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ResultBean {

    @Id
    private Long id;
    private String shopName;//搜索词
    private String rootName;//词根
    private String adjWord;//修饰词
    private String rootResult;//母宝贝、主图、标题
    private String lv1;//分类
    private String lv2;
    private String lv3;
    private String lv4;
    private String lv5;
    private String lvResult;
    @Generated(hash = 2054662773)
    public ResultBean(Long id, String shopName, String rootName, String adjWord,
            String rootResult, String lv1, String lv2, String lv3, String lv4,
            String lv5, String lvResult) {
        this.id = id;
        this.shopName = shopName;
        this.rootName = rootName;
        this.adjWord = adjWord;
        this.rootResult = rootResult;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
        this.lv4 = lv4;
        this.lv5 = lv5;
        this.lvResult = lvResult;
    }
    @Generated(hash = 2137771703)
    public ResultBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShopName() {
        return this.shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getRootName() {
        return this.rootName;
    }
    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
    public String getAdjWord() {
        return this.adjWord;
    }
    public void setAdjWord(String adjWord) {
        this.adjWord = adjWord;
    }
    public String getRootResult() {
        return this.rootResult;
    }
    public void setRootResult(String rootResult) {
        this.rootResult = rootResult;
    }
    public String getLv1() {
        return this.lv1;
    }
    public void setLv1(String lv1) {
        this.lv1 = lv1;
    }
    public String getLv2() {
        return this.lv2;
    }
    public void setLv2(String lv2) {
        this.lv2 = lv2;
    }
    public String getLv3() {
        return this.lv3;
    }
    public void setLv3(String lv3) {
        this.lv3 = lv3;
    }
    public String getLv4() {
        return this.lv4;
    }
    public void setLv4(String lv4) {
        this.lv4 = lv4;
    }
    public String getLv5() {
        return this.lv5;
    }
    public void setLv5(String lv5) {
        this.lv5 = lv5;
    }
    public String getLvResult() {
        return this.lvResult;
    }
    public void setLvResult(String lvResult) {
        this.lvResult = lvResult;
    }
}
