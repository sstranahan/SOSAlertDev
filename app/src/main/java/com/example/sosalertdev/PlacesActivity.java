package com.example.sosalertdev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

//TODO: Fix up places view

public class PlacesActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView phoneNum;
    TextView hours;
    TextView website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        name = (TextView) findViewById(R.id.place_name);
        address = (TextView) findViewById(R.id.place_add);
        phoneNum = (TextView) findViewById(R.id.place_phone);
        hours = (TextView) findViewById(R.id.place_hours);
        website = (TextView) findViewById(R.id.place_website);

        name.setText(MapsActivity.clickedPlace.getName());

        address.setText(MapsActivity.clickedPlace.getAddress());

        phoneNum.setText(MapsActivity.clickedPlace.getPhoneNumber());

        hours.setText(MapsActivity.clickedPlace.getOpeningHours().toString());

        website.setText(MapsActivity.clickedPlace.getWebsiteUri().toString());

    }
}