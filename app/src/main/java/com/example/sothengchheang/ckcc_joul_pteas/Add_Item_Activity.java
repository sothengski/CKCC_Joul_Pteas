package com.example.sothengchheang.ckcc_joul_pteas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Add_Item_Activity extends AppCompatActivity {


    private final int GALLERY_REQUEST_CODE = 1;
    private final int MAP_REQUEST_CODE = 2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 3;

    private ImageView imgUpload;
    private Bitmap selectedImage;
    private ProgressBar progressBar;
    private EditText etxtCoordinate, etxtLocation,etxtprice,etxtdescription,etxtdate;
    private Button btnMap;
    private double SavedLat;
    private double SavedLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add__item);
        imgUpload = findViewById(R.id.img_upload);
        progressBar = findViewById(R.id.progressBar);
//        etxtprice = findViewById(R.id.etxt_price);
//        etxtdate = findViewById(R.id.etxt_date);
//        etxtdescription = findViewById(R.id.etxt_description);
        etxtLocation = findViewById(R.id.etxt_location);
        etxtCoordinate = findViewById(R.id.etxt_coordinate);
        btnMap = findViewById(R.id.btn_mapview);

        //loadUserCurrentLocation();
    }


    public void onItemViewClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void onPickOnMapClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, MAP_REQUEST_CODE);
    }

    public void onSearchByNameClick(View view) {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    public void onSaveButtonClick(View view) {
        progressBar.setVisibility(View.VISIBLE);

        //Upload image to Firebase storage
        //Create reference
        String fileLocation = "/images/" + System.currentTimeMillis() + ".png";
        final StorageReference imageRef = FirebaseStorage.getInstance().getReference(fileLocation);

        //Convert bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        //Start Upload
        UploadTask uploadTask = imageRef.putBytes(byteArray);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                String imageUrl = task.getResult().toString();
                addItemToFirestore(imageUrl);
            }


        });
    }

    private void addItemToFirestore(String imageUrl) {
        EditText etxtPrice = findViewById(R.id.etxt_price);
        EditText etxtDate = findViewById(R.id.etxt_date);
        EditText etxtLocation = findViewById(R.id.etxt_location);
        EditText etxtDescription = findViewById(R.id.etxt_description);

        final String price = etxtPrice.getText().toString();
        final String date = etxtDate.getText().toString();
        final String location = etxtLocation.getText().toString();
        final String description = etxtDescription.getText().toString();

        //Add item to FireStore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> item = new HashMap<>();
        item.put("price", price);
        item.put("date", date);
        item.put("location", location);
        item.put("description", description);
        item.put("imageUrl", imageUrl);

        db.collection("items").add(item).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(Add_Item_Activity.this, "Add item success", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(Add_Item_Activity.this, "Add Item fail" + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onItemImageViewClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgUpload.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (requestCode == MAP_REQUEST_CODE) {
                double lat = data.getDoubleExtra("lat", 0);
                double lng = data.getDoubleExtra("lng", 0);
                SavedLat = lat;
                SavedLng = lng;
                reverseGeocode(lat, lng);
            }


            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    etxtLocation.setText(place.getName());

                    SavedLat = place.getLatLng().latitude;
                    SavedLng = place.getLatLng().longitude;
                    Log.i("ckcc", "Place: " + SavedLng);
                    Log.i("ckcc", "Place: " + SavedLat);
                    Log.i("ckcc", "Place: " + place.getLatLng());
                    Log.i("ckcc", "Place: " + place.getName());
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    Log.i("ckcc", status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                    //
                }

            }

        }

    }

    private void reverseGeocode(final double lat, final double lng) {
        Log.d("ckcc", "reverseGeocode");
        final Geocoder geocoder = new Geocoder(this, new Locale("km"));
        Thread geocodeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    final Address address = addresses.get(0);
                    //etxtAddress.setText(address.getAddressLine(0));
                    Log.d("ckcc", "Address: " + address.toString());
                    // Dispatch background thread to main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etxtLocation.setText(address.getAddressLine(0));
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(Add_Item_Activity.this, "Error while trying to reverse geocode.", Toast.LENGTH_SHORT).show();
                    Log.d("ckcc", "reverseGeocode error: " + e.getMessage());
                }
            }
        });
        geocodeThread.start();
    }
}
