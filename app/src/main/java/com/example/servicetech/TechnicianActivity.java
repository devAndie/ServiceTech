package com.example.servicetech;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class TechnicianActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    DrawerLayout nDrawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_home);

        bottomNav = findViewById(R.id.bottomNav_t);
        bottomNav.setOnNavigationItemSelectedListener(navList);

/*        navigationView = findViewById(R.id.t_nav_view);
        toolbar = findViewById(R.id.toolbar_main);
        nDrawer = findViewById(R.id.t_draw_lay);


        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        nDrawer.addDrawerListener(drawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
*/


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
            case R.id.book_d:
                fragmentClass = BookFragment.class;
                break;
            case R.id.prog_dr:
                fragmentClass = ProgressFragment.class;
                break;
            case R.id.pay_dr:
                fragmentClass = PaymentFragment.class;
                break;
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

}