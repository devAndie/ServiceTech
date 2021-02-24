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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    public static final int MY_PASSWORD_DIALOG_ID = 4;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    private EditText Name, mail, address, phone, pwd, conf_Pwd;
    private String Id, Names, Mail, Address, Password, Conf;
    int Phone;
    private Button signUp, logIn;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Name = findViewById(R.id.c_name); mail = findViewById(R.id.mail);
        address = findViewById(R.id.address);   phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pass); conf_Pwd = findViewById(R.id.conPass);
        logIn = findViewById(R.id.log_in);
        signUp = findViewById(R.id.sign_in);

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("customers").document();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        Names = Name.getText().toString();
        Mail = mail.getText().toString();
        Address = address.getText().toString();
        Phone = Integer.parseInt(phone.getText().toString());
        Password = pwd.getText().toString();
        Conf = conf_Pwd.getText().toString();

        signUp.setOnClickListener(v -> {
            if(Names.equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type a username",
                        Toast.LENGTH_SHORT).show();
            }else if(Mail.equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type an email id",
                        Toast.LENGTH_SHORT).show();
            }else if(Password.equals("")){
                Toast.makeText(RegisterActivity.this, "Please type a password",
                        Toast.LENGTH_SHORT).show();
            }else if(!Conf.equals(Password)){
                Toast.makeText(RegisterActivity.this, "Password mismatch",
                        Toast.LENGTH_SHORT).show();
            }else {
                ref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Intent LogIn = new Intent(this, LogInActivity.class);
                        Toast.makeText(RegisterActivity.this, "Sorry,this user exists",
                                Toast.LENGTH_SHORT).show();
                        startActivity(LogIn);
                    } else {
                        authenticate();
                    }
                });
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LogIn = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(LogIn);
            }
        });
    }
    public void authenticate(){
        auth.createUserWithEmailAndPassword(Mail, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Id = user.getUid();

                            //add doc
                            addCustomer();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    public void addCustomer(){
        CustomerModel customer = createCustomer();
        addDocumentToCollection(customer);
    }
    public CustomerModel createCustomer() {
        final CustomerModel customer = new CustomerModel();
        customer.setCustId(Id);
        customer.setNames(Names);
        customer.setMail(Mail);
        customer.setAddress(Address);
        customer.setPhone(Phone);
        customer.setPassword(Password);

        return customer;
    }

    public void addDocumentToCollection(CustomerModel customer){
        firebaseFirestore.collection("customers")
        .add(customer)
        .addOnSuccessListener(documentReference -> {
            Toast.makeText(RegisterActivity.this, "Registration successful",
                Toast.LENGTH_SHORT).show();
            FirebaseUser user = auth.getCurrentUser();
            updateUI(user);
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", e.getMessage());
            }
        });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeActivity);
        } else {
            Intent reload = new Intent(RegisterActivity.this, RegisterActivity.class);
            startActivity(reload);
        }
    }
}