package com.example.danie.mobilne;

/**
 * Created by danie on 07.11.2017.
 */

public class InterActivityVariables {
    private static InterActivityVariables INSTANCE;

    private final String SERVERADRESS = "http:/" + "192.168.1.104:8080";
    private final String LOGIN = "/login";
    private final String REGISTER = "/register";
    private final String PRODUCTS = "/results";
    private String USER;

    private InterActivityVariables() {

    }

    public synchronized static InterActivityVariables getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InterActivityVariables();
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

    public String getUSER() {
        return USER;
    }

    public void setUSER(String user) {
        this.USER = user;
    }

}
