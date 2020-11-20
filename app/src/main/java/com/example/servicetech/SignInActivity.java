package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    public static final int MY_PASSWORD_DIALOG_ID = 4;
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");

    EditText phone = findViewById(R.id.phone);
    EditText password = findViewById(R.id.pass);
    EditText confirmPassword = findViewById(R.id.conPass);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button signIn = findViewById(R.id.signUp);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                validatePhonenumber();
                validatePassword();
            }
        });
    }
    public boolean validatePhonenumber(){
        String phoneInput = phone.getEditableText().toString().trim();

        if (phoneInput.isEmpty()) {
            phone.setError("Field can't be empty");
            return false;
        }else {
            phone.setError(null);

            return true;
        }
    }
    public boolean validatePassword() {
        String passwordInput = password.getEditableText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }

    
}