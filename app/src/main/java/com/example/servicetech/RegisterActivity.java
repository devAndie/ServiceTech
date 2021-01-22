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
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText Name, mail, address, phone, pwd, conf_Pwd;
    Button signUp, logIn;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Name = findViewById(R.id.username); mail = findViewById(R.id.mail);
        address = findViewById(R.id.address);   phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pass); conf_Pwd = findViewById(R.id.conPass);
        logIn = findViewById(R.id.log_in);
        signUp = findViewById(R.id.sign_in);

        String password = pwd.getText().toString();
        String passConf = conf_Pwd.getText().toString();

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("customers").document();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        signUp.setOnClickListener(v -> {
            if(Name.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type a username",
                        Toast.LENGTH_SHORT).show();
            }else if(mail.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type an email id",
                        Toast.LENGTH_SHORT).show();
            }else if(password.equals("")){
                Toast.makeText(RegisterActivity.this, "Please type a password",
                        Toast.LENGTH_SHORT).show();
            }else if(!passConf.equals(password)){
                Toast.makeText(RegisterActivity.this, "Password mismatch",
                        Toast.LENGTH_SHORT).show();
            }else
                confirmInput(v);
                createAccount();
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LogIn = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(LogIn);
            }
        });
    }

    private void createAccount(){
        String user = Name.getText().toString();
        String password = pwd.getText().toString();
        String Phone = phone.getText().toString();
        String email = mail.getText().toString();
        String Address = address.getText().toString();
        String passConf = conf_Pwd.getText().toString();

        ref.get().addOnSuccessListener(documentSnapshot -> {

            if (documentSnapshot.exists()){
                Toast.makeText(RegisterActivity.this, "Sorry,this user exists",
                    Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> customer = new HashMap<>();
                customer.put("Username", user);
                customer.put("Phone No", Phone);
                customer.put("Email", email);
                customer.put("Address", Address);
                customer.put("Password", password);

                auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                            //String myId = ref.getId();
                            firebaseFirestore.collection("customers")
                            .add(customer)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(RegisterActivity.this, "Registration successful",
                                        Toast.LENGTH_SHORT).show();

                                    updateUI(user);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error", e.getMessage());
                                    updateUI(null);
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
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
    public void confirmInput(View v) {
        String input = "Email: " + mail.getText().toString();   input += "\n";
        input += "Username: " + Name.getText().toString();  input += "\n";
        input += "phoneNumber: " + phone.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}