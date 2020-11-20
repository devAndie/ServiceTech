package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                validInputs();
                validateUsername();
                validatePhonenumber();
                validatePassword();
            }
        });
    }
    public boolean validInputs() {
        EditText phone = findViewById(R.id.phone);
        EditText username = findViewById(R.id.name);
        EditText Password = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmPass);

        String validUsername = "";
        String Username = username.getText().toString();
        Matcher matcher = Pattern.compile(validUsername).matcher(Username);
        if (matcher.matches()){
            Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Enter valid Username", Toast.LENGTH_LONG).show();
        }
        if (confirmPassword.getText().toString().equals("")){
            confirmPassword.setError("Enter password");
        };
    }
    public boolean validatePhonenumber(){
        String passwordInput = phone.getEditableText().toString().trim();

        if (passwordInput.isEmpty()) {
            phone.setError("Field can't be empty");
            return false;
        }else {
            phone.setError(null);

            return true;
        }
    }
    private boolean validateUsername() {
        String nameInput = name.getEditableText().toString().trim();
        if (nameInput.isEmpty()) {
            name.setError("Field can't be empty");
            return false;
        }else if (nameInput.length() > 12) {
            name.setError("Username too long");
            return false;
        }else {
            name.setError(null);
            return  true;
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