package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button login, signup;
    TextView head, intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.log1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(loginIntent);
            }
        });

        signup = findViewById(R.id.signUp);
        signup.setOnClickListener(v -> {
            Intent signupIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(signupIntent);
        });
    }
}