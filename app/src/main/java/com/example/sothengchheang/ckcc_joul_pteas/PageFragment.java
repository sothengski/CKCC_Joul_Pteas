package com.example.sothengchheang.ckcc_joul_pteas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PageFragment extends Fragment {


    private static final String ARG_PAGE = "ARG_PAGE";

    // each tab is a fragment. this function make a new instance of each when view created.
    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        // get int to decide what page to render.
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPage = getArguments().getInt(ARG_PAGE);
    }


}
