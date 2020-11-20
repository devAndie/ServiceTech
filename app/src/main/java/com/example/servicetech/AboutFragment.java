package com.example.servicetech;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    ImageButton profile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView user = view.findViewById(R.id.user);
        TextView level = view.findViewById(R.id.uslevl);

        Button userUp = view.findViewById(R.id.up_ul);
        userUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent techReg = new Intent(getContext(), TechRegActivity.class);
                startActivity(techReg);
            }
        });

        ImageButton profile = view.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent viewimg = new Intent(Intent.ACTION_VIEW, )
            }
        });

        CardView msg = view.findViewById(R.id.msg_card);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //super.onViewCreated(view, savedInstanceState);
    }
}
