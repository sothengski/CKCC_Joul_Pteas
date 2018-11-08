package com.example.sothengchheang.ckcc_joul_pteas;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements View.OnClickListener{
    public FavoritesFragment() {
        // Required empty public constructor
    }
    //    private RecyclerView recyclerView;
    //    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_favorites, container, false);

        return fragmentLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView = view.findViewById(R.id.recycler_view);

        //Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        final ListsAdapter listAdapter = new ListsAdapter();
        itemsAdapter = new ItemsAdapter();
        recyclerView.setAdapter(itemsAdapter);


        //Laod Data

        String url = "http://test.js-cambodia.com/ckcc/events.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(response,"error");
                Gson gson = new Gson();
                Item[] items = gson.fromJson(response, Item[].class);
                itemsAdapter.setItems(items);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getActivity(), "Load data error: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                Toast.makeText(HomeFragment.this, "Load data error: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                hideLoading();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {

    }

    class ItemsAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        private Item[] items;

        public Item[] getItems() {
            return items;
        }

        public void setItems(Item[] items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.viewholder_list, viewGroup, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            Item item = items[i];
            itemViewHolder.txtPrice.setText(item.getPrice());
            itemViewHolder.txtDate.setText(item.getDate());
            itemViewHolder.imageView.setImageURI(item.getImageUrl());
        }


        @Override
        public int getItemCount() {
            return (items == null) ? 0 : items.length;
//            if (lists == null) {
//                return 0;
//            } else {
//                return lists.length;
//            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private SimpleDraweeView imageView;
        private TextView txtPrice;
        private TextView txtDate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_list);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtDate = itemView.findViewById(R.id.txt_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
//    public void onClick(View view) {
//        Intent intent = new Intent(EventsActivity.this, EventDetailActivity.class);
//
//        // Pass data
//        int index = getAdapterPosition();
//        Event event = data[index];
////                intent.putExtra("title", event.getTitle());
////                intent.putExtra("date", event.getDate());
////                intent.putExtra("location", event.getLocation());
////                intent.putExtra("description", event.getDescription());
//        Gson gson = new Gson();
//        String eventJson = gson.toJson(event);
//        intent.putExtra("eventJson", eventJson);
//
//
//        startActivity(intent);
//    }
//}
}


