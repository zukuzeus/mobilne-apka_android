package com.example.danie.mobilne;

import com.example.danie.mobilne.database.DatabaseHelper;

/**
 * Created by danie on 07.11.2017.
 */

public class InterActivityVariablesSingleton {
    private static InterActivityVariablesSingleton INSTANCE;
    private final String http = "http:/";
    private final String LOGIN = "/login";
    private final String REGISTER = "/register";
    private final String PRODUCTS = "/results";
    private final String DELETE_PRODUCT = "/delete";
    private final String UPDATE_PRODUCT = "/update";
    private final String ADD_PRODUCT = "/add";
    private String Ip;
    private String SERVER_ADRESS;
    private String USER;
    private String PASSWORD;
    private DatabaseHelper User_db;


    private InterActivityVariablesSingleton() {

    }

    public synchronized static InterActivityVariablesSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InterActivityVariablesSingleton();
        }
        return INSTANCE;
    }

    public synchronized String getLoginURL() {
        return INSTANCE.SERVER_ADRESS + INSTANCE.LOGIN;
    }

    public synchronized String getRegisterURL() {
        return INSTANCE.SERVER_ADRESS + INSTANCE.REGISTER;
    }

    public synchronized String getProductsURL() {
        return INSTANCE.SERVER_ADRESS + INSTANCE.PRODUCTS;
    }

    public synchronized String getUSER() {
        return USER;
    }

    public synchronized void setUSER(String user) {
        this.USER = user;
    }

    public synchronized String getDeleteURL() {
        return INSTANCE.SERVER_ADRESS + DELETE_PRODUCT;
    }

    public synchronized String getUpdateURL() {
        return INSTANCE.SERVER_ADRESS + UPDATE_PRODUCT;
    }

    public synchronized String getAddURL() {
        return INSTANCE.SERVER_ADRESS + ADD_PRODUCT;
    }

    public synchronized void setServerIp(String ip) {
        this.INSTANCE.Ip = ip;
        this.INSTANCE.SERVER_ADRESS = INSTANCE.http + INSTANCE.Ip;
    }


    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
