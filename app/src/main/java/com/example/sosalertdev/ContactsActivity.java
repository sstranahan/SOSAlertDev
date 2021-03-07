package com.example.sosalertdev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ContactsActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton settingsBtn;
    ImageButton contactsBtn;
    ImageButton newsBtn;
    ImageButton mapsBtn;
    ImageButton weatherBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        contactsBtn = (ImageButton) findViewById(R.id.contactsBtn);
        newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
                ContactsActivity.this.startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, SettingsActivity.class);
                ContactsActivity.this.startActivity(intent);
            }
        });

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ContactsActivity.this, ContactsActivity.class);
//                ContactsActivity.this.startActivity(intent);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, NewsActivity.class);
                ContactsActivity.this.startActivity(intent);
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, MapsActivity.class);
                ContactsActivity.this.startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, WeatherActivity.class);
                ContactsActivity.this.startActivity(intent);
            }
        });

    }
}