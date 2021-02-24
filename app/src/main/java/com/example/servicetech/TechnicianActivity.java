package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TechnicianActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private DrawerLayout nDrawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent Login = new Intent(getApplicationContext(), TechLogInActivity.class);
            startActivity(Login);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_home);

        navigationView = findViewById(R.id.t_nav_view);
        toolbar = findViewById(R.id.toolbar_main);
        nDrawer = findViewById(R.id.t_draw_lay);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        nDrawer.addDrawerListener(drawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawerContent(navigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.tech_container,
                new ListingFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, nDrawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                }
        );
    }
    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.td_listing:
                fragmentClass = ListingFragment.class;
                break;
            case R.id.td_schedule:
                fragmentClass = ScheduleFragment.class;
                break;
            case R.id.td_setting:
                fragmentClass = SettingsActivity.class;
                break;
            case R.id.cd_wallet:
                fragmentClass = PaymentFragment.class;
                break;
            case R.id.swapt:
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (id == R.id.td_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(),TechLogInActivity.class);
            startActivity(loginActivity);
            finish();
        }

        fragmentManager.beginTransaction().replace(R.id.tech_container, fragment).commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());


        nDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (nDrawer.isDrawerOpen(GravityCompat.START)){
            nDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

}