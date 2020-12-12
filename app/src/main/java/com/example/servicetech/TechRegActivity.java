package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TechRegActivity extends AppCompatActivity {

    Button techReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_reg);

        techReg = findViewById(R.id.tech_reg);
        techReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Techact = new Intent(getApplicationContext(), TechnicianActivity.class);
                startActivity(Techact);
            }
        });

    }
}