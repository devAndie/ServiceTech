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

public class LogInActivity extends AppCompatActivity {

    EditText name, password;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText name = findViewById(R.id.user);
        EditText password = findViewById(R.id.pass);


        Button logIn =findViewById(R.id.log2);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                //validInputs();
                //validateUsername();
                //validatePassword();
            }
        });
    }

    /*public void validInputs() {
        EditText username = findViewById(R.id.user);
        EditText password = findViewById(R.id.pass);

        String validUsername = "";
        String Username = username.getText().toString();
        Matcher matcher = Pattern.compile(validUsername).matcher(Username);
        if (matcher.matches()){
            Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Enter valid Username", Toast.LENGTH_LONG).show();
        }
        if (password.getText().toString().equals("")){
            password.setError("Enter password");
        }
        else {
            password.setError(null);
        }
    }*/

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