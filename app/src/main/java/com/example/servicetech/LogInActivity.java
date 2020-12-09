package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    EditText name, password;
    private static final String TAG = "LogInActivity";


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
    public void basicReadWrite() {
        // Read from the database
        Query myRef;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }

     */

}