package com.example.danie.mobilne;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);
        final TextView ipButton = (TextView) findViewById(R.id.ipButton);
        final EditText ip = (EditText) findViewById(R.id.ipInput);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterActivityVariablesSingleton.getInstance().setServerIp(ip.getText().toString());
                StringRequest request = new StringRequest(Request.Method.POST, InterActivityVariablesSingleton.getInstance().getLoginURL(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("true")) {
                            Toast.makeText(LoginActivity.this, "Login succesful", Toast.LENGTH_LONG).show();
                            InterActivityVariablesSingleton.getInstance().setUSER(etUsername.getText().toString());
                            InterActivityVariablesSingleton.getInstance().setPASSWORD(etPassword.getText().toString());
                            Intent productScreen = new Intent(LoginActivity.this, ShopListActivity.class);
                            productScreen.putExtra("username",etUsername.getText().toString());
                            startActivity(productScreen);
                            //finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
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
                RequestQueue rQueuee = Volley.newRequestQueue(LoginActivity.this);
                rQueuee.add(request);
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterActivityVariablesSingleton.getInstance().setServerIp(ip.getText().toString());
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        ipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip.setVisibility(View.VISIBLE);
            }
        });


    }
}
