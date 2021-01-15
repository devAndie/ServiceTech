package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    EditText name, password;
    private static final String TAG = "LogInActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button logIn =findViewById(R.id.log2);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(HomeIntent);
            }
        });
    }
}