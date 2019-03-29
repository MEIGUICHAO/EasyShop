package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TemTitleBean {

    @Id
    private Long id;
    private String title;
    @Generated(hash = 221079466)
    public TemTitleBean(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    @Generated(hash = 1053620839)
    public TemTitleBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
