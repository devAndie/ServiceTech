package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    Button login, tech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
            // No user is signed in
        } else {
            // User logged in

            String level = ParseUser.getCurrentUser().getString("Level");

            if (level == "Customer"){
                Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeActivity);
//            finish();
            } else if(user.getString("Level") == "Technician"){
                Intent homeActivity = new Intent(MainActivity.this, TechnicianActivity.class);
                startActivity(homeActivity);
//            finish();
            }
        }

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