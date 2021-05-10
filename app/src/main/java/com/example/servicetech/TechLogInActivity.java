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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TechLogInActivity extends AppCompatActivity {


    private static final String TAG = TechLogInActivity.class.getSimpleName();

    EditText mail, pwd;
    Button login, register;
    ProgressBar progress;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    DocumentReference ref;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // No user is signed in
        } else {
            updateUI(currentUser);
            // User logged in
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mail = findViewById(R.id.logins);
        pwd = findViewById(R.id.login_pwd);
        progress = findViewById(R.id.prg_bar);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth auth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(TechLogInActivity.this, TechnicianActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent techHome = new Intent(getApplicationContext(), TechnicianActivity.class);
                startActivity(techHome);
/*
                if(mail.getText().toString().equals("")){
                    Toast.makeText(TechLogInActivity.this, "Please enter valid email",
                            Toast.LENGTH_SHORT).show();
                }else if(pwd.getText().toString().equals("")){
                    Toast.makeText(TechLogInActivity.this, "Please enter valid password",
                            Toast.LENGTH_SHORT).show();
                } else
                    logIn();

 */
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

        auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(TechLogInActivity.this, new OnCompleteListener<AuthResult>() {
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
