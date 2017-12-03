package com.example.danie.mobilne.ShopList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.danie.mobilne.CustomActivities.ActivityWithDatabase;
import com.example.danie.mobilne.InterActivityVariablesSingleton;
import com.example.danie.mobilne.R;
import com.example.danie.mobilne.UpdateCreateDeleteProductActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopListActivity extends ActivityWithDatabase {

    ListView listView;
    Button synchronise;
    Button newProduct;
    EditText productName;
    EditText shop;
    EditText price;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        setupOnCreate();
        setupListeners();
//        createDatabase();
        //askServerDatabaseForProducts();
        askLocalDatabaseForProducts();
    }

    private void setupOnCreate() {
        listView = (ListView) findViewById(R.id.product_list);
        newProduct = (Button) findViewById(R.id.productAdd);
        productName = (EditText) findViewById(R.id.newProductName);
        shop = (EditText) findViewById(R.id.newProductShop);
        price = (EditText) findViewById(R.id.newProductPrice);
        newProduct.setEnabled(isText(productName) && isText(shop) && isText(price));
        synchronise = (Button) findViewById(R.id.synchronise_button);
    }

    private void setupListeners() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent singleProductScreen = new Intent(ShopListActivity.this, UpdateCreateDeleteProductActivity.class);
            singleProductScreen.putExtra("product", productList.get(position));
            //startActivity(singleProductScreen);
            startActivityForResult(singleProductScreen, 1);
        });
//        newProduct.setOnClickListener(v -> {
//            addProductToServerDatabase();
//            recreate();
//
//        });
        newProduct.setOnClickListener(v -> {
            Product prod = new Product(productName.getText().toString(), shop.getText().toString(), Double.parseDouble(price.getText().toString()), 0);
            boolean isInserted = DATABASE.insertData(prod);
            if (isInserted == true)
                Toast.makeText(ShopListActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ShopListActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            askLocalDatabaseForProducts();
            recreate();
        });
        synchronise.setOnClickListener(v -> {
            askServerDatabaseForProductsAndSaveItLocally();
        });

        final TextWatcher buttonEnabledWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isText(productName) && isText(shop) && isText(price)) {
                    newProduct.setEnabled(true);
                } else {
                    newProduct.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        productName.addTextChangedListener(buttonEnabledWatcher);
        shop.addTextChangedListener(buttonEnabledWatcher);
        price.addTextChangedListener(buttonEnabledWatcher);
    }

    private void askServerDatabaseForProducts() {

        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getProductsURL(), s -> {
            try {
                createProductListFromJSONArray(new JSONArray(s));
                ProductAdapter adapter = new ProductAdapter(ShopListActivity.this, productList);
                listView.setAdapter(adapter);
            } catch (Exception e) {
                Log.d("Some tag", Log.getStackTraceString(e.getCause().getCause()));
            }

        }, error -> Toast.makeText(ShopListActivity.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                parameters.put("password", InterActivityVariablesSingleton.getInstance().getPASSWORD());
                return parameters;
            }
        };
        RequestQueue rQueuee = Volley.newRequestQueue(ShopListActivity.this);
        rQueuee.add(request);
    }

    private void askServerDatabaseForProductsAndSaveItLocally() {
        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getProductsURL(), s -> {
            try {
                List<Product> prod = createProductListFromJSONArrayAndGetIt(new JSONArray(s));
                DATABASE.insertDataToDBFromList(prod);
                askLocalDatabaseForProducts();
                recreate();
            } catch (Exception e) {
                //Log.d("Some tag", Log.getStackTraceString(e.getCause().getCause()));
                Toast.makeText(ShopListActivity.this, "Some error occurred -> " + e, Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(ShopListActivity.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                parameters.put("password", InterActivityVariablesSingleton.getInstance().getPASSWORD());
//                parameters.put("id", InterActivityVariablesSingleton.getInstance().getDEVICE_ID());
                return parameters;
            }
        };
        RequestQueue rQueuee = Volley.newRequestQueue(ShopListActivity.this);
        rQueuee.add(request);
    }

    private void addProductToServerDatabase() {
        //TODO zrobić porzadek z tym czyms
        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getAddURL(), (String s) -> {
            if (s.equals("")) {
//                InterActivityVariablesSingleton.getInstance().get
                Toast.makeText(ShopListActivity.this, "no responce", Toast.LENGTH_LONG).show();
            } else if (s.equals("true")) {
                Toast.makeText(ShopListActivity.this, "new productName added", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(ShopListActivity.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                parameters.put("password", InterActivityVariablesSingleton.getInstance().getPASSWORD());
                parameters.put("productName", productName.getText().toString());
                parameters.put("shop", shop.getText().toString());
                parameters.put("price", price.getText().toString());
                return parameters;
            }
        };
        RequestQueue rQueuee = Volley.newRequestQueue(ShopListActivity.this);
        rQueuee.add(request);
    }

    private void askLocalDatabaseForProducts() {
        productList = DATABASE.getAllProductsAsList();
        ProductAdapter adapter = new ProductAdapter(ShopListActivity.this, productList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //askServerDatabaseForProducts();
        askLocalDatabaseForProducts();
        productName.setText("");
        shop.setText("");
        price.setText("");
        newProduct.setEnabled(false);
    }

    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }

    private boolean isText(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return true;

        return false;
    }

    private void createProductListFromJSONArray(JSONArray jsonArray) throws JSONException {
        productList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            Product product = new Product(o.getString("productName"), o.getString("store"), o.getDouble("prize"), o.getInt("quantity"));
            productList.add(product);
        }
        //ewentualne dodanie nowego pustego wiersza
        //productList.add(new Product("", "", 0.0, 0));
    }

    private List<Product> createProductListFromJSONArrayAndGetIt(JSONArray jsonArray) throws JSONException {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            Product product = new Product(o.getString("product"), o.getString("store"), o.getDouble("prize"), o.getInt("quantity"));
            products.add(product);
            //productList.add(product);
        }
        return products;
        //ewentualne dodanie nowego pustego wiersza
        //productList.add(new Product("", "", 0.0, 0));
    }
}
