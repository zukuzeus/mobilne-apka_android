package com.example.danie.mobilne;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopListActivity extends Activity {
    // final TextView text = (TextView) findViewById(R.id.textView);

    ListView listView;
    Button newProduct;
    EditText product;
    EditText shop;
    EditText price;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        setupOnCreate();
        setupListeners();
        askDatabaseForProducts();
    }

    private void setupOnCreate() {
        listView = (ListView) findViewById(R.id.product_list);
        //listView.setAdapter(new ProductAdapter(this,productList));
        newProduct = (Button) findViewById(R.id.productAdd);
        product = (EditText) findViewById(R.id.newProductName);
        shop = (EditText) findViewById(R.id.newProductShop);
        price = (EditText) findViewById(R.id.newProductPrice);
        newProduct.setEnabled(isText(product) && isText(shop) && isText(price));
    }

    private void setupListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent singleProductScreen = new Intent(ShopListActivity.this, UpdateCreateDelete.class);
                singleProductScreen.putExtra("product", productList.get(position));
                startActivityForResult(singleProductScreen, 1);
            }
        });
        newProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToDatabase();
                recreate();
            }
        });
        final TextWatcher buttonEnabled = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isText(product) && isText(shop) && isText(price)) {
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
        product.addTextChangedListener(buttonEnabled);
        shop.addTextChangedListener(buttonEnabled);
        price.addTextChangedListener(buttonEnabled);
    }

    private void askDatabaseForProducts() {
        //TODO zrobić porzadek z tym czyms
        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getProductsURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    createProductList(new JSONArray(s));
                    ProductAdapter adapter = new ProductAdapter(ShopListActivity.this, productList);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.d("Some tag", Log.getStackTraceString(e.getCause().getCause()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShopListActivity.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                return parameters;
            }
        };
        RequestQueue rQueuee = Volley.newRequestQueue(ShopListActivity.this);
        rQueuee.add(request);
    }

    private void addUserToDatabase() {
        //TODO zrobić porzadek z tym czyms
        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getAddURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("")) {
                    Toast.makeText(ShopListActivity.this, "no responce", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ShopListActivity.this, "new user success", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShopListActivity.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                parameters.put("product", product.getText().toString());
                parameters.put("shop", shop.getText().toString());
                parameters.put("price", price.getText().toString());

                return parameters;
            }
        };
        RequestQueue rQueuee = Volley.newRequestQueue(ShopListActivity.this);
        rQueuee.add(request);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //TODO czy to wgle będzie potrzebne sie zastanowić
//        if (requestCode == 1) {
//
//            if (resultCode == RESULT_OK) {
//                //Update List
//                super.onRestart();
//                Toast.makeText(ShopListActivity.this, "wrociłes z poprzedniej aktywności, zrestartowałes widok", Toast.LENGTH_LONG).show();
//            }
//            if (resultCode == RESULT_CANCELED) {
//                //Do nothing?
//            }
//        }
//    }

    @Override
    public void onRestart() {
        super.onRestart();
        askDatabaseForProducts();
        product.setText("");
        shop.setText("");
        price.setText("");
        newProduct.setEnabled(false);

    }

    private boolean isText(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return true;

        return false;
    }

    private void createProductList(JSONArray jsonArray) throws JSONException {
        productList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            Product product = new Product(o.getString("product"), o.getString("store"), o.getDouble("prize"), o.getInt("quantity"));
            productList.add(product);
        }
        //ewentualne dodanie nowego pustego wiersza
        //productList.add(new Product("", "", 0.0, 0));
    }
}
