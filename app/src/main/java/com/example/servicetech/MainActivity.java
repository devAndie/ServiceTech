package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button login, tech;
    TextView head, intro;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.customer);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Customer = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(Customer);
            }
        });

        tech = findViewById(R.id.technician);
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Tech = new Intent(getApplicationContext(), TechLogInActivity.class);
                startActivity(Tech);
            }
        });
    }

}