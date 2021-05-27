 package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    public static final int MY_PASSWORD_DIALOG_ID = 4;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    EditText name, mail, address, phone, pwd, conf_Pwd;
    private Button signUp, logIn;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        name = findViewById(R.id.c_name); mail = findViewById(R.id.mail);
        address = findViewById(R.id.address);   phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pass); conf_Pwd = findViewById(R.id.conPass);
        signUp = findViewById(R.id.sign_in);

        signUp.setOnClickListener(v -> {
            if(name.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type your official names",
                        Toast.LENGTH_SHORT).show();
            }else if(mail.getText().toString().equals("")) {
                Toast.makeText(RegisterActivity.this, "Please type a valid email",
                        Toast.LENGTH_SHORT).show();
            }else if(pwd.getText().toString().equals("")){
                Toast.makeText(RegisterActivity.this, "Please type a password",
                        Toast.LENGTH_SHORT).show();
            }else if(!conf_Pwd.getText().toString().equals(pwd.getText().toString())){
                Toast.makeText(RegisterActivity.this, "Password mismatch",
                        Toast.LENGTH_SHORT).show();
            }else {
                authenticate();
            }
        });

    }
    public void authenticate() {
        String Names = name.getText().toString();
        String Mail = mail.getText().toString();
        String Address = address.getText().toString();
        String Phone = phone.getText().toString();
        String Password = pwd.getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(Names);
        user.setEmail(Mail);
        user.setPassword(Password);

        user.put("Phone", Phone);
        user.put("Address", Address);
        user.put("Level", "Customer");

        user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseUser user = ParseUser.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(RegisterActivity.this,
                                "Data Write failed. "
                                        + e.getMessage(),
                                Toast.LENGTH_SHORT).show();


                    }
                }
            }
        );
    }
    private void updateUI(ParseUser user) {
        if (user != null) {
            Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeActivity);
        } else {
            //do nothing
//            Intent reload = new Intent(RegisterActivity.this, RegisterActivity.class);
//            startActivity(reload);
        }
    }
    public String GetDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());
        return currentdate;
    }
}