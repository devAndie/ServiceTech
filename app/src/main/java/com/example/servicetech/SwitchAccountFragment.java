package com.example.servicetech;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SwitchAccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_switch_accounts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button Switch = view.findViewById(R.id.switchAcc);
        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent LogIn = new Intent(getContext(), MainActivity.class);
                startActivity(LogIn);

            }
        });
    }
}
