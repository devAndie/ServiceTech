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
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class EventFragment extends Fragment {

    public NewFragment newFragment;
    public ProgressFragment progressFragment;
    public StakesFragment stakesFragment;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    FragmentActivity listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DrawerLayout mDrawer = getView().findViewById(R.id.draw_lay);
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        getSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(nvDrawer);

        insertNestedFragment();

        //super.onViewCreated(view, savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            newFragment = new NewFragment();
            progressFragment = new ProgressFragment();
            stakesFragment = new StakesFragment();
        }

        getChildFragmentManager().beginTransaction().replace(R.id.event_container,
                new HomeFragment()).commit();

        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.new:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        MenuItem menuItem = null;
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }
    public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
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
                fragmentClass = .class;
                break;
            case R.id.:
                fragmentClass = .class;
                break;

        }
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
