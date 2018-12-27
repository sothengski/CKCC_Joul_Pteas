

package com.example.sothengchheang.ckcc_joul_pteas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class ImageFragment extends Fragment {
    String datalocation;
    String dataprice;
    String datadescription;
    String dataimg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View imagelayout = inflater.inflate(R.layout.fragment_detail, container, false);

        DetailActivity activity = (DetailActivity) getActivity();
        datalocation = activity.getlocation();
        dataprice = activity.getprice();
        datadescription = activity.getdescription();
        dataimg = activity.getimg();
        Log.d("ckcc", "data:::::"+datalocation);
        return imagelayout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtLocation = view.findViewById(R.id.txt_location_detail);
        TextView txtPrice = view.findViewById(R.id.txt_price_detail);
        TextView txtDescription = view.findViewById(R.id.txt_description_detail);
        SimpleDraweeView imgItem = view.findViewById(R.id.img_detail);

        txtLocation.setText(datalocation);
        txtPrice.setText(dataprice);
        txtDescription.setText(datadescription);
        imgItem.setImageURI(dataimg);

//
//        Get data from HomeFragment
//        Intent intent = new Intent();
//        String itemJson = intent.getStringExtra("itemJson");
//        Gson gson = new Gson();
//        Item item = gson.fromJson(itemJson, Item.class);
//
//        txtLocation.setText(item.getLocation());
//        txtPrice.setText(item.getPrice());
//        txtDescription.setText(item.getDescription());
//        imgItem.setImageURI(item.getImageUrl());
    }
//
    }
    //
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View image = inflater.inflate(R.layout.fragment_detail,container,false);
//        return image;
////    }
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_list);
//
//        TextView txtLocation = findViewById(R.id.txt_location);
//        TextView txtPrice = findViewById(R.id.txt_price);
//        TextView txtDescription = findViewById(R.id.txt_description);
//        SimpleDraweeView imgItem = findViewById(R.id.img_list);
//
//        //Get data from HomeFragment
//        Intent intent = getIntent();
//        String itemJson = intent.getStringExtra("itemJson");
//        Gson gson = new Gson();
//        Item item = gson.fromJson(itemJson, Item.class);
//
//        txtLocation.setText(item.getLocation());
//        txtPrice.setText(item.getPrice());
//        txtDescription.setText(item.getDescription());
//        imgItem.setImageURI(item.getImageUrl());
//    }

