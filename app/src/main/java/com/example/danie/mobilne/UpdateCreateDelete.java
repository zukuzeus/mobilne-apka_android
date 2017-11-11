package com.example.danie.mobilne;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UpdateCreateDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_create_delete);
        Product prod = (Product) getIntent().getSerializableExtra("product");

    }
}
