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

public class NewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new, container, false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button submit = getView().findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            Intent bookIntent = new Intent(getContext(), BookFragment.class);
            startActivity(bookIntent);
        });

        Button locPin = getView().findViewById(R.id.pin);
        locPin.setOnClickListener(v -> {
            //Intent locationPin = new Intent get my location
            //startActivity(locationPin);
        });

        //super.onViewCreated(view, savedInstanceState);
    }
}
