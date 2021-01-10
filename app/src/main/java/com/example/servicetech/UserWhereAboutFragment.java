package com.example.servicetech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class UserWhereAboutFragment extends Fragment {
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
        CardView Articles = view.findViewById(R.id.Bkmk);
        Articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });


    }

}
