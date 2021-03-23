package com.example.sosalertdev;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.w3c.dom.Text;

import java.util.List;

//TODO: Fix up places view

public class PlacesActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView phoneNum;
    TextView hours;
    TextView website;
    TextView openStatus;
    TextView rating;

    ImageView photo;

    Boolean isOpen;
    double ratingDbl;

    List<PhotoMetadata> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        name = (TextView) findViewById(R.id.place_name);
        address = (TextView) findViewById(R.id.place_add);
        phoneNum = (TextView) findViewById(R.id.place_phone);
        hours = (TextView) findViewById(R.id.place_hours);
        website = (TextView) findViewById(R.id.place_website);
        openStatus = (TextView) findViewById(R.id.open_status);
        rating = (TextView) findViewById(R.id.rating);

        photo = (ImageView) findViewById(R.id.photo_view);

        if (MapsActivity.clickedPlace.isOpen() != null)
            isOpen = MapsActivity.clickedPlace.isOpen();

        ratingDbl = MapsActivity.clickedPlace.getRating();

        if (isOpen != null) {
            if (isOpen) {
                openStatus.setText("OPEN");
            } else {
                openStatus.setText("CLOSED");
            }
        }

        photoList = MapsActivity.clickedPlace.getPhotoMetadatas();

        System.out.println(photoList.toString());

        PhotoMetadata pm = photoList.get(0);

        final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(pm)
                .setMaxWidth(1000) // Optional.
                .setMaxHeight(600) // Optional.
                .build();

        PlacesClient placesClient = Places.createClient(getApplicationContext());

        placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
            Bitmap bitmap = fetchPhotoResponse.getBitmap();
            photo.setImageBitmap(bitmap);
        }).addOnFailureListener((exception) -> {
            exception.printStackTrace();
            }
        );

        photo.setScaleX((float)1.8);
        photo.setScaleY((float)1.8);

        //bitmap = (Bitmap) getIntent().getParcelableExtra("Bitmap");

        //System.out.println(bitmap.toString());

        //photo.setImageBitmap(bitmap);

        rating.setText("Rating: " + ratingDbl);

        name.setText(MapsActivity.clickedPlace.getName());

        address.setText(MapsActivity.clickedPlace.getAddress());

        phoneNum.setText(MapsActivity.clickedPlace.getPhoneNumber());

        if (MapsActivity.clickedPlace.getOpeningHours() != null) {
            hours.setText(MapsActivity.clickedPlace.getOpeningHours().getWeekdayText().toString()
                    .substring(1, MapsActivity.clickedPlace.getOpeningHours().getWeekdayText().toString().length() - 1));
        }

        website.setText(MapsActivity.clickedPlace.getWebsiteUri().toString());

    }
}