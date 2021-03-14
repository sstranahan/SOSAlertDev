package com.example.sosalertdev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton settingsBtn;
    ImageButton contactsBtn;
    ImageButton newsBtn;
    ImageButton mapsBtn;
    ImageButton weatherBtn;

    TextView textView;
    EditText editText;

    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        contactsBtn = (ImageButton) findViewById(R.id.contactsBtn);
        newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);

        textView = (TextView) findViewById(R.id.sampleDisp);
        editText = (EditText) findViewById(R.id.editText);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
//                SettingsActivity.this.startActivity(intent);
            }
        });

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ContactsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, NewsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MapsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, WeatherActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText() + " Location:  Lat: " + MainActivity.getLatLng().latitude + "Lon: " + MainActivity.getLatLng().longitude);
                MainActivity.sosText = editText.getText().toString() + " Location:  Lat: " + MainActivity.getLatLng().latitude + "Lon: " + MainActivity.getLatLng().longitude;
            }
        });


    }
}