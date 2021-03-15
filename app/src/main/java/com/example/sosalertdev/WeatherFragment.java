package com.example.sosalertdev;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class WeatherFragment extends Fragment {
    Typeface weatherFont;

    private TextView cityField;
    private TextView weatherIcon;
    private TextView updatedField;
    private TextView currentTemperatureField;
    private TextView detailsField;

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

        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);

        detailsField = (TextView)rootView.findViewById(R.id.details_field);


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
                    String.format("%.2f", main.getDouble("temp"))+ " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

       /*setWeatherIcon(details.getInt("id"),
               json.getJSONObject("sys").getLong("sunrise") * 1000,
               json.getJSONObject("sys").getLong("sunset") * 1000);
        */
        }catch(Exception e){
            Log.e("SimpleWeather", "Field not present in JSON Received");
        }
    }

}