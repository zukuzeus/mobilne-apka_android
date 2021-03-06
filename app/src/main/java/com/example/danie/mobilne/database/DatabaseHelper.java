package com.example.danie.mobilne.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.danie.mobilne.ShopList.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 02.12.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME_PRODUCTS = "products";
    public static final String COL_1_PRODUCTS = "product";
    public static final String COL_2_PRODUCTS = "store";
    public static final String COL_3_PRODUCTS = "price"; // REAL
    public static final String COL_4_PRODUCTS = "quantity"; // INT
    public static final String COL_5_PRODUCTS = "quantityremote"; // INT
    public static final String TASK_CREATE_PRODUCTS_TABLE_STANDALONE = "create table " + TABLE_NAME_PRODUCTS + " "
            + "(" + COL_1_PRODUCTS + " TEXT," + COL_2_PRODUCTS
            + " TEXT," + COL_3_PRODUCTS + " REAL," + COL_4_PRODUCTS + " INT," + COL_5_PRODUCTS + " INT,"
            + " PRIMARY KEY (" + COL_1_PRODUCTS + ")"
            + ");";
    public static final String TABLE_NAME_ID = "deviceid";
    public static final String COL_1_ID = "deviceidentyficator";
    public static final String TASK_CREATE_DEVICE_TABLE_ID = "create table " + TABLE_NAME_ID + " "
            + "( " + COL_1_ID + " INT PRIMARY KEY )";
    private static final String DEBUG_TAG = "SqLiteTodoManager";
    private static final String COMMA = ",";
    private static final String EAR = "'";

    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context, final String DB_NAME) {
        //super(new DatabaseContext(context), DB_NAME, null, 1);
        super(context, DB_NAME, null, 5);
//        DirectoryManager.getDir(context, DirectoryManager.ChildDir.Databases, DirectoryManager.ForcePath.SDCard) + nazwa pliku
//        Environment.getExternalStorageDirectory()
//                + File.separator + FILE_DIR
//                + File.separator + DB_NAME + ".db"
        //DB_NAME + ".db"
        //Environment.getExternalStorageDirectory().getPath() + // nie wiem jak podejrzec tą baze
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.setForeignKeyConstraintsEnabled(true);
        //db.execSQL(TASK_CREATE_USERS_TABLE);
        //db.execSQL(TASK_CREATE_PRODUCTS_TABLE);
        db.execSQL(TASK_CREATE_PRODUCTS_TABLE_STANDALONE);
        db.execSQL(TASK_CREATE_DEVICE_TABLE_ID);
        //db.execSQL("Insert into " + TABLE_NAME_ID + -1);

        Log.d(DEBUG_TAG, "Database creating...");
        Log.d(DEBUG_TAG, "Table " + TABLE_NAME_PRODUCTS + " ver." + DB_VERSION + " created");

        Log.d(DEBUG_TAG, "Table " + TASK_CREATE_DEVICE_TABLE_ID + " ver." + DB_VERSION + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ID);
        Log.d(DEBUG_TAG, "Database updating...");
        Log.d(DEBUG_TAG, "Table " + TABLE_NAME_PRODUCTS + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "All data is lost.");
        Log.d(DEBUG_TAG, "Database updating...");
        Log.d(DEBUG_TAG, "Table " + TABLE_NAME_ID + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "All data is lost.");
        onCreate(db);

    }

    public boolean insertID(final int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_ID, id);
        long result = db.insert(TABLE_NAME_ID, null, contentValues);
//        long result = db.update(TABLE_NAME_ID, null, COL_1_ID + " = -1", new String[]{Integer.toString(id)});
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getID() {
        int id = -1;
        Cursor cursor = getDataFromID();
        if (cursor.moveToFirst()) {
            id = Integer.parseInt(cursor.getString(0));
        }
        return id;
    }

    public boolean insertData(final String productName, final String store, final double price, final int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_PRODUCTS, productName);
        contentValues.put(COL_2_PRODUCTS, store);
        contentValues.put(COL_3_PRODUCTS, price);
        contentValues.put(COL_4_PRODUCTS, quantity);
        long result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(final Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_PRODUCTS, product.getProductName());
        contentValues.put(COL_2_PRODUCTS, product.getStore());
        contentValues.put(COL_3_PRODUCTS, product.getPrice());
        contentValues.put(COL_4_PRODUCTS, product.getQuantity());
        long result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataSynchronize(final Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_PRODUCTS, product.getProductName());
        contentValues.put(COL_2_PRODUCTS, product.getStore());
        contentValues.put(COL_3_PRODUCTS, product.getPrice());
        contentValues.put(COL_4_PRODUCTS, 0);
        contentValues.put(COL_5_PRODUCTS, product.getQuantityRemote());
        long result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_PRODUCTS, null);
        return res;
    }

    public Cursor getDataFromID() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_ID, null);
        return res;
    }

    public List<Product> getAllMyProductsAsList() {
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
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)));
                // product.setQuantityRemote(Integer.parseInt(cursor.getString(4)));
                ProductList.add(product);
            } while (cursor.moveToNext());
        }
        // return contact list
        return ProductList;
    }

    public boolean updateData(final String productName, final String store, final double price, final int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_PRODUCTS, productName);
        contentValues.put(COL_2_PRODUCTS, store);
        contentValues.put(COL_3_PRODUCTS, price);
        contentValues.put(COL_4_PRODUCTS, quantity);
        db.update(TABLE_NAME_PRODUCTS, contentValues, COL_1_PRODUCTS + " = ?", new String[]{productName});
        return true;
    }

    public boolean insertDataToDBFromList(final List<Product> productList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = 1;
        for (int i = 0; i < productList.size(); i++) {
            contentValues.put(COL_1_PRODUCTS, productList.get(i).getProductName());
            contentValues.put(COL_2_PRODUCTS, productList.get(i).getStore());
            contentValues.put(COL_3_PRODUCTS, productList.get(i).getPrice());
            contentValues.put(COL_4_PRODUCTS, productList.get(i).getQuantity());
            result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);
//            if (result == -1) return false;
        }
        if (result == -1) return false;
        return true;
    }

    public boolean updateData(final Product product, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_PRODUCTS, product.getProductName());
        contentValues.put(COL_2_PRODUCTS, product.getStore());//potencjalnie do wykomentowania
        contentValues.put(COL_3_PRODUCTS, product.getPrice());//
        contentValues.put(COL_4_PRODUCTS, newQuantity);
        long result = db.update(TABLE_NAME_PRODUCTS, contentValues, COL_1_PRODUCTS + " = ?", new String[]{product.getProductName()});
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateDataSynchronize(final Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5_PRODUCTS, product.getQuantityRemote());
        long result = db.update(TABLE_NAME_PRODUCTS, contentValues, COL_1_PRODUCTS + " = ?", new String[]{product.getProductName()});
        if (result == -1)
            return false;
        else
            return true;
    }

    public Integer deleteData(final String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_PRODUCTS, COL_1_PRODUCTS + " = ?", new String[]{productName});
    }

    public Integer deleteData(final Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_PRODUCTS, COL_1_PRODUCTS + " = ?", new String[]{product.getProductName()});
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertOrUpdateProducts(List<Product> productList) {
        productList.forEach(this::insertOrUpdateProduct);
    }

    public void insertOrUpdateProduct(Product product) {
        if (isProductExist(product.getProductName())) {
            updateDataSynchronize(product);
        } else {
            insertDataSynchronize(product);
        }
    }

    private boolean isProductExist(String productName) {
        long line = DatabaseUtils.longForQuery(this.getReadableDatabase(), "SELECT COUNT(*) FROM " + TABLE_NAME_PRODUCTS + " WHERE " + COL_1_PRODUCTS + "=?",
                new String[]{productName});
        return line > 0;
    }
}
