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

public class ProgressFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button mile1 = getView().findViewById(R.id.mile1);
        mile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent milestoneIntent = new Intent(getContext(), MilestoneFragment.class);
                startActivity(milestoneIntent);

            }
        });

        //super.onViewCreated(view, savedInstanceState);
    }
}
