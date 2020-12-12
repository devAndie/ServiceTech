package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button login, signup;
    TextView head, intro;

    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
//No one signed in
            startActivity(new Intent(this, SignInActivity.class));
            this.finish();
        }else{

        }

        */
        Button login = findViewById(R.id.log1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(loginIntent);
            }
        });

        Button signup = findViewById(R.id.signUp);
        signup.setOnClickListener(v -> {
            Intent signupIntent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(signupIntent);
        });
    }
}