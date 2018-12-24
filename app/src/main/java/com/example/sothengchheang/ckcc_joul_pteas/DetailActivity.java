package com.example.sothengchheang.ckcc_joul_pteas;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.ViewSwitcher;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

//    @BindView(R.id.view_pager)
//    ViewPager pager;
//    @BindView(R.id.tabs)
//    PagerSlidingTabStrip tabStrip;
//    @BindView(R.id.descriptImage)
//    ImageSwitcher descriptImage;

    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // images for switcher
    private static final int[] IMAGES = {R.drawable.ic_image_default, R.drawable.ic_location, R.drawable.ic_contact};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView txtLocation = findViewById(R.id.txt_location_detail);
        TextView txtPrice = findViewById(R.id.txt_price_detail);
        TextView txtDescription = findViewById(R.id.txt_description_detail);
        SimpleDraweeView imgItem = findViewById(R.id.img_detail);
//        initializeComponents();

//        Get data from HomeFragment
        Intent intent = getIntent();
        String itemJson = intent.getStringExtra("itemJson");
        Gson gson = new Gson();
        Item item = gson.fromJson(itemJson, Item.class);

//        txtLocation.setText(item.getLocation());
//        txtPrice.setText(item.getPrice());
//        txtDescription.setText(item.getDescription());
//        imgItem.setImageURI(item.getImageUrl());
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ImageFragment(), "Images");
        adapter.addFragment(new MapsFragment(), "Map");
        adapter.addFragment(new ContactFragment(), "Contact");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
//    private void initializeComponents() {
////        updateDatabase = new UpdateDatabase();
//        ButterKnife.bind(this);
//        // set the pager adapter. More info look in 3rd party library document
//        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
//        tabStrip.setViewPager(pager);
//        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                // if user go to another tab change color, change image and query from database to match
//                descriptImage.setImageResource(IMAGES[position]);
//                dataQuery(position);
////                changeColor(position);
////                openAndQueryDb(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//
////        actionButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // add new item is clicked
////                Intent intent = new Intent(v.getContext(), AddTodoItem.class);
////                startActivity(intent);
////            }
////        });
//
//        descriptImage.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                // set props for image switcher
//                ImageView imgview = new ImageView(getApplicationContext());
//                imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//                return imgview;
//            }
//        });
//        // Photo flies in and out
//        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
//        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
//        descriptImage.setInAnimation(in);
//        descriptImage.setOutAnimation(out);
//        descriptImage.setImageResource(IMAGES[0]); // first start render
//
//    }
//
//    private void dataQuery(final int page) {
//
//        Cursor cursor;
//        //        Get data from HomeFragment
//        Intent intent = getIntent();
//        String itemJson = intent.getStringExtra("itemJson");
//        Gson gson = new Gson();
//        Item item = gson.fromJson(itemJson, Item.class);
//
//        // depending on the page : inbox, today, or next 7 days to query
//        switch(page)
//        {
//            case 0:
//                onImageFragment();
//                break;
//            case 1:
//
//                break;
//            case 2:
//
//                break;
//            default:
//                cursor = null;
//        }
//
//    }
//    private void onImageFragment() {
//        ImageFragment imageFragment = new ImageFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.view_pager, imageFragment);
//        fragmentTransaction.commit();
//    }
//
//    private void onProfileClick() {
//        ProfileFragment eventsFragment = new ProfileFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.lyt_fragment_container, eventsFragment);
//        fragmentTransaction.commit();
//    }

//    private void changeColor(int position) {
//        switch (position) {
//            case 0:
//                applyNewColor("#303f9f", "#757de8", "#3f51b5");
//                break;
//            case 1:
//                applyNewColor("#a00037", "#ff5c8d", "#d81b60");
//                break;
//            case 2:
//                applyNewColor("#4b2c20", "#a98274", "#795548");
//                break;
//            default:
//                break;
//        }
//    }
//    // apply new fancy color based on material color tool
//    private void applyNewColor (String actionBarColor, String tabStripColor, String indicatorColor) {
//        ActionBar actionBar = getSupportActionBar();
//        Window window = this.getWindow();
//
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionBarColor)));
//        window.setStatusBarColor(Color.parseColor(indicatorColor));
//        tabStrip.setBackground(new ColorDrawable((Color.parseColor(tabStripColor))));
//        tabStrip.setIndicatorColor(Color.parseColor(indicatorColor));
////        actionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(actionBarColor)));
//
//    }


//        TextView txtLocation = findViewById(R.id.txt_location);
//        TextView txtPrice = findViewById(R.id.txt_price);
//        TextView txtDescription = findViewById(R.id.txt_description);
//        SimpleDraweeView imgItem = findViewById(R.id.img_list);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_list_bottom);
//
//        bottomNavigationView = findViewById(R.id.bottom_nav);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                if(menuItem.getItemId()==R.id.mnu_image){
//                    onImageClick();
//                }else if(menuItem.getItemId()==R.id.mnu_maps){
//                    onMapsClick();
//                }else {
//                    onContactClick();
//                }
//
//                return true;
//            }
//        });
//
////        Get data from HomeFragment
//        Intent intent = getIntent();
//        String itemJson = intent.getStringExtra("itemJson");
//        Gson gson = new Gson();
//        Item item = gson.fromJson(itemJson, Item.class);
//
////
////        txtLocation.setText(item.getLocation());
////        txtPrice.setText(item.getPrice());
////        txtDescription.setText(item.getDescription());
////        imgItem.setImageURI(item.getImageUrl());
//    }
//
//
//
//    private void onImageClick(){
//        ImageFragment imageFragment = new ImageFragment();
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.layout_main, imageFragment);
//        fragmentTransaction.commit();
//    }
//
//    private void onMapsClick(){
////        EventsFragment eventsFragment = new EventsFragment();
////        FragmentManager fragmentManager = getSupportFragmentManager();
////        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////
////        fragmentTransaction.replace(R.id.layout_main, eventsFragment);
////        fragmentTransaction.commit();
//    }
//
//    private void onContactClick(){
//        ProfileFragment profileFragment = new ProfileFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.layout_main, profileFragment);
//        fragmentTransaction.commit();
//    }
//}
