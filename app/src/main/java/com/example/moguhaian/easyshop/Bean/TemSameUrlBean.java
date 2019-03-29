package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TemSameUrlBean {

    @Id
    private Long id;
    private String url;
    @Generated(hash = 834365123)
    public TemSameUrlBean(Long id, String url) {
        this.id = id;
        this.url = url;
    }
    @Generated(hash = 1563113795)
    public TemSameUrlBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
