package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SameStyleTitleArrayBean {

    @Id
    private Long id;
    private long productId;
    private String title;
    @Generated(hash = 1294194599)
    public SameStyleTitleArrayBean(Long id, long productId, String title) {
        this.id = id;
        this.productId = productId;
        this.title = title;
    }
    @Generated(hash = 546091717)
    public SameStyleTitleArrayBean() {
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
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
