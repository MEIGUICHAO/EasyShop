package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class CaijiBean {

    @Id
    private Long id;
    private String lv1Name;
    private String lv2Name;
    private String lv3Name;
    private String lv4Name;
    private String lv5Name;
    private String lv6Name;
    private String lv7Name;

    @NotNull
    private String shopUrl;
    private String originTitle;
    private String exchangeTitle;
    private String mainPath;
    private int position;
    private int exchagePosition;
    @Generated(hash = 1461988449)
    public CaijiBean(Long id, String lv1Name, String lv2Name, String lv3Name,
            String lv4Name, String lv5Name, String lv6Name, String lv7Name,
            @NotNull String shopUrl, String originTitle, String exchangeTitle,
            String mainPath, int position, int exchagePosition) {
        this.id = id;
        this.lv1Name = lv1Name;
        this.lv2Name = lv2Name;
        this.lv3Name = lv3Name;
        this.lv4Name = lv4Name;
        this.lv5Name = lv5Name;
        this.lv6Name = lv6Name;
        this.lv7Name = lv7Name;
        this.shopUrl = shopUrl;
        this.originTitle = originTitle;
        this.exchangeTitle = exchangeTitle;
        this.mainPath = mainPath;
        this.position = position;
        this.exchagePosition = exchagePosition;
    }
    @Generated(hash = 232354729)
    public CaijiBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLv1Name() {
        return this.lv1Name;
    }
    public void setLv1Name(String lv1Name) {
        this.lv1Name = lv1Name;
    }
    public String getLv2Name() {
        return this.lv2Name;
    }
    public void setLv2Name(String lv2Name) {
        this.lv2Name = lv2Name;
    }
    public String getLv3Name() {
        return this.lv3Name;
    }
    public void setLv3Name(String lv3Name) {
        this.lv3Name = lv3Name;
    }
    public String getLv4Name() {
        return this.lv4Name;
    }
    public void setLv4Name(String lv4Name) {
        this.lv4Name = lv4Name;
    }
    public String getLv5Name() {
        return this.lv5Name;
    }
    public void setLv5Name(String lv5Name) {
        this.lv5Name = lv5Name;
    }
    public String getLv6Name() {
        return this.lv6Name;
    }
    public void setLv6Name(String lv6Name) {
        this.lv6Name = lv6Name;
    }
    public String getLv7Name() {
        return this.lv7Name;
    }
    public void setLv7Name(String lv7Name) {
        this.lv7Name = lv7Name;
    }
    public String getShopUrl() {
        return this.shopUrl;
    }
    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }
    public String getOriginTitle() {
        return this.originTitle;
    }
    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }
    public String getExchangeTitle() {
        return this.exchangeTitle;
    }
    public void setExchangeTitle(String exchangeTitle) {
        this.exchangeTitle = exchangeTitle;
    }
    public String getMainPath() {
        return this.mainPath;
    }
    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getExchagePosition() {
        return this.exchagePosition;
    }
    public void setExchagePosition(int exchagePosition) {
        this.exchagePosition = exchagePosition;
    }

}
