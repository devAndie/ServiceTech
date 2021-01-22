package com.example.servicetech;

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

import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    RequestServiceFragment requestServiceFragment;
    ProgressFragment progressFragment;
    StakesFragment stakesFragment;
    FragmentActivity listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            requestServiceFragment = new RequestServiceFragment();
            progressFragment = new ProgressFragment();
            stakesFragment = new StakesFragment();
        }
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //super.onViewCreated(view, savedInstanceState);
    }

    private void insertNestedFragment(){
        Fragment newFragment = new RequestServiceFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment).commit();

        NavigationView nav = getView().findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                switch (item.getItemId()){
                    case  R.id.new_d:
                        selectedFrag = new RequestServiceFragment();
                        break;
                    case  R.id.prog_dr:
                        selectedFrag = new ProgressFragment();
                        break;
                    case  R.id.book_d:
                        selectedFrag = new StakesFragment();
                        break;
                }
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFrag).commit();
                return true;

            }
        });
    }

}
