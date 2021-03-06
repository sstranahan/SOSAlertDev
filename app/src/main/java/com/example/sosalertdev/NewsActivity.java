package com.example.sosalertdev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton settingsBtn;
    ImageButton contactsBtn;
    ImageButton newsBtn;
    ImageButton mapsBtn;
    ImageButton weatherBtn;

    JSONObject myJSONObject;

    static int googleAvail;

    static TextView textView;

    static String responseStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        contactsBtn = (ImageButton) findViewById(R.id.contactsBtn);
        newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);



        // Check Google Services
        checkApiAvail();

        // Check permissions
        if (ContextCompat.checkSelfPermission(NewsActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewsActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
        } else {
            Toast.makeText(NewsActivity.this, "Internet Permissions Available", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(NewsActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewsActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        } else {
            Toast.makeText(NewsActivity.this, "Access Permissions Available", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(NewsActivity.this, Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewsActivity.this, new String[]{Manifest.permission.CHANGE_NETWORK_STATE}, 1);
        } else {
            Toast.makeText(NewsActivity.this, "Change Permissions Available", Toast.LENGTH_SHORT).show();
        }




        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, MainActivity.class);
                NewsActivity.this.startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, SettingsActivity.class);
                NewsActivity.this.startActivity(intent);
            }
        });

        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, ContactsActivity.class);
                NewsActivity.this.startActivity(intent);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NewsActivity.this, NewsActivity.class);
//                NewsActivity.this.startActivity(intent);
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, MapsActivity.class);
                NewsActivity.this.startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, WeatherActivity.class);
                NewsActivity.this.startActivity(intent);
            }
        });

        new MyTask().execute();

        System.out.println(responseStr);






    }

    private void checkApiAvail() {
        // Check if google play services available
        GoogleApiAvailability gApiAvail = new GoogleApiAvailability();
        googleAvail = gApiAvail.isGooglePlayServicesAvailable(getApplicationContext());
        if (googleAvail != ConnectionResult.SUCCESS) {
            Toast.makeText(NewsActivity.this, "Google Services Unavailable", Toast.LENGTH_SHORT).show();
            System.out.println("Not avail");
            System.exit(99);
        } else {
            Toast.makeText(NewsActivity.this, "Google Services Available", Toast.LENGTH_SHORT).show();
            System.out.println("avail");
        }
    }

//    class APIClient extends AsyncTask<Void, Void, Void> {
//
//        URL myUrl;
//        URLConnection myUrlConnection;
//         JSONObject myJsonObject;
//        java.io.InputStream myInputStream = null;
//        String line, str;
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//            NewsActivity.responseStr = str;
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//                myUrl = new URL("https://newsapi.org/v2/everything?q=bitcoin&apiKey=2ad130f782ac4c8ea47427d709022569");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            URLConnection myUrlConnection = null;
//            try {
//                myUrlConnection = myUrl.openConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
//
//            try {
//                myInputStream = myUrlConnection.getInputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            InputStreamReader myInputStreamReader;
//            myInputStreamReader = new InputStreamReader(myInputStream);
//
//            BufferedReader in = new BufferedReader(myInputStreamReader);
//
//            str = "";
//
//            System.out.print("\n");
//
//            try {
//                while ((line = in.readLine()) != null)
//                    str += line;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            NewsActivity.responseStr = str;
//
//            try {
//                myJsonObject = new JSONObject(str);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//
//        }
//    }

    private class MyTask extends AsyncTask<Void, Void, Void>{
        String result, str, line;
        int statInt;
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            try {
                url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=michigan&api-key=nENAki5mQZVtYtXhFLpGuxD1CV6YzMwY");
                URLConnection myURLConnection = url.openConnection();

                InputStream myInputStream = myURLConnection.getInputStream();
                InputStreamReader myInputStreamReader;
                myInputStreamReader = new InputStreamReader(myInputStream);
                BufferedReader in = new BufferedReader(myInputStreamReader);

                while ((line = in.readLine()) != null)
                    str += line;

                result = str;

            } catch (IOException e){
                e.printStackTrace();
                result = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){

            result = result.substring(4);

            System.out.println(result);

            NewsActivity.responseStr = result;

            JSONObject myJSONObject = null;

        try {
            myJSONObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            try {
                myJSONObject = myJSONObject.getJSONObject("response");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray myJSONArray = null;

            try {
                myJSONArray = myJSONObject.getJSONArray("docs");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int numResults = myJSONArray.length();

            JSONObject[] jsonArr = new JSONObject[numResults];

            for (int i = 0; i < numResults ; i++) {
                try {
                    jsonArr[i] = myJSONArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String[] abstracts = new String[numResults];
            String[] urls = new String[numResults];

            String[] pars = new String[numResults];

            String[][] newsArr = new String[3][numResults];

            newsArr[0] = abstracts;
            newsArr[1] = urls;
            newsArr[2] = pars;



            for (int i = 0; i < numResults - 1; i++) {
                try {
                    newsArr[0][i] = (jsonArr[i].getString("abstract"));
                    newsArr[1][i] = (jsonArr[i].getString("web_url"));
                    newsArr[2][i] = (jsonArr[i].getString("lead_paragraph"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            System.out.println("" + numResults);

            ArrayList<String> myArrayList = new ArrayList<String>();

            for (int i = 0; i < numResults ; i++) {

                String record = "";

                for (int j = 0; j < 3; j++) {
                    record += (newsArr[j][i] + "\n\n");
                }

                    myArrayList.add(record);
            }

            ArrayAdapter<String> myAdapter;

            ListView myListView = (ListView) findViewById(R.id.LV);
            myAdapter = new
                    ArrayAdapter<String>(NewsActivity.this,R.layout.my_text_view,myArrayList);
            myListView.setAdapter(myAdapter);

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("ME", "pos" + position);
                }
            });


            super.onPostExecute(aVoid);

        }
    }

}


