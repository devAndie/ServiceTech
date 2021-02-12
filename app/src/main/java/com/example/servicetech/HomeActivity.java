 package com.example.servicetech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private BottomNavigationView bottomNav;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentActivity listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);

        mDrawer = findViewById(R.id.draw_lay);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_main);

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navList);

        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        mDrawer.addDrawerListener(drawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawerContent(navigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RequestServiceFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar,
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
    private BottomNavigationView.OnNavigationItemSelectedListener
            navList = item -> {
                Fragment selectedFrag = null;
                switch (item.getItemId()) {
                    case R.id.request:
                        selectedFrag = new RequestServiceFragment();
                        break;
                    case R.id.event:
                        selectedFrag = new DashboardFragment();
                        break;
                    case R.id.schedule:
                        selectedFrag = new AppointmentsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFrag).commit();
                return true;
            };

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.new_d:
                fragmentClass = RequestServiceFragment.class;
                break;
            case R.id.prog_dr:
                fragmentClass = ProgressFragment.class;
                break;
            case R.id.pay_dr:
                fragmentClass = WalletFragment.class;
                break;
            case R.id.settings:
                fragmentClass = SettingsActivity.class;
//                Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
//                startActivity(settings);
                break;
            case R.id.swapt:
                fragmentClass = MainActivity.class;
//                Intent main = new Intent(getApplicationContext(), MainActivity.class);
//               startActivity(main);
                break;
            case R.id.exit:
                fragmentClass = LogInActivity.class;
//                Intent logIn = new Intent(getApplicationContext(), LogInActivity.class);
//                startActivity(logIn);
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

        mDrawer.closeDrawer(GravityCompat.START);
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
        if (mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}