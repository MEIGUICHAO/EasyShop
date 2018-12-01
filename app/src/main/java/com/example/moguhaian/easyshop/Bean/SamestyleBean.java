package com.example.moguhaian.easyshop.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SamestyleBean {

    @Id
    private Long id;
    private String productNames;
    private int titleTableId;
    private String titleArrayId;
    @Generated(hash = 1030949225)
    public SamestyleBean(Long id, String productNames, int titleTableId,
            String titleArrayId) {
        this.id = id;
        this.productNames = productNames;
        this.titleTableId = titleTableId;
        this.titleArrayId = titleArrayId;
    }
    @Generated(hash = 309377369)
    public SamestyleBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProductNames() {
        return this.productNames;
    }
    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }
    public int getTitleTableId() {
        return this.titleTableId;
    }
    public void setTitleTableId(int titleTableId) {
        this.titleTableId = titleTableId;
    }
    public String getTitleArrayId() {
        return this.titleArrayId;
    }
    public void setTitleArrayId(String titleArrayId) {
        this.titleArrayId = titleArrayId;
    }
}
