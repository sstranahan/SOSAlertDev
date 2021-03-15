package com.example.sosalertdev;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;


public class WeatherHttpClient {


    public static JSONObject getJSON(Context context){
        try {
            double lat = MainActivity.getLatLng().latitude;
            double lon = MainActivity.getLatLng().longitude;

            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=imperial&appid=22b70b703283692f88de41d390c69d0c");
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}