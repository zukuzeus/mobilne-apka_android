package com.example.danie.mobilne.ShopList;

import org.json.JSONException;
import org.json.JSONObject;

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
    private int quantityRemote;


    public Product() {
    }

    public Product(String productName, String store, double price, int quantity) {
        this.productName = productName;
        this.store = store;
        this.price = price;
        this.quantity = quantity;
        this.quantityRemote = 0;
        //this.subStatesOfProduct = new ProductGlobalState(quantity, 0);
    }

    public Product(String productName, String store, double price, int quantity, int quantityRemote) {
        this.productName = productName;
        this.store = store;
        this.price = price;
        this.quantity = quantity;
        this.quantityRemote = quantityRemote;
        //this.subStatesOfProduct = new ProductGlobalState(quantity, 0);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", store='" + store + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", quantityRemote=" + this.quantityRemote +
                '}';
    }

    public JSONObject toJsonObject() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productName", this.productName);
            jsonObject.put("store", this.store);
            jsonObject.put("price", this.price);
            jsonObject.put("quantity", this.quantity);
            //jsonObject.put("quantityRemote", this.quantityRemote);
//            jsonObject.put("quantityRemote", this.quantityRemote)

//            jsonObject.put("subStatesOfProduct", this.subStatesOfProduct.toJSONObject());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
        //this.subStatesOfProduct.setQuantityLocaly(quantity);
        this.quantity = quantity;
    }

    public int getQuantityRemote() {
        return quantityRemote;
    }

    public void setQuantityRemote(int quantity) {
//        this.subStatesOfProduct.setDeviceQuantityFromRemote(quantity);
        this.quantityRemote = quantity;
    }

    public int getProductSum() {
        return this.quantity + this.quantityRemote;
    }
}
