package com.example.danie.mobilne.ShopList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by danie on 03.12.2017.
 */

public class ProductGlobalState implements Serializable {
    private static final long serialVersionUID = 3L;
    private int quantityLocaly;
    private int secondDeviceQuantity;
    //private Map<String,Integer> otherDevicesValues = new HashMap<String,Integer>();

    public ProductGlobalState() {

    }

    public ProductGlobalState(int quantityLocaly, int secondDeviceQuantity) {
        this.quantityLocaly = quantityLocaly;
        this.secondDeviceQuantity = secondDeviceQuantity;
    }

    public int getQuantityLocaly() {
        return quantityLocaly;
    }

    public void setQuantityLocaly(int quantityLocaly) {
        this.quantityLocaly = quantityLocaly;
    }

    public int getSecondDeviceQuantity() {
        return secondDeviceQuantity;
    }

    public void setDeviceQuantityFromRemote(int secondDeviceQuantity) {
        this.secondDeviceQuantity = secondDeviceQuantity;
    }

    @Override
    public String toString() {
        return "{" +
                "quantityLocaly=" + quantityLocaly +
                ", secondDeviceQuantity=" + secondDeviceQuantity +
                '}';
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("quantityLocaly", this.quantityLocaly);
            jsonObject.put("secondDeviceQuantity", this.secondDeviceQuantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
