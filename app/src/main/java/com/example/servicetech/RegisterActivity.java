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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    public static final int MY_PASSWORD_DIALOG_ID = 4;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    EditText name, mail, address, phone, pwd, conf_Pwd;
    private String Id, Names, Mail, Phone, Address, Password, Conf;
    private Button signUp, logIn;
    private FirebaseAuth mAuth;

    private DatabaseReference customerDatabase;

    private FirebaseDatabase db;
    FirebaseUser currentUser;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance();
        customerDatabase = db.getReference("Customers");

        name = findViewById(R.id.c_name); mail = findViewById(R.id.mail);
        address = findViewById(R.id.address);   phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pass); conf_Pwd = findViewById(R.id.conPass);
        logIn = findViewById(R.id.log_in);
        signUp = findViewById(R.id.sign_in);


//        dbRef = db.getReference().child("Customers");

        firebaseFirestore=FirebaseFirestore.getInstance();

//        ref = firebaseFirestore.collection("customers").document();

        Names = name.getText().toString();
        Mail = mail.getText().toString();
        Address = address.getText().toString();
        Phone = phone.getText().toString();
        Password = pwd.getText().toString();
        Conf = conf_Pwd.getText().toString();

        signUp.setOnClickListener(v -> {
            if(name.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type your official names",
                        Toast.LENGTH_SHORT).show();
            }else if(mail.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type a valid email",
                        Toast.LENGTH_SHORT).show();
            }else if(pwd.getText().toString().equals("")){
                Toast.makeText(RegisterActivity.this, "Please type a password",
                        Toast.LENGTH_SHORT).show();
            }else if(!conf_Pwd.getText().toString().equals(pwd.getText().toString())){
                Toast.makeText(RegisterActivity.this, "Password mismatch",
                        Toast.LENGTH_SHORT).show();
            }else {
                authenticate();
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
        String  email = mail.getText().toString();
        String password = pwd.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            currentUser = mAuth.getCurrentUser();
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
        //generate id
        Id = customerDatabase.push().getKey();

        final CustomerModel customer = new
                CustomerModel(Id, Names, Mail, Address, Phone, Password);

        customerDatabase.child(Id).setValue(customer).addOnSuccessListener(aVoid -> {
            Toast.makeText(RegisterActivity.this, "Registration successful",
                    Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }).addOnFailureListener(e -> Log.d("Error", e.getMessage()));

 //       CustomerModel customer = createCustomer();
//        addDocumentToCollection(customer);
    }
    public void home() {

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
    public String GetDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());
        return currentdate;
    }
}