package com.example.asus.jingdong.model.bean;

/**
 * 购物车存起了的总价和数量
 */
public class Bean {
    private String price;
    private String number;

    public Bean(String number, String price) {
        this.price = price;
        this.number = number;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

