package com.factorymethod.idcard;

import com.factorymethod.framework.Factory;
import com.factorymethod.framework.Product;

import java.util.ArrayList;
import java.util.List;

public class IDCardFactory extends Factory {
    private List owners = new ArrayList();
    protected  Product createProduct(String owner) {
        return new IDCard(owner);
    }
    protected void registerProduct(Product product) {
        IDCard card = (IDCard)product;
        owners.add(card.getOwner());
    }
    public List getOwners() {
        return owners;
    }
}
