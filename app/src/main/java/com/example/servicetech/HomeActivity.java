package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    public HomeFragment homeFragment;
    public DashboardFragment dashboardFragment;
    public EventFragment eventFragment;
    public AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null){
            homeFragment = new HomeFragment();
            dashboardFragment = new DashboardFragment();
            eventFragment = new EventFragment();
            aboutFragment = new AboutFragment();
        }
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                switch (item.getItemId()){
                    case  R.id.home:
                        selectedFrag = new HomeFragment();
                        break;
                    case  R.id.dash:
                        selectedFrag = new DashboardFragment();
                        break;
                    case  R.id.event:
                        selectedFrag = new EventFragment();
                        break;
                    case  R.id.about:
                        selectedFrag = new AboutFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFrag).commit();
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }
    protected void displayHome(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, homeFragment);
        ft.commit();
    }
    protected void displayDashboard(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, aboutFragment);
        ft.commit();
    }
    protected void displayEvent(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, eventFragment);
        ft.commit();
    }
    protected void displayAbout(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, aboutFragment);
        ft.commit();
    }
}