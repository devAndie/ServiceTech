package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class TechRegActivity extends AppCompatActivity {
    private static final String TAG = TechRegActivity.class.getSimpleName();

    EditText name, mail, phone, specialty, tether, pwd, conf_pwd;
    private String techId, Name, Phone, Mail, Specialty, Tether,
            Password, ConPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_reg);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        name = findViewById(R.id.names);
        mail = findViewById(R.id.tmail);
        phone = findViewById(R.id.tPhone);
        specialty = findViewById(R.id.specialty);
        tether = findViewById(R.id.tether);
        pwd = findViewById(R.id.tPass);
        conf_pwd = findViewById(R.id.tconPass);



        Button tchRg = findViewById(R.id.tech_rgt);
        tchRg.setOnClickListener(v -> {
            if(name.getText().toString().equals("")) {
                Toast.makeText(TechRegActivity.this, "Please type Your Official Names",
                        Toast.LENGTH_SHORT).show();

            }else if(mail.getText().toString().equals("")) {
                Toast.makeText(TechRegActivity.this, "Please type a valid email",
                        Toast.LENGTH_SHORT).show();

            }else if(pwd.getText().toString().equals("")){
                Toast.makeText(TechRegActivity.this, "Please type a password",
                        Toast.LENGTH_SHORT).show();

            }else if(!conf_pwd.getText().toString().equals(pwd.getText().toString())){
                Toast.makeText(TechRegActivity.this, "Password mismatch",
                        Toast.LENGTH_SHORT).show();
            }else {
                        authentication();
                }
        });
    }

    public void authentication(){
        Name = name.getText().toString();
        Phone = phone.getText().toString();
        Mail = mail.getText().toString();
        Specialty = specialty.getText().toString();
        Tether = tether.getText().toString();
        Password = pwd.getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(Name);
        user.setEmail(Mail);
        user.setPassword(Password);

        user.put("Phone", Phone);
        user.put("Specialty", Specialty);
        user.put("Tether", Tether);
        user.put("Level", "Technician");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    ParseUser user = ParseUser.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(TechRegActivity.this,
                            "Data Write failed. "
                                    + e.getMessage(),
                            Toast.LENGTH_SHORT).show();


                }
            }
        });


    }
    public void addTechnician(){
        //generate techid

        ParseObject technicians = new ParseObject("Technicians");

        technicians.put("techId", techId);
        technicians.put("Names", Name);
        technicians.put("email", Mail);
        technicians.put("Specialty", Specialty);
        technicians.put("Phone", Phone);
        technicians.put("Tether", Tether);
        technicians.put("Password", Password);

        technicians.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    ParseUser user = ParseUser.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(TechRegActivity.this,
                            "Authentication failed. "
                                    + e.getMessage(),
                            Toast.LENGTH_SHORT).show();


                }
            }
        });

        //create technician
//        TechnicianModel technician = createTechnicianObject();

    }
    private void updateUI(ParseUser user) {
        if (user != null) {
            Intent homeActivity = new Intent(getApplicationContext(), TechnicianActivity.class);
            startActivity(homeActivity);
        } else {
            //do nothing
        }
    }

}