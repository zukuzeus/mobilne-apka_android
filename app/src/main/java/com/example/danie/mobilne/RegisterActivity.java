package com.example.danie.mobilne;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bRegister = (Button) findViewById(R.id.bRegister);



        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getRegisterURL(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        if (s.equals("true")) {
                            Toast.makeText(RegisterActivity.this, "registration Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else if (s.equals("false")) {
                            Toast.makeText(RegisterActivity.this, "user already exist, Can't register", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Some error occurred ->" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("username", etUsername.getText().toString());
                        parameters.put("password", etPassword.getText().toString());
                        return parameters;
                    }
                };
                RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
                rQueue.add(request);
            }
        });

    }
}
