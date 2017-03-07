package com.factorymethod.idcard;

import com.factorymethod.framework.Product;

public class IDCard extends Product {
    private String owner;
    IDCard(String owner) {
        System.out.println("制作ID卡"+owner);
        this.owner = owner;
    }
    public void use() {
        System.out.println("使用ID卡"+owner);
    }
    public String getOwner() {
        return owner;
    }
}
