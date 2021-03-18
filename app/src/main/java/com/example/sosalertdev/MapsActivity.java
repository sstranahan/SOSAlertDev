package com.example.sosalertdev;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ImageButton homeBtn;
    ImageButton settingsBtn;
    ImageButton contactsBtn;
    ImageButton newsBtn;
    ImageButton mapsBtn;
    ImageButton weatherBtn;

    private GoogleMap mMap;

    public static Place clickedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        contactsBtn = (ImageButton) findViewById(R.id.contactsBtn);
        newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                MapsActivity.this.startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SettingsActivity.class);
                MapsActivity.this.startActivity(intent);
            }
        });

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ContactsActivity.class);
                MapsActivity.this.startActivity(intent);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, NewsActivity.class);
                MapsActivity.this.startActivity(intent);
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
//                MapsActivity.this.startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, WeatherActivity.class);
                MapsActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    // TODO: Try using Google Places SDK to get POI information to display

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {

        }

        //Edit the following as per you needs
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        GoogleMap.OnMarkerClickListener myMarkerListener = null;
        mMap.setOnMarkerClickListener(myMarkerListener);

        LatLng placeLocation = null;



        while (placeLocation == null) {
            placeLocation = MainActivity.getLatLng();
        }

        if(placeLocation != null) {

            Marker placeMarker = mMap.addMarker(new MarkerOptions().position(placeLocation)
                    .title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);
        }

        setPoiClick(mMap);

    }

    private void setPoiClick(final GoogleMap map) {



        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = mMap.addMarker(new MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name));

                String poiId = poi.placeId;


                Places.initialize(getApplicationContext(), "AIzaSyC1iES0hZiF_kSe3jqFSZ15gBsFxWZL6es");

                // Create a new PlacesClient instance
                PlacesClient placesClient = Places.createClient(getApplicationContext());

                // Specify the fields to return.
                final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                        Place.Field.ADDRESS, Place.Field.PHONE_NUMBER,
                        Place.Field.OPENING_HOURS, Place.Field.WEBSITE_URI);

                final FetchPlaceRequest request = FetchPlaceRequest.newInstance(poiId, placeFields);



                Task<FetchPlaceResponse> placeTask = placesClient.fetchPlace(request);


                placeTask.addOnSuccessListener((response) -> {
                    Place place = response.getPlace();
                    clickedPlace = place;

                    System.out.println("Place found!!!!");
                    Log.i(TAG, "Place found: " + place.getName());

                    MyPlace myPlace = new MyPlace(place.getId(), place.getName(), place.getAddress(),
                            place.getPhoneNumber(), place.getOpeningHours(), place.getWebsiteUri());

                    Intent intent = new Intent(MapsActivity.this,PlacesActivity.class);
                    MapsActivity.this.startActivity(intent);

                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + exception.getMessage());
                        final int statusCode = apiException.getStatusCode();
                        // TODO: Handle error with given status code.
                    }
                    exception.printStackTrace();
                });

                //TODO: Make website for POI appear when POI marker clicked

            }
            });

    }


}