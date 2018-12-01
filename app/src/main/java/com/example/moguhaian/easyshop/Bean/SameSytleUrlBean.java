package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SameSytleUrlBean {

    @Id
    private Long id;
    private long productId;
    private String sameStyleUrl;
    @Generated(hash = 2137362940)
    public SameSytleUrlBean(Long id, long productId, String sameStyleUrl) {
        this.id = id;
        this.productId = productId;
        this.sameStyleUrl = sameStyleUrl;
    }
    @Generated(hash = 145094929)
    public SameSytleUrlBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getProductId() {
        return this.productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
    public String getSameStyleUrl() {
        return this.sameStyleUrl;
    }
    public void setSameStyleUrl(String sameStyleUrl) {
        this.sameStyleUrl = sameStyleUrl;
    }
}
