package com.example.servicetech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TechLogInActivity extends AppCompatActivity {


    private static final String TAG = TechLogInActivity.class.getSimpleName();

    EditText mail, pwd;
    Button login, register;
    ProgressBar progress;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_log_in);

        mail = findViewById(R.id.tmail);
        pwd = findViewById(R.id.tpwd);
        progress = findViewById(R.id.prg_bar);
        login = findViewById(R.id.tlogin);
        register = findViewById(R.id.tregister);

        firebaseFirestore = FirebaseFirestore.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Register = new Intent(TechLogInActivity.this, TechRegActivity.class);
                startActivity(Register);
            }
        });

    }

    public void logIn(){

        String password = pwd.getText().toString();
        String email = mail.getText().toString();

        if(mail.getText().toString().equals("")){
            Toast.makeText(TechLogInActivity.this, "Please enter valid email",
                    Toast.LENGTH_SHORT).show();
        }else if(pwd.getText().toString().equals("")){
            Toast.makeText(TechLogInActivity.this, "Please enter valid password",
                    Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();

                                updateUI(user);

                            }else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(TechLogInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent homeActivity = new Intent(getApplicationContext(), TechnicianActivity.class);
            startActivity(homeActivity);
        } else {
            Intent reload = new Intent(TechLogInActivity.this, TechLogInActivity.class);
            startActivity(reload);
        }
    }
}
