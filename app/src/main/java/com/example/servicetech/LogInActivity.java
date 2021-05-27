package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = LogInActivity.class.getSimpleName();
    EditText mail, pwd;
    Button login, register;
    ProgressBar progress;
	ProgressDialog progressDialog;

	ParseUser user;

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

        mail = findViewById(R.id.logins);
        pwd = findViewById(R.id.login_pwd);
        progress = findViewById(R.id.prg_bar);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String password = pwd.getText().toString();

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
                Intent Register = new Intent(LogInActivity.this, RegisterActivity.class);
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
                    Toast.makeText(LogInActivity.this,
                            "Authentication failed" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();

                    pwd.setText("");
                }
            }
        });

    }

    private void updateUI(ParseUser user) {
        if (user != null) {
            Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeActivity);
        } else {
            /*
            Intent reload = new Intent(LogInActivity.this, LogInActivity.class);
            startActivity(reload);
             */
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
    }
}

