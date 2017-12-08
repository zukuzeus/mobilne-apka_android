package com.example.danie.mobilne;

import android.os.Build;

import com.example.danie.mobilne.database.DatabaseHelper;

import java.util.UUID;

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
    private final String GETID = "/getId";
    private final String SYNCHRONIZE = "/synchronize";
    private String Ip;
    private String SERVER_ADRESS;
    private String USER;
    private String PASSWORD;
    private DatabaseHelper User_db;
    private String DEVICE_ID;


    private InterActivityVariablesSingleton() {
        this.DEVICE_ID = this.getPsuedoUniqueID();
    }

    public synchronized static InterActivityVariablesSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InterActivityVariablesSingleton();
        }
        return INSTANCE;
    }

    private static String getPsuedoUniqueID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their phone or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" +
                (Build.BOARD.length() % 10)
                + (Build.BRAND.length() % 10)
                + (Build.CPU_ABI.length() % 10)
                + (Build.DEVICE.length() % 10)
                + (Build.MANUFACTURER.length() % 10)
                + (Build.MODEL.length() % 10)
                + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their phone, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
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

    public synchronized String getIdURL() {
        return INSTANCE.SERVER_ADRESS + GETID;
    }

    public synchronized String getSYNCHRONIZEURL() {
        return INSTANCE.SERVER_ADRESS + SYNCHRONIZE;
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

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }
}
