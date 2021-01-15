package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TechRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_reg);

        Button tchRg = findViewById(R.id.tech_rgt);
        tchRg.setOnClickListener(v -> {
            Intent TechReg = new Intent(getApplicationContext(), TechnicianActivity.class);
            startActivity(TechReg);
        });
    }
}