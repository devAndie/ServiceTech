package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LogInActivity extends AppCompatActivity {
    EditText logIns, pwd;
    Button login, register;
    ProgressBar progress;
    private static final String TAG = "LogInActivity";
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logIns = findViewById(R.id.logins);
        pwd = findViewById(R.id.login_pwd);
        progress = findViewById(R.id.prg_bar);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        firebaseFirestore = FirebaseFirestore.getInstance();
        login.setOnClickListener(this::onClick);
        register.setOnClickListener(this::onClick);

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_login:
                if(logIns.getText().toString().equals("")){
                    Toast.makeText(LogInActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else if( pwd.getText().toString().equals("")){
                    Toast.makeText(LogInActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
                firebaseFirestore.collection("client")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot doc : task.getResult()){
                                        String a=doc.getString("Email");
                                        String b=doc.getString("Password");
                                        String a1 = logIns.getText().toString().trim();
                                        String b1 = pwd.getText().toString().trim();
                                        if(a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                            Intent home = new Intent(LogInActivity.this, HomeActivity.class);
                                            startActivity(home);
                                            Toast.makeText(LogInActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                            break;
                                        }else
                                            Toast.makeText(LogInActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                break;
            case R.id.btn_register:
                Intent register_view=new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(register_view);
                break;
        }
    }
}