package com.example.sothengchheang.ckcc_joul_pteas;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class DrawerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.menu_home) {
                    onHomeClick();
                } else if (menuItem.getItemId() == R.id.menu_profile) {
                    onProfileClick();
                } else if (menuItem.getItemId() == R.id.menu_favorites) {
                    onFavoriteClick();
                } else if (menuItem.getItemId() == R.id.menu_settings) {
                    onSettingsClick();
                } else if (menuItem.getItemId() == R.id.menu_aboutUS) {
                    onAboutUSClick();
                } else if (menuItem.getItemId() == R.id.menu_contactUS) {
                    onContactsClick();
                }

                return true;
            }
        });
    }

    private void onHomeClick() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.lyt_fragment_container, homeFragment);
        fragmentTransaction.commit();
    }

    private void onProfileClick() {
        ProfileFragment eventsFragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.lyt_fragment_container, eventsFragment);
        fragmentTransaction.commit();
    }

    private void onFavoriteClick() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.lyt_fragment_container, favoritesFragment);
        fragmentTransaction.commit();
    }

    private void onSettingsClick() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.lyt_fragment_container, settingsFragment);
        fragmentTransaction.commit();
    }

    private void onContactsClick() {
        ContactUSFragment contactUSFragment = new ContactUSFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.lyt_fragment_container, contactUSFragment);
        fragmentTransaction.commit();
    }

    private void onAboutUSClick() {
        AboutUSFragment aboutUSFragment = new AboutUSFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.lyt_fragment_container, aboutUSFragment);
        fragmentTransaction.commit();
    }
//        HomeFragment homeFragment = new HomeFragment();

}

