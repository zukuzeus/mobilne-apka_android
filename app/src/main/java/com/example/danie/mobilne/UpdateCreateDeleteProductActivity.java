package com.example.danie.mobilne;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.danie.mobilne.CustomActivities.ActivityWithDatabase;
import com.example.danie.mobilne.ShopList.Product;

import java.util.HashMap;
import java.util.Map;

public class UpdateCreateDeleteProductActivity extends ActivityWithDatabase {
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
        deleteButton.setOnClickListener(v -> {
            //TODO request do kasowania produktów
            //deleteProductFromServer();
            deleteProductFromLocalDB();
//                Intent returnIntent = new Intent();
//                setResult(RESULT_CANCELED, returnIntent);
            finish();
        });
        increaseButton.setOnClickListener(v -> {
            //TODO request do update decrease
            //updateProductQuantityOnServer(-Integer.parseInt(quantityCrud.getText().toString()));
            updateProductQuantityOnLocalDB(-Integer.parseInt(quantityCrud.getText().toString()));
//                Intent returnIntent = new Intent();
//                setResult(RESULT_OK, returnIntent);
            finish();
        });
        decreaseButton.setOnClickListener(v -> {
            //TODO request do update increase
            //updateProductQuantityOnServer(Integer.parseInt(quantityCrud.getText().toString()));
            updateProductQuantityOnLocalDB(Integer.parseInt(quantityCrud.getText().toString()));
//                Intent returnIntent = new Intent();
//                setResult(RESULT_OK, returnIntent);
            finish();
        });
    }

    private void deleteProductFromServer() {

        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getDeleteURL(), s -> {
            if (s.equals("")) {
                Toast.makeText(UpdateCreateDeleteProductActivity.this, "no responce", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(UpdateCreateDeleteProductActivity.this, "delete success", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(UpdateCreateDeleteProductActivity.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("username", InterActivityVariablesSingleton.getInstance().getUSER());
                parameters.put("password", InterActivityVariablesSingleton.getInstance().getPASSWORD());
                parameters.put("product", prod.getProductName());
                return parameters;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(UpdateCreateDeleteProductActivity.this);
        rQueue.add(request);
    }

    private void deleteProductFromLocalDB() {
        this.DATABASE.deleteData(prod);

    }

    private void updateProductQuantityOnServer(final int delta) {
        final int quantity = prod.getQuantity();
        final int newQ = evaluateQuantity(quantity, delta);

        StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getUpdateURL(), s -> {
            if (s.equals("")) {
                Toast.makeText(UpdateCreateDeleteProductActivity.this, "no responce", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(UpdateCreateDeleteProductActivity.this, "update success", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(UpdateCreateDeleteProductActivity.this, "Some error occurred in update -> " + error, Toast.LENGTH_LONG).show()) {
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
        RequestQueue rQueue = Volley.newRequestQueue(UpdateCreateDeleteProductActivity.this);
        rQueue.add(request);
    }

    private void updateProductQuantityOnLocalDB(final int delta) {
        final int quantity = prod.getQuantity();
        final int newQ = evaluateQuantity(quantity, delta);
        if (this.DATABASE.updateData(prod, newQ)) {
            Toast.makeText(UpdateCreateDeleteProductActivity.this, "update locally success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UpdateCreateDeleteProductActivity.this, "update locally failed!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private int evaluateQuantity(final int quantity, final int delta) {
        if (quantity - delta <= 0) {
            return 0;
        } else return quantity - delta;
    }
}
