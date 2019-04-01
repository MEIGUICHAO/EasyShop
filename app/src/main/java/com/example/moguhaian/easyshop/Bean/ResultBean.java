package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ResultBean {

    @Id
    private Long id;
    private String shopName;
    private String rootUrls;
    private String rootName;
    private String adjWord;
    private String shopMainUrl;
    private String shopTitle;
    @Generated(hash = 1085930132)
    public ResultBean(Long id, String shopName, String rootUrls, String rootName,
            String adjWord, String shopMainUrl, String shopTitle) {
        this.id = id;
        this.shopName = shopName;
        this.rootUrls = rootUrls;
        this.rootName = rootName;
        this.adjWord = adjWord;
        this.shopMainUrl = shopMainUrl;
        this.shopTitle = shopTitle;
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
    public String getRootUrls() {
        return this.rootUrls;
    }
    public void setRootUrls(String rootUrls) {
        this.rootUrls = rootUrls;
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
    public String getShopMainUrl() {
        return this.shopMainUrl;
    }
    public void setShopMainUrl(String shopMainUrl) {
        this.shopMainUrl = shopMainUrl;
    }
    public String getShopTitle() {
        return this.shopTitle;
    }
    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }
}
