package com.example.servicetech;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EventFragment extends Fragment {

    public NewFragment newFragment;
    public ProgressFragment progressFragment;
    public StakesFragment stakesFragment;

    FragmentActivity listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            newFragment = new NewFragment();
            progressFragment = new ProgressFragment();
            stakesFragment = new StakesFragment();
        }
        /**/

        getChildFragmentManager().beginTransaction().replace(R.id.event_container,
                new HomeFragment()).commit();

    return inflater.inflate(R.layout.fragment_event, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        insertNestedFragment();
        //super.onViewCreated(view, savedInstanceState);
    }
    private void insertNestedFragment(){
        Fragment newFragment = new NewFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, newFragment).commit();

        BottomNavigationView topnav = getView().findViewById(R.id.top_nav);
        topnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                switch (item.getItemId()){
                    case  R.id.add:
                        selectedFrag = new NewFragment();
                        break;
                    case  R.id.pending:
                        selectedFrag = new ProgressFragment();
                        break;
                    case  R.id.stake:
                        selectedFrag = new StakesFragment();
                        break;
                }
                getChildFragmentManager().beginTransaction().replace(R.id.event_container,
                        selectedFrag).commit();
                return true;
            }
        });
    }
}
