package com.example.servicetech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    NewFragment newFragment;
    ProgressFragment progressFragment;
    StakesFragment stakesFragment;
    DrawerLayout mDrawer;
    Toolbar toolbar;
    NavigationView nvDrawer;
    ActionBarDrawerToggle drawerToggle;

    ActionBar bar;
    FragmentActivity listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            newFragment = new NewFragment();
            progressFragment = new ProgressFragment();
            stakesFragment = new StakesFragment();
        }

        getChildFragmentManager().beginTransaction().replace(R.id.homeFrag,
                new HomeFragment()).commit();


        return inflater.inflate(R.layout.fragment_home, container, false);



        //    return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDrawer = (DrawerLayout)getView().findViewById(R.id.draw_lay);

        toolbar = (Toolbar)getView().findViewById(R.id.toolbar);
        /*setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        nvDrawer = (NavigationView)getView().findViewById(R.id.drawer);
        //setupDrawerContent(nvDrawer);

        insertNestedFragment();
        //super.onViewCreated(view, savedInstanceState);

        //super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.event:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        //   return super.onOptionsItemSelected(item);
    }


    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                item -> {
                    MenuItem menuItem = null;
                    selectDrawerItem(menuItem);
                    return true;
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
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.sup_d:
                fragmentClass = SupportFragment.class;
                break;
            default:
                fragmentClass = StakesFragment.class;
        }
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.event_container, fragment).commit();

        menuItem.setChecked(true);
        menuItem.setTitle(getId());

        mDrawer.closeDrawers();
    }

    private void insertNestedFragment(){
        Fragment newFragment = new NewFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.event_container, newFragment).commit();

        NavigationView nav = getView().findViewById(R.id.drawer);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                switch (item.getItemId()){
                    case  R.id.nw_d:
                        selectedFrag = new NewFragment();
                        break;
                    case  R.id.prog_dr:
                        selectedFrag = new ProgressFragment();
                        break;
                    case  R.id.book_d:
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
