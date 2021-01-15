package com.example.servicetech;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class ListingFragment extends Fragment {

    FragmentActivity listener;
    StakeAdapter stakeAdapter;
    String[] technician, rItems, description, cost;

    Context thisContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listings, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.listList);

        Resources res = getResources();
        technician = res.getStringArray(R.array.technicians);
        rItems = res.getStringArray(R.array.rItems);
        cost = res.getStringArray(R.array.cost);
        description = res.getStringArray(R.array.description);

        listView.setAdapter(stakeAdapter);
        stakeAdapter = new StakeAdapter(thisContext, technician, rItems, description);

    }



}
