package com.example.sothengchheang.ckcc_joul_pteas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class Detail_List_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        TextView txtLocation = findViewById(R.id.txt_location);
        TextView txtPrice = findViewById(R.id.txt_price);
        TextView txtDescription = findViewById(R.id.txt_description);
        SimpleDraweeView imgItem = findViewById(R.id.img_list);

        //Get data from HomeFragment
        Intent intent = getIntent();
        String itemJson = intent.getStringExtra("itemJson");
        Gson gson = new Gson();
        Item item = gson.fromJson(itemJson, Item.class);

        txtLocation.setText(item.getLocation());
        txtPrice.setText(item.getPrice());
        txtDescription.setText(item.getDescription());
        imgItem.setImageURI(item.getImageUrl());
    }
}
