package com.example.sothengchheang.ckcc_joul_pteas;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    //    private RecyclerView recyclerView;
    //    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private Item[] AllItemData;
    private Item[] ItemDataShow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_home, container, false);
        return fragmentLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton btnAddItem = view.findViewById(R.id.btn_add_items);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Add_Item_Activity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView = view.findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);

        //Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        final ListsAdapter listAdapter = new ListsAdapter();
        itemsAdapter = new ItemsAdapter();
        recyclerView.setAdapter(itemsAdapter);

//        loadItemsFromWebService();
        loadItemsFromFirestore();

    }


    //Laod Data From WebService
    private void loadItemsFromWebService() {
        String url = "http://test.js-cambodia.com/ckcc/events.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(response, "error");
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

    private void loadItemsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Non-realtime query
     /*   db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List[] items = new List[task.getResult().size()];
                    int index = 0;
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        Item item = documentSnapshot.toObject(Item.class);
                        item.setId(documentSnapshot.getId());
                        items[index] = (List) item;
                        index++;
                    }
                    itemsAdapter.setItems((Item[]) items);
                } else {
                    Toast.makeText(getActivity(),"Load items fail.",Toast.LENGTH_LONG).show();
                    Log.d("ckcc", "Load items fail: "+ task.getException());
                }
            }
        }); */

        //Realtime query
        db.collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                Item[] items = new Item[queryDocumentSnapshots.size()];
                int index = 0;
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Item item = documentSnapshot.toObject(Item.class);
                    item.setId(documentSnapshot.getId());
                    items[index] = item;
                    index++;
                }
                itemsAdapter.setItems(items);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {


        public Item[] getItems() {
            return AllItemData;
        }

        public void setItems(Item[] items) {
            AllItemData = items;
            ItemDataShow = items;
            notifyDataSetChanged();
        }
//        public void setData(Store[] data) {
////            AllStoreData = data;
////            StoreDataShow = data;
////            notifyDataSetChanged();
////        }

        public void search(String keywords){
            List<Item> foundItem = new ArrayList<>();
            for (Item item : AllItemData){
                if (item.getLocation().toLowerCase().contains(keywords.toLowerCase())) {
                    foundItem.add(item);
                }
            }
            ItemDataShow = new Item[foundItem.size()];
            foundItem.toArray(ItemDataShow);
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
            Item item = ItemDataShow[i];
            itemViewHolder.txtPrice.setText(item.getPrice());
            itemViewHolder.txtLocation.setText(item.getLocation());
            itemViewHolder.imageView.setImageURI(item.getImageUrl());
        }


        @Override
        public int getItemCount() {
            return (ItemDataShow == null) ? 0 : ItemDataShow.length;
//            if (lists == null) {
//                return 0;
//            } else {
//                return lists.length;
//            }
        }

        //View Holder
        class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private SimpleDraweeView imageView;
            private TextView txtPrice;
            private TextView txtLocation;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.img_list);
                txtPrice = itemView.findViewById(R.id.txt_price);
                txtLocation = itemView.findViewById(R.id.txt_location);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DetailActivity.class);

                //Pass data
                int index = getAdapterPosition();
                Item item = ItemDataShow[index];
                Gson gson = new Gson();
                String itemJson = gson.toJson(item);
                intent.putExtra("itemJson", itemJson);

                startActivity(intent);
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
//
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Cart menu
        inflater.inflate(R.menu.toolbar_item_menu, menu);


        final MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
//                homeAdapter.search(keyword);
                Log.d("ckcc", "search"+ keyword);
                ItemsAdapter itemsAdapter = new ItemsAdapter();
                itemsAdapter.search(keyword);

                return false;
            }
        });
    }
}



