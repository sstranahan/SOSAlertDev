package com.example.sosalertdev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class WeatherActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton settingsBtn;
    ImageButton contactsBtn;
    ImageButton newsBtn;
    ImageButton mapsBtn;
    ImageButton weatherBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        WeatherFragment frag = new WeatherFragment();


        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        contactsBtn = (ImageButton) findViewById(R.id.contactsBtn);
        newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, frag)
                    .commit();
        }

        frag.updateWeatherData();



        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
                WeatherActivity.this.startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WeatherActivity.this, SettingsActivity.class);
            WeatherActivity.this.startActivity(intent);
        });

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, ContactsActivity.class);
                WeatherActivity.this.startActivity(intent);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, NewsActivity.class);
                WeatherActivity.this.startActivity(intent);
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, MapsActivity.class);
                WeatherActivity.this.startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(WeatherActivity.this, WeatherActivity.class);
//                WeatherActivity.this.startActivity(intent);
            }
        });
    }
}