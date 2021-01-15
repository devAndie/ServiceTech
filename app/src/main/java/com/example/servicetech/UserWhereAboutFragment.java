package com.example.servicetech;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

//import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UserWhereAboutFragment extends Fragment {
    private int STORAGE_PERMISSION_CODE = 23;
    CircularImageView imageView;
    SQLiteDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_wherebouts, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        CardView regTech = view.findViewById(R.id.RegTech);
        regTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent techReg = new Intent(getContext(), TechRegActivity.class);
                startActivity(techReg);
            }
        });
        CardView Wallet = view.findViewById(R.id.wallet);
        Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Payment = new Intent(getContext(), PaymentFragment.class);
                startActivity(Payment);
            }
        });
        CardView SwitchUser = view.findViewById(R.id.switchU);
        SwitchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LogIn = new Intent(getContext(), LogInActivity.class);
                startActivity(LogIn);
            }
        });
        CardView settings = view.findViewById(R.id.set);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Settings = new Intent(getContext(), SettingsActivity.class);
                startActivity(Settings);
            }
        });
        CardView LogOut = view.findViewById(R.id.logOut);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent LogIn = new Intent(getContext(), LogInActivity.class);
               startActivity(LogIn);
            }
        });
    }

}
