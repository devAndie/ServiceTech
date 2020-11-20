package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInActivity extends AppCompatActivity {

    EditText phone = findViewById(R.id.phone);
    EditText username = findViewById(R.id.name);
    EditText Password = findViewById(R.id.password);
    EditText confirmPassword = findViewById(R.id.confirmPass);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button logIn =findViewById(R.id.log2);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                validInputs();
                validateUsername();
                validatePhonenumber();
                validatePassword();
                dialog.setTitle("connecting.....");
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