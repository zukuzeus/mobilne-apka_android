package com.example.danie.mobilne;

/**
 * Created by danie on 07.11.2017.
 */

public class InterActivityVariablesSingleton {
    private static InterActivityVariablesSingleton INSTANCE;

    private final String SERVERADRESS = "http:/" + "192.168.1.105:8080";
    private final String LOGIN = "/login";
    private final String REGISTER = "/register";
    private final String PRODUCTS = "/results";
    private final String DELETE_PRODUCT = "/delete";
    private final String UPDATE_PRODUCT = "/update";
    private final String ADD_PRODUCT = "/add";
    private String USER;


    private InterActivityVariablesSingleton() {

    }

    public synchronized static InterActivityVariablesSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InterActivityVariablesSingleton();
        }
        return INSTANCE;
    }

    public synchronized String getLoginURL() {
        return INSTANCE.SERVERADRESS + INSTANCE.LOGIN;
    }

    public synchronized String getRegisterURL() {
        return INSTANCE.SERVERADRESS + INSTANCE.REGISTER;
    }

    public synchronized String getProductsURL() {
        return INSTANCE.SERVERADRESS + INSTANCE.PRODUCTS;
    }

    public synchronized String getUSER() {
        return USER;
    }

    public synchronized void setUSER(String user) {
        this.USER = user;
    }

    public synchronized String getDeleteURL() {
        return INSTANCE.SERVERADRESS + DELETE_PRODUCT;
    }

    public synchronized String getUpdateURL() {
        return INSTANCE.SERVERADRESS + UPDATE_PRODUCT;
    }

    public synchronized String getAddURL() {
        return INSTANCE.SERVERADRESS + ADD_PRODUCT;
    }
}
