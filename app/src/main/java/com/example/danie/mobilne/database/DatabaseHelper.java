package com.example.danie.mobilne.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.danie.mobilne.ShopList.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 02.12.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // public static final String DATABASE_NAME = "projekt.db";
    ////  public static final String TABLE_NAME_USERS = "users";
    public static final String TABLE_NAME_PRODUCTS = "products";
    //    public static final String COL_1_USERS = "username";
//    public static final String COL_2_USERS = "password";
//    public static final String COL_1_PRODUCTS = "username";
    public static final String COL_2_PRODUCTS = "product";
    public static final String COL_3_PRODUCTS = "store";
    public static final String COL_4_PRODUCTS = "price"; // REAL
    public static final String COL_5_PRODUCTS = "quantity"; // INT
    //    public static final String TASK_CREATE_PRODUCTS_TABLE = "create table " + TABLE_NAME_PRODUCTS + " "
//            + "(" + COL_1_PRODUCTS + " TEXT, " + COL_2_PRODUCTS + " TEXT," + COL_3_PRODUCTS
//            + " TEXT," + COL_4_PRODUCTS + " REAL," + COL_5_PRODUCTS + "INT"
//            + "PRIMARY KEY (" + COL_1_PRODUCTS + "," + COL_2_PRODUCTS + ")"
//            + "FOREIGN KEY (" + COL_1_PRODUCTS + ") REFERENCES " + TABLE_NAME_USERS + "(" + COL_1_USERS + ")"
//            + ")";
    public static final String TASK_CREATE_PRODUCTS_TABLE_STANDALONE = "create table " + TABLE_NAME_PRODUCTS + " "
            + "(" + COL_2_PRODUCTS + " TEXT," + COL_3_PRODUCTS
            + " TEXT," + COL_4_PRODUCTS + " REAL," + COL_5_PRODUCTS + " INT,"
            + " PRIMARY KEY (" + COL_2_PRODUCTS + ")"
            + ");";
    private static final String DEBUG_TAG = "SqLiteTodoManager";
    //    public static final String space = " ";
//    public static final String TASK_CREATE_USERS_TABLE = "create table "
//            + TABLE_NAME_USERS + " " + "(" + COL_1_USERS + " TEXT PRIMARY KEY, " + COL_2_USERS + " TEXT" + ")";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context, final String DB_NAME) {
        super(context, DB_NAME + ".db", null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.setForeignKeyConstraintsEnabled(true);
        //db.execSQL(TASK_CREATE_USERS_TABLE);
        //db.execSQL(TASK_CREATE_PRODUCTS_TABLE);
        db.execSQL(TASK_CREATE_PRODUCTS_TABLE_STANDALONE);
        Log.d(DEBUG_TAG, "Database creating...");
        Log.d(DEBUG_TAG, "Table " + TABLE_NAME_PRODUCTS + " ver." + DB_VERSION + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCTS);
        Log.d(DEBUG_TAG, "Database updating...");
        Log.d(DEBUG_TAG, "Table " + TABLE_NAME_PRODUCTS + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "All data is lost.");
        onCreate(db);

    }


    public boolean insertData(final String productName, final String store, final double price, final int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_PRODUCTS, productName);
        contentValues.put(COL_3_PRODUCTS, store);
        contentValues.put(COL_4_PRODUCTS, price);
        contentValues.put(COL_5_PRODUCTS, quantity);
        long result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(final Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_PRODUCTS, product.getProductName());
        contentValues.put(COL_3_PRODUCTS, product.getStore());
        contentValues.put(COL_4_PRODUCTS, product.getPrice());
        contentValues.put(COL_5_PRODUCTS, product.getQuantity());
        long result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataFromList(final List<Product> productList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = 1;
        for (int i = 0; i < productList.size(); i++) {
            contentValues.put(COL_2_PRODUCTS, productList.get(i).getProductName());
            contentValues.put(COL_3_PRODUCTS, productList.get(i).getStore());
            contentValues.put(COL_4_PRODUCTS, productList.get(i).getPrice());
            contentValues.put(COL_5_PRODUCTS, productList.get(i).getQuantity());
            result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);
            if (result == -1) return false;
        }
        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_PRODUCTS, null);
        return res;
    }

    public List<Product> getAllProductsAsList() {
        List<Product> ProductList = new ArrayList<Product>();
        // Select All Query
        Cursor cursor = getAllData();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(0),
                        cursor.getString(1),
                        Double.parseDouble(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)));
                ProductList.add(product);
            } while (cursor.moveToNext());
        }
        // return contact list
        return ProductList;
    }

    public boolean updateData(final String productName, final String store, final double price, final int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_PRODUCTS, productName);
        contentValues.put(COL_3_PRODUCTS, store);
        contentValues.put(COL_4_PRODUCTS, price);
        contentValues.put(COL_5_PRODUCTS, quantity);
        db.update(TABLE_NAME_PRODUCTS, contentValues, COL_2_PRODUCTS + " = ?", new String[]{productName});
        return true;
    }

    public boolean updateData(final Product product, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_PRODUCTS, product.getProductName());
        contentValues.put(COL_3_PRODUCTS, product.getStore());//potencjalnie do wykomentowania
        contentValues.put(COL_4_PRODUCTS, product.getPrice());//
        contentValues.put(COL_5_PRODUCTS, newQuantity);
        long result = db.update(TABLE_NAME_PRODUCTS, contentValues, COL_2_PRODUCTS + " = ?", new String[]{product.getProductName()});
        if (result == -1)
            return false;
        else
            return true;
    }

    public Integer deleteData(final String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_PRODUCTS, COL_2_PRODUCTS + " = ?", new String[]{productName});
    }

    public Integer deleteData(final Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_PRODUCTS, COL_2_PRODUCTS + " = ?", new String[]{product.getProductName()});
    }

}
