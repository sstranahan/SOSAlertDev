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
import com.google.android.material.snackbar.Snackbar;

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
    ListView lv;

    static int googleAvail;


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
        lv = (ListView) findViewById(R.id.LV);

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

    private class MyTask extends AsyncTask<Void, Void, Void>{
        String result, str, line;
        int statInt;
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            try {
                //url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=glocations:(\"DETROIT\")&api-key=nENAki5mQZVtYtXhFLpGuxD1CV6YzMwY");
                url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=&api-key=nENAki5mQZVtYtXhFLpGuxD1CV6YzMwY");
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

            ArrayList<NewsItem> newsArr = new ArrayList<>();

            try {
                for (int i = 0; i < numResults ; i++) {
//                    newsArr[i].abs = jsonArr[i].getString("abstract");
//                    newsArr[i].snippet = jsonArr[i].getString("snippet");
//                    newsArr[i].url = jsonArr[i].getString("web_url");
//                    newsArr[i].leadPar = jsonArr[i].getString("lead_paragraph");

                    JSONArray multiArr = jsonArr[i].getJSONArray("multimedia");
                    JSONObject imgObj = multiArr.getJSONObject(0);

                    NewsItem tempNewsObj = new NewsItem(jsonArr[i].getString("abstract"),
                            jsonArr[i].getString("web_url"),jsonArr[i].getString("snippet"),
                            jsonArr[i].getString("lead_paragraph"),"https://www.nytimes.com/" + imgObj.getString("url"));

                    newsArr.add(tempNewsObj);

                    System.out.println();
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            MyListViewAdapter adapter = new MyListViewAdapter(getApplicationContext(), newsArr);

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NewsItem newsItem = newsArr.get(position);

                    Snackbar.make(view, newsItem.getSnippet()+"\n"+newsItem.getLeadPar(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });

            super.onPostExecute(aVoid);

        }
    }

}


