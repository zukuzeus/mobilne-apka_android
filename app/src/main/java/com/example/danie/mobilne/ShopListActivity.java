package com.example.danie.mobilne;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        listView = (ListView) findViewById(R.id.product_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), Integer.toString(position),
                        Toast.LENGTH_SHORT).show();

                Intent singleProductScreen = new Intent(ShopListActivity.this, UpdateCreateDelete.class);
                singleProductScreen.putExtra("product", productList.get(position));

                startActivity(singleProductScreen);
            }
        });

        //final String username = getIntent().getStringExtra("username");
        final String username = InterActivityVariablesSingleton.getInstance().getUSER();

        //TODO zrobić porzadek z tym czyms
        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getProductsURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("")) {
                    Toast.makeText(ShopListActivity.this, "data is not available", Toast.LENGTH_LONG).show();
                    //text.setText(s);
                    // finish();
                } else {
                    Toast.makeText(ShopListActivity.this, s, Toast.LENGTH_LONG).show();
                }
//
                try {
                    createProductList(new JSONArray(s));
//                    JSONArray jsonArray = new JSONArray(s);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject o = jsonArray.getJSONObject(i);
//                        Product product = new Product(o.getString("product"), o.getString("store"), o.getDouble("prize"), o.getInt("quantity"));
//                        productList.add(product);
//                    }
                    //ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, R.id.listView1, productList.toArray());
                    Toast.makeText(ShopListActivity.this, productList.toString(), Toast.LENGTH_LONG).show();
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

                parameters.put("username", username);
                return parameters;
            }

        };

        RequestQueue rQueuee = Volley.newRequestQueue(ShopListActivity.this);
        rQueuee.add(request);


    }

    private void createProductList(JSONArray jsonArray) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            Product product = new Product(o.getString("product"), o.getString("store"), o.getDouble("prize"), o.getInt("quantity"));
            productList.add(product);
        }
    }
}
