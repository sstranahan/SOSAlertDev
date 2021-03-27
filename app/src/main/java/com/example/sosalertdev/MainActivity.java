package com.example.sosalertdev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

////////////////////////////////////////////////////////////////////////////////////////////////////
// SOSAlert App - Author: Stephen Stranahan
// ECE 435 - Midterm Android App Project - Prof. Paul Watta
////////////////////////////////////////////////////////////////////////////////////////////////////

// This is an android app which will serve to provide SOS emergency contact services, news, traffic,
// weather, map data. The user can select one or more emergency contacts from their android device's
// native contacts list, define (or use default) SOS message, and press a button which will
// send their emergency contact(s) their defined SOS message and location (lat & long). Note: an
// actual SMS message cannot be sent using an emulated device, (no SIM card), so the simulated
// message will appear in a toast.

// News is implemented using NYTimes news api HTTPS calls, and retrieved as JSON. The news
// ticker is currently configured to show all top headlines for the US.

// The maps service is implemented using Google Maps and Google Places SDKs. Places are clickable,
// and will display a custom activity with the details of the place, including a clickable website
// link.

// The weather service is implemented using the open weather map api and is displayed in a custom
// view.

//////////////////////////////////////////////////////////////////////////////////////////////////////////
// Grader Notes: Try clicking a place in google map view, try setting custom SOS message in settings view

// Must select an emergency contact in contacts view for message to send

// Please add some contacts to your AVD if using emulator

// SOS Message is shown in a textview - actual messages require a real sim card

// Try clicking links
// News and weather data retrieved as JSON from APIs


public class MainActivity extends AppCompatActivity {

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    static int googleAvail;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    public static LatLng latLng;

    public static String sosText = "Emergency message from SOSAlert App - ";

    public static boolean locSetFlag = false;

    ImageButton homeBtn;
    ImageButton settingsBtn;
    ImageButton contactsBtn;
    ImageButton newsBtn;
    ImageButton mapsBtn;
    ImageButton weatherBtn;

    ImageButton sosBtn;

    TextView errorTxtView;
    TextView msgView;

    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkApiAvail();
        checkReqPermissions();
        startLocationUpdates();

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        contactsBtn = (ImageButton) findViewById(R.id.contactsBtn);
        newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);
        sosBtn = (ImageButton) findViewById(R.id.sosButton);
        errorTxtView = (TextView) findViewById(R.id.errorTxt);
        msgView = (TextView) findViewById(R.id.demo_msg);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        // Will send simulated SOS message when SOS button is long clicked
        // Real SMS message cannot be sent without physical SIM card - those calls are included
        // but commented. Emergency contacts are retrieved from static list in ContactsActivity class
        sosBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (ContactsActivity.contactListStat != null && MainActivity.sosText != "") {
                    // Simulated text - cannot send text through phone native SMS without real SIM card

                    if (!locSetFlag) {
                        sosText += " Location:  Lat: " + MainActivity.getLatLng().latitude + " Lon: " + MainActivity.getLatLng().longitude;
                        locSetFlag = true;
                    }

                    for (int i = 0; i < ContactsActivity.contactsDispList.size(); i++) {
                        String contName = null;
                        String contNum = null;

                        contName = ContactsActivity.contactsDispList.get(i).getDisplayName();
                        contNum = ContactsActivity.contactsDispList.get(i).getPhoneNumber();

                        // Toast.makeText(MainActivity.this, "Simulated text message to recipient: " + contName + " Phone: " + contNum + "\n" + MainActivity.sosText , Toast.LENGTH_LONG).show();

                        msgView.setText("Simulated text message to recipient:\n" + contName + " Phone: " + contNum + "\n" + MainActivity.sosText);

                        //    -- Real SMS message would be sent here --
                        //    sendSmsMessage(findViewById(R.id.main));
                    }

                } else {
                    errorTxtView.setText("Error: Emergency contact hasn't been selected.");
                }
                return true;
            }
        });
    }

    private void checkApiAvail() {
        // Check if google play services available
        GoogleApiAvailability gApiAvail = new GoogleApiAvailability();
        googleAvail = gApiAvail.isGooglePlayServicesAvailable(getApplicationContext());
        if (googleAvail != ConnectionResult.SUCCESS) {
            System.out.println("Not avail");
            System.exit(99);
        } else {
            System.out.println("avail");
        }
    }

    private void checkReqPermissions() {
        // Check if permissions granted - if not, request them
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //Toast.makeText(MainActivity.this, "Fine Location Permissions Available", Toast.LENGTH_SHORT).show();
        }

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            //Toast.makeText(MainActivity.this, "Coarse Location Permissions Available", Toast.LENGTH_SHORT).show();
        }

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
        } else {
            //Toast.makeText(MainActivity.this, "Background Location Permissions Available", Toast.LENGTH_SHORT).show();
        }

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
            //Toast.makeText(MainActivity.this, "SMS Permissions Available", Toast.LENGTH_SHORT).show();
        }

    }

    // Trigger new location updates at interval
    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    public static LatLng getLatLng () {
        return latLng;
    }

    public void sendSmsMessage(View view) {

        for (int i = 0; i < ContactsActivity.contactListStat.size(); i++) {

            String destinationAddress = ContactsActivity.contactListStat.get(i).getPhoneNumber();

            String smsMessage = MainActivity.sosText;

            // Set the service center address if needed, otherwise null.
            String scAddress = null;
            // Set pending intents to broadcast
            // when message sent and when delivered, or set to null.
            PendingIntent sentIntent = null, deliveryIntent = null;

            smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(destinationAddress, scAddress, smsMessage,
                    sentIntent, deliveryIntent);
        }
    }
}