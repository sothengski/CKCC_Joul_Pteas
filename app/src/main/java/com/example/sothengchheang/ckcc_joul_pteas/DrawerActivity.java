package com.example.sothengchheang.ckcc_joul_pteas;

import android.content.Intent;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DrawerActivity extends AppCompatActivity {

    private SimpleDraweeView imgProfile_nav;
    private TextView txtName_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        onHomeClick();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        imgProfile_nav = headerView.findViewById(R.id.img_profile_nav);
        imgProfile_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DrawerActivity.this,ProfileFragment.class);
                onProfileClick();
            }
        });
        txtName_nav = headerView.findViewById(R.id.txtName_nav);

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
                } else if (menuItem.getItemId() == R.id.menu_aboutus) {
                    onAboutUSClick();
                } else if (menuItem.getItemId() == R.id.menu_contactus) {
                    onContactsClick();
                }

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadProfileInfo();
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

    private void onMapClick() {
        MapsFragment mapsFragment = new MapsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.lyt_fragment_container, mapsFragment);
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


    private void loadProfileInfo() {
        Log.d("ckcc", "loadProfileInfo");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (object != null) {
                            fetchResult(object);
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name");
        parameters.putString("field", "id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void fetchResult(JSONObject object) {
        Log.d("ckcc", "fetchResult: " + object);

        Gson gson = new Gson();
        UserProfile profile = gson.fromJson(object.toString(), UserProfile.class);
        txtName_nav.setText("" + profile.name);
//        txtEmail.setText("Email: " + profile.email);
//        txtGender.setText("Gender: " + profile.gender);
//        txtBirthday.setText("Birthday: " + profile.birthday);
//        txtAddress.setText("Address: " + profile.location.name);

        String profileImageUrl = "http://graph.facebook.com/" + profile.id + "/picture?type=large";
        imgProfile_nav.setImageURI(profileImageUrl);

    }

    //        HomeFragment homeFragment = new HomeFragment();
//    private void loadProfileInfo() {
//        Log.d("ckcc", "loadProfileInfo");
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        GraphRequest request = GraphRequest.newMeRequest(accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        fetchResult(object);
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "name");
//        parameters.putString("field", "id");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }
//
//    private void fetchResult(JSONObject object) {
//        Log.d("ckcc", "fetchResult: " + object);
//
//        Gson gson = new Gson();
//        UserProfile profile = gson.fromJson(object.toString(), UserProfile.class);
//        txtName_nav.setText("Name: " + profile.name);
////        txtEmail.setText("Email: " + profile.email);
////        txtGender.setText("Gender: " + profile.gender);
////        txtBirthday.setText("Birthday: " + profile.birthday);
////        txtAddress.setText("Address: " + profile.location.name);
//
//        String profileImageUrl = "http://graph.facebook.com/" + profile.id + "/picture?type=large";
//        imgProfile_nav.setImageURI(profileImageUrl);
//
//
//    }

}