package com.example.servicetech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

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

        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
            // No user is signed in
        } else {
            updateUI(user);
            // User logged in
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //initialize parse server
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        mail = findViewById(R.id.logins);
        pwd = findViewById(R.id.login_pwd);
        progress = findViewById(R.id.prg_bar);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = pwd.getText().toString();
                String email = mail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mail.setError("Enter names registered with this app");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    pwd.setError("This field can't be empty");
                    return;
                } else
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
        String email = mail.getText().toString();
        String password = pwd.getText().toString();

        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Log.d(TAG, "signInWithEmail:success");

                    updateUI(user);
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Log.w(TAG, "signInWithEmail:failure");
                    Toast.makeText(TechLogInActivity.this,
                            "Authentication failed" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateUI(ParseUser user) {
        if (user != null) {
            Intent homeActivity = new Intent(getApplicationContext(), TechnicianActivity.class);
            startActivity(homeActivity);
        } else {

//            Intent reload = new Intent(TechLogInActivity.this, TechLogInActivity.class);
//            startActivity(reload);
        }
    }
}
