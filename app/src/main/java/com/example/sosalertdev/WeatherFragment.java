package com.example.sosalertdev;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;


//TODO: Get feelslike and icon to appear

public class WeatherFragment extends Fragment {
    Typeface weatherFont;

    private TextView cityField;
    private TextView weatherIcon;
    private TextView updatedField;
    private TextView currentTemperatureField;
    private TextView detailsField;

    private TextView feelsLikeField;
    private TextView humidityField;

    private ImageView iconView;

    Handler handler;

    public WeatherFragment(){
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        updatedField = (TextView)rootView.findViewById(R.id.updated_field);

        cityField = (TextView)rootView.findViewById(R.id.city_field);

        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);

        feelsLikeField = (TextView)rootView.findViewById(R.id.feels_like_field);


        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);

        detailsField = (TextView)rootView.findViewById(R.id.details_field);

       iconView = (ImageView)rootView.findViewById(R.id.imageView);


        return rootView;
    }

    public void updateWeatherData(){
        new Thread(){
            public void run(){
                final JSONObject json = WeatherHttpClient.getJSON(getActivity());
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " \u00B0F");

            feelsLikeField.setText( "Feels like: " + String.format("%.02f",main.getDouble("feels_like")) + " \u00B0F");


            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

            String icon = details.getString("icon");
            String iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";

           Picasso.get().load(iconUrl).into(iconView);


        } catch(Exception e){
            e.printStackTrace();
        }
    }

}