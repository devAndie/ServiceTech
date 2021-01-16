package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

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
    private EditText Name, mail, address, phone, password, conf_Pwd;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Name = findViewById(R.id.username); mail = findViewById(R.id.mail);
        address = findViewById(R.id.address);   phone = findViewById(R.id.phone);
        password = findViewById(R.id.pass); conf_Pwd = findViewById(R.id.conPass);

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("customers").document();


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        DBHelper dbHelper = new DBHelper(RegisterActivity.this);

        Button signUp = findViewById(R.id.sign_in);
        signUp.setOnClickListener(v -> {
            if(Name.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type a username", Toast.LENGTH_SHORT).show();
     
            }else if(mail.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type an email id", Toast.LENGTH_SHORT).show();
     
            }else if(password.getText().toString().equals("")){
                Toast.makeText(RegisterActivity.this, "Please type a password", Toast.LENGTH_SHORT).show();
     
            }else if(!conf_Pwd.getText().toString().equals(password.getText().toString())){
                Toast.makeText(RegisterActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
     
            }else {
                ref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Toast.makeText(RegisterActivity.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, Object> reg_entry = new HashMap<>();
                        reg_entry.put("Username", Name.getText().toString());
                        reg_entry.put("Phone No", phone.getText().toString());
                        reg_entry.put("Email", mail.getText().toString());
                        reg_entry.put("Address", address.getText().toString());
                        reg_entry.put("Password", password.getText().toString());

                        //String myId = ref.getId();
                        firebaseFirestore.collection("customers")
                        .add(reg_entry)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RegisterActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Error", e.getMessage());
                            }
                        });
                    }
                });
            }
            Toast.makeText(RegisterActivity.this, "Success signing in ", Toast.LENGTH_SHORT).show();

            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
            confirmInput(v);
        });
        Button logIn = findViewById(R.id.log_in);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LogIn = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(LogIn);
            }
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
        String usernameInput = Name.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            Name.setError("Field can't be empty");  return false;
        } else if (usernameInput.length() > 15) {
            Name.setError("Username too long"); return false;
        } else {
            Name.setError(null);    return true; }
    }
    public void confirmInput(View v) {
        if (!validateEmail() | !validatePhonenumber() | !validateUsername() | !validatePassword()) {
            return; }
        String input = "Email: " + mail.getText().toString();   input += "\n";
        input += "Username: " + Name.getText().toString();  input += "\n";
        input += "phoneNumber: " + phone.getText().toString();  input += "\n";
        input += "Password: " + password.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}