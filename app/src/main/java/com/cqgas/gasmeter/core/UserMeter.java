package com.cqgas.gasmeter.core;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class UserMeter {
    // 姓名
    private String name;
    // 编号
    private String number;
    // 地址
    private String address;
    // 上月用量
    private float lastMonthQuantity;
    // 本月用量
    private float quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLastMonthQuantity() {
        return lastMonthQuantity;
    }

    public void setLastMonthQuantity(float lastMonthQuantity) {
        this.lastMonthQuantity = lastMonthQuantity;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("name:%s,address:%s,number:%s,lastMonthQuantity:%f,quantity:%f",name,address,number,lastMonthQuantity,quantity);
    }
}
