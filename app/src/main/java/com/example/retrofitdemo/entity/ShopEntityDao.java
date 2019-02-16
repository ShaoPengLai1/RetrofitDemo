package com.example.retrofitdemo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShopEntityDao {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String url;
    private String price;
    private String sayMo;
    @Generated(hash = 1255427361)
    public ShopEntityDao(Long id, String name, String url, String price,
            String sayMo) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.price = price;
        this.sayMo = sayMo;
    }
    @Generated(hash = 1532384464)
    public ShopEntityDao() {
    }


    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getSayMo() {
        return this.sayMo;
    }
    public void setSayMo(String sayMo) {
        this.sayMo = sayMo;
    }

}
