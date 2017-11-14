package com.example.danie.mobilne;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateCreateDelete extends AppCompatActivity {
    private Product prod;
    private TextView productName;
    private TextView productShop;
    private TextView productPrice;
    private TextView quantity;
    private EditText quantityCrud;
    private Button deleteButton;
    private Button increaseButton;
    private Button decreaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_create_delete);
        initElements();
        setProductValues();
        setButtonBehavior();
    }


    private void initElements() {

        productName = (TextView) findViewById(R.id.crudProductName);
        productShop = (TextView) findViewById(R.id.crudProductShop);
        productPrice = (TextView) findViewById(R.id.crudProductPrice);
        quantity = (TextView) findViewById(R.id.quantity);
        quantityCrud = (EditText) findViewById(R.id.crudProductQuantity);
        deleteButton = (Button) findViewById(R.id.crudDeleteButton);
        increaseButton = (Button) findViewById(R.id.incButton);
        decreaseButton = (Button) findViewById(R.id.decButton);
    }

    private void setProductValues() {
        prod = (Product) getIntent().getSerializableExtra("product");
        String name = prod.getProductName();
        String shop = prod.getStore();
        String price = Double.toString(prod.getPrice()) + " zł";
        String quanti = Integer.toString(prod.getQuantity()) + " szt";

        productName.setText(name);
        productShop.setText(shop);
        productPrice.setText(price);
        quantity.setText(quanti);
    }

    private void setButtonBehavior() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO request do kasowania produktów

                deleteProduct();
//                Intent returnIntent = new Intent();
//                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO request do update decrease
                updateProductQuantity(-Integer.parseInt(quantityCrud.getText().toString()));
//                Intent returnIntent = new Intent();
//                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO request do update increase
                updateProductQuantity(Integer.parseInt(quantityCrud.getText().toString()));
//                Intent returnIntent = new Intent();
//                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void deleteProduct() {

        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getDeleteURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("")) {
                    Toast.makeText(UpdateCreateDelete.this, "no responce", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateCreateDelete.this, "delete success", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateCreateDelete.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                parameters.put("password", InterActivityVariablesSingleton.getInstance().getPASSWORD());
                parameters.put("product", prod.getProductName());
                return parameters;
            }


        };
        RequestQueue rQueue = Volley.newRequestQueue(UpdateCreateDelete.this);
        rQueue.add(request);
    }

    private void updateProductQuantity(final int delta) {
        final int quantity = prod.getQuantity();
        final int newQ = evaluateQuantity(quantity, delta);

        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getUpdateURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("")) {
                    Toast.makeText(UpdateCreateDelete.this, "no responce", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateCreateDelete.this, "update success", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateCreateDelete.this, "Some error occurred in update -> " + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                parameters.put("password", InterActivityVariablesSingleton.getInstance().getPASSWORD());
                parameters.put("product", prod.getProductName());
                parameters.put("quantity", Integer.toString(newQ));
                return parameters;
            }


        };
        RequestQueue rQueue = Volley.newRequestQueue(UpdateCreateDelete.this);
        rQueue.add(request);
    }

    private int evaluateQuantity(final int quantity, final int delta) {
        if (quantity - delta <= 0) {
            return 0;
        } else return quantity - delta;
    }
}
