package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Top20wRecordBean {

    @Id
    private Long id;
    private String keyWord;
    private String lv1;
    private String lv2;
    private String lv3;
    private boolean hasSearch;
    private String titlesArray;
    private String linkUrls1688;
    @Generated(hash = 1601072797)
    public Top20wRecordBean(Long id, String keyWord, String lv1, String lv2,
            String lv3, boolean hasSearch, String titlesArray,
            String linkUrls1688) {
        this.id = id;
        this.keyWord = keyWord;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
        this.hasSearch = hasSearch;
        this.titlesArray = titlesArray;
        this.linkUrls1688 = linkUrls1688;
    }
    @Generated(hash = 244771959)
    public Top20wRecordBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyWord() {
        return this.keyWord;
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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
    public boolean getHasSearch() {
        return this.hasSearch;
    }
    public void setHasSearch(boolean hasSearch) {
        this.hasSearch = hasSearch;
    }
    public String getTitlesArray() {
        return this.titlesArray;
    }
    public void setTitlesArray(String titlesArray) {
        this.titlesArray = titlesArray;
    }
    public String getLinkUrls1688() {
        return this.linkUrls1688;
    }
    public void setLinkUrls1688(String linkUrls1688) {
        this.linkUrls1688 = linkUrls1688;
    }

}
