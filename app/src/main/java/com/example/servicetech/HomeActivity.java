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
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    DashboardFragment dashboardFragment;
    EventFragment eventFragment;
    AboutFragment aboutFragment;

    DrawerLayout mDrawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FragmentActivity listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawer = findViewById(R.id.draw_lay);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_main);
       // View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        //ImageView ivHeadPhoto = headerLayout.findViewById(R.id.nav_head_img);

        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        mDrawer.addDrawerListener(drawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawerContent(navigationView);

        //        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new HomeFragment()).commit();
//        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            dashboardFragment = new DashboardFragment();
            eventFragment = new EventFragment();
            aboutFragment = new AboutFragment();
        }
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        /*bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFrag = new HomeFragment();
                        break;
                    case R.id.event:
                        selectedFrag = new EventFragment();
                        break;
                    case R.id.about:
                        selectedFrag = new AboutFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFrag).commit();
                return true;
            }
        });*/
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

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nwFrag:
                fragmentClass = NewFragment.class;
                break;
            case R.id.avail_d:
                fragmentClass = StakesFragment.class;
                break;
            case R.id.book_d:
                fragmentClass = BookFragment.class;
                break;
            case R.id.progFrag:
                fragmentClass = ProgressFragment.class;
                break;
            case R.id.pay_dr:
                fragmentClass = PaymentFragment.class;
                break;
            case R.id.loc_dr:
                fragmentClass = LocationFragment.class;
                break;
            case R.id.stt_d:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.sup_d:
                fragmentClass = SupportFragment.class;
                break;
            default:
                fragmentClass = StakesFragment.class;
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

    protected void displayHome() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, homeFragment);
        ft.commit();
    }
    protected void displayDashboard() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, aboutFragment);
        ft.commit();
    }
    protected void displayEvent() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, eventFragment);
        ft.commit();
    }
    protected void displayAbout() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, aboutFragment);
        ft.commit();
    }
}