package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.android.SqlPersistenceStorageEngine;

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
    private static final String TAG = "MainActivity";
    private EditText fName, sName, mail, address, phone, password, confirmPassword, username;

   // private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
     //   auth = FirebaseAuth.getInstance();

        fName = findViewById(R.id.fName);   sName = findViewById(R.id.surName);
        username = findViewById(R.id.username); mail = findViewById(R.id.mail);
        address = findViewById(R.id.address);   phone = findViewById(R.id.phone);
        password = findViewById(R.id.pass); confirmPassword = findViewById(R.id.conPass);

        Button signIn = findViewById(R.id.sign_in);
        signIn.setOnClickListener(v -> {
            CustomerModel customerModel = null;
            try {
                customerModel = new CustomerModel(-1, fName.getText().toString(),
                        sName.getText().toString(), username.getText().toString(), mail.getText().toString(),
                        address.getText().toString(), Integer.parseInt(phone.getText().toString()),
                        password.getText().toString()
                );
                Toast.makeText(SignInActivity.this, "account created successfully", Toast.LENGTH_SHORT).show(); ;
            } catch (Exception e) {
                Toast.makeText(SignInActivity.this, "error creating account", Toast.LENGTH_SHORT).show();
            }
            DBHelper dbHelper = new DBHelper(SignInActivity.this);
            boolean success = dbHelper.addCustomers(customerModel);
            Toast.makeText(SignInActivity.this, "Success= " +success, Toast.LENGTH_SHORT).show();

            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
            confirmInput(v);
            uploadFile();
        });
    }

    public boolean validatePhonenumber(){
        String phoneInput = phone.getText().toString().trim();
        if (phoneInput.isEmpty()) {
            phone.setError("Field can't be empty"); return false;
        }else { phone.setError(null);   return true; }
    }
    private boolean validateEmail() {
        String emailInput = mail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            mail.setError("Field can't be empty");  return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mail.setError("Please enter a valid email address");    return false;
        } else { mail.setError(null);    return true; }
    }
    public boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty");  return false;
        }else { password.setError(null);    return true; }
    }
    private boolean validateUsername() {
        String usernameInput = username.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            username.setError("Field can't be empty");  return false;
        } else if (usernameInput.length() > 15) {
            username.setError("Username too long"); return false;
        } else {
            username.setError(null);    return true; }
    }
    public void confirmInput(View v) {
        if (!validateEmail() | !validatePhonenumber() | !validateUsername() | !validatePassword()) {
            return; }
        String input = "Email: " + mail.getText().toString();   input += "\n";
        input += "Username: " + username.getText().toString();  input += "\n";
        input += "phoneNumber: " + phone.getText().toString();  input += "\n";
        input += "Password: " + password.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
    private void uploadFile(){
       // SqlPersistenceStorageEngine storageEngine = SqlPersistenceStorageEngine.child() ;

    }
    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database
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
}