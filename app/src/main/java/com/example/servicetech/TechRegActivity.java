package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TechRegActivity extends AppCompatActivity {
    private static final String TAG = TechRegActivity.class.getSimpleName();

    EditText name, mail, phone, specialty, tether, pwd, conf_pwd;
    private String TechId, Name, Phone, Mail, Specialty, Tether, Password, ConPass;
    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_reg);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("Technicians").document();

        name = findViewById(R.id.names);
        mail = findViewById(R.id.tmail);
        phone = findViewById(R.id.tPhone);
        specialty = findViewById(R.id.specialty);
        tether = findViewById(R.id.tether);
        pwd = findViewById(R.id.tPass);
        conf_pwd = findViewById(R.id.tconPass);

        Name = name.getText().toString();
        Phone = phone.getText().toString();
        Mail = mail.getText().toString();
        Specialty = specialty.getText().toString();
        Tether = tether.getText().toString();
        Password = pwd.getText().toString();
        ConPass = conf_pwd.getText().toString();

        Button tchRg = findViewById(R.id.tech_rgt);
        tchRg.setOnClickListener(v -> {
            if(Name.equals("")) {
                Toast.makeText(TechRegActivity.this, "Please type a username",
                        Toast.LENGTH_SHORT).show();

            }else if(Mail.equals("")) {
                Toast.makeText(TechRegActivity.this, "Please type an email id",
                        Toast.LENGTH_SHORT).show();

            }else if(Password.equals("")){
                Toast.makeText(TechRegActivity.this, "Please type a password",
                        Toast.LENGTH_SHORT).show();

            }else if(!ConPass.equals(Password)){
                Toast.makeText(TechRegActivity.this, "Password mismatch",
                        Toast.LENGTH_SHORT).show();
            }else {
                ref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Toast.makeText(TechRegActivity.this, "Sorry,this user exists",
                                Toast.LENGTH_SHORT).show();
                        Intent LogIn = new Intent(this, LogInActivity.class);
                        startActivity(LogIn);
                    } else {
                        authentication();
                    }
                });
            }
        });
    }

    public void authentication(){
        mAuth.createUserWithEmailAndPassword(Mail, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            TechId = user.getUid();

                            //add doc
                            addTechnician();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(TechRegActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    public void addTechnician(){
        TechnicianModel technician = createTechnicianObject();
        addDocumentToCollection(technician);
    }
    public TechnicianModel createTechnicianObject(){
        final TechnicianModel technician = new TechnicianModel();
        technician.setId(TechId);
        technician.setNames(Name);
        technician.setMail(Mail);
        technician.setPhoneNo(Phone);
        technician.setTether(Tether);
        technician.setSpecialty(Specialty);
        technician.setPassword(Password);

        return technician;
    }
    public void addDocumentToCollection(TechnicianModel technician){
        firebaseFirestore.collection("technicians")
        .add(technician)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Event document added - id: "
                        + documentReference.getId());
                Toast.makeText(TechRegActivity.this,
                        "Event document has been added",
                        Toast.LENGTH_SHORT).show();
                Intent Home = new Intent(TechRegActivity.this, TechnicianActivity.class);
                startActivity(Home);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding event document", e);
                Toast.makeText(TechRegActivity.this,
                        "Event document could not be added",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}