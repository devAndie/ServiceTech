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

public class TechnicianActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private DrawerLayout nDrawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_home);

        bottomNav = findViewById(R.id.bottomNav_t);
        bottomNav.setOnNavigationItemSelectedListener(navList);

        navigationView = findViewById(R.id.t_nav_view);
        toolbar = findViewById(R.id.toolbar_main);
        nDrawer = findViewById(R.id.t_draw_lay);

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
    private BottomNavigationView.OnNavigationItemSelectedListener
            navList = item -> {
        Fragment selectedFrag = null;
        switch (item.getItemId()) {
            case R.id.listng:
                selectedFrag = new ListingFragment();
                break;
            case R.id.tprogress:
                selectedFrag = new ProgressFragment();
                break;
            case R.id.quat:
                selectedFrag = new ScheduleFragment();
                break;
            case R.id.tabout:
                selectedFrag = new UserWhereAboutFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.tech_container,
                selectedFrag).commit();
        return true;
    };

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
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.new_d:
                fragmentClass = RequestServiceFragment.class;
                break;
            case R.id.settings:
                fragmentClass = BookFragment.class;
                break;
            case R.id.prog_dr:
                fragmentClass = ProgressFragment.class;
                break;
            case R.id.pay_dr:
                fragmentClass = PaymentFragment.class;
                break;
            case R.id.swapt:
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                break;
            case R.id.logout:
                Intent logIn = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(logIn);
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
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