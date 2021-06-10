package com.example.servicetech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class Launcher extends AppCompatActivity {
    public Launcher() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ParseUser.getCurrentUser() != null){
            SharedPreferences Level = getSharedPreferences("Level",
                    Context.MODE_PRIVATE);
            String userLevel = ParseUser.getCurrentUser().getString("Level");

            if (userLevel == "Customer"){
                startActivity(new Intent(this, HomeActivity.class));

            } else if (userLevel == "Technician") {
                startActivity(new Intent(this, TechnicianActivity.class));
            }else {
                startActivity(new Intent(this, MainActivity.class));
            }
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
