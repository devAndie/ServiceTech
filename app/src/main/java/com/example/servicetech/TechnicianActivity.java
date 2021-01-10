package com.example.servicetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TechnicianActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);

        bottomNav = findViewById(R.id.bottomNav_t);
        bottomNav.setOnNavigationItemSelectedListener(navList);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ListingFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener
            navList = item -> {
        Fragment selectedFrag = null;
        switch (item.getItemId()) {
            case R.id.listng:
                selectedFrag = new ListingFragment();
                break;
            case R.id.event:
                selectedFrag = new ProgressFragment();
                break;
            case R.id.quat:
                selectedFrag = new QuotationFragment();
                break;
            case R.id.tabout:
                selectedFrag = new UserWhereAboutFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.tech_container,
                selectedFrag).commit();
        return true;
    };

}