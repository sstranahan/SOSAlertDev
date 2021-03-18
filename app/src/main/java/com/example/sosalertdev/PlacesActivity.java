package com.example.sosalertdev;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlacesActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Parcelable obj = savedInstanceState.getParcelable("place");

    }
}
