package com.example.danie.mobilne.ShopList;

import java.io.Serializable;

/**
 * Created by danie on 09.11.2017.
 */

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String productName;
    private String store;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(String productName, String store, double price, int quantity) {
        this.productName = productName;
        this.store = store;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
