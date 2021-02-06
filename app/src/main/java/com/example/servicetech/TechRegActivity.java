package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TechRegActivity extends AppCompatActivity {
    EditText Name, mail, phone, specialty, tether, pwd, conf_pwd;
    private FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_reg);

        firebaseFirestore= FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("Technicians").document();


        Button tchRg = findViewById(R.id.tech_rgt);
        tchRg.setOnClickListener(v -> {
            if(Name.getText().toString().equals("")) {
                Toast.makeText(TechRegActivity.this, "Please type a username", Toast.LENGTH_SHORT).show();

            }else if(mail.getText().toString().equals("")) {
                Toast.makeText(TechRegActivity.this, "Please type an email id", Toast.LENGTH_SHORT).show();

            }else if(pwd.getText().toString().equals("")){
                Toast.makeText(TechRegActivity.this, "Please type a password", Toast.LENGTH_SHORT).show();

            }else if(!conf_pwd.getText().toString().equals(pwd.getText().toString())){
                Toast.makeText(TechRegActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();

            }else {
                ref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Toast.makeText(TechRegActivity.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, Object> Technician = new HashMap<>();
                        Technician.put("Name", Name.getText().toString());
                        Technician.put("Phone No", phone.getText().toString());
                        Technician.put("Email", mail.getText().toString());
                        Technician.put("Specialty", specialty.getText().toString());
                        Technician.put("Tether", tether.getText().toString());
                        Technician.put("Password", pwd.getText().toString());

                        String myId = ref.getId();

                        firebaseFirestore.collection("Technicians")
                                .add(Technician)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(TechRegActivity.this, "Successfully added",
                                                Toast.LENGTH_SHORT).show();
                                        Intent TechHome = new Intent(getApplicationContext(), TechnicianActivity.class);
                                        startActivity(TechHome);

                                        Toast.makeText(TechRegActivity.this, "Registration Successful",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Error", e.getMessage());
                                        Intent reload = new Intent(TechRegActivity.this, TechRegActivity.class);
                                        startActivity(reload);
                                    }
                                });
                    }
                });
            }

            Intent TechHome = new Intent(getApplicationContext(), TechnicianActivity.class);
            startActivity(TechHome);
        });
    }
}