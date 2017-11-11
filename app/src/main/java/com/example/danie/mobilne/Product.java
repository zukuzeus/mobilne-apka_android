package com.example.danie.mobilne;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by danie on 09.11.2017.
 */

public class Product implements Serializable {
    private String product;
    private String store;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(String product, String store, double price, int quantity) {
        this.product = product;
        this.store = store;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
