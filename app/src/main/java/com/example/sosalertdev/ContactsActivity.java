package com.example.sosalertdev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity<MyCustomAdapter> extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton settingsBtn;
    ImageButton contactsBtn;
    ImageButton newsBtn;
    ImageButton mapsBtn;
    ImageButton weatherBtn;

    MyContactAdapter dataAdapter = null;
    MyContactAdapter2 dataAdapter2 = null;
    ListView listView;
    ListView listView2;

    Button btnGetContacts;
    Button btnDispContacts;

    static ArrayList<ContactsInfo> contactsDispList;

    static List<ContactsInfo> contactListStat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsDispList = new ArrayList<>();

        // Attach views
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        contactsBtn = (ImageButton) findViewById(R.id.contactsBtn);
        newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);
        weatherBtn = (ImageButton) findViewById(R.id.weatherBtn);
        listView = (ListView) findViewById(R.id.list_view);
        listView2 = (ListView) findViewById(R.id.list_view2);

        btnGetContacts = (Button) findViewById(R.id.searchButton);
        btnDispContacts = (Button) findViewById(R.id.displayButton);

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

        btnGetContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContacts();
            }
        });

        btnDispContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displContacts();
            }
        });

    }

    // Use cursor and ContentResolver class to retrieve contacts from phone
    // stored in static array list upon completion - for access by MyContactAdapter class
    private void getContacts(){
        ContentResolver contentResolver = getContentResolver();
        String contactId = null;
        String displayName = null;
        ArrayList<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    ContactsInfo contactsInfo = new ContactsInfo();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactsInfo.setContactId(contactId);
                    contactsInfo.setDisplayName(displayName);

                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsInfo.setPhoneNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    contactsInfoList.add(contactsInfo);
                }
            }
        }
        cursor.close();

        // Attach listview adapter
        dataAdapter = new MyContactAdapter(getApplicationContext(), R.layout.contact_info, contactsInfoList);
        listView.setAdapter(dataAdapter);

        // Copy to static list
        contactListStat = contactsInfoList;
    }


    // Copy all selected contacts into emergency contacts listView
    private void displContacts() {
        contactsDispList.clear();
        // getContacts();
        if (contactListStat!=null) {
            for (int i = 0; i < contactListStat.size(); i++) {
                if (contactListStat.get(i) != null) {
                    if (contactListStat.get(i).isSelected()) {
                        contactsDispList.add(contactListStat.get(i));
                    }
                }
            }
        }

        dataAdapter2 = new MyContactAdapter2(getApplicationContext(), R.layout.contact_info, contactsDispList);
        listView2.setAdapter(dataAdapter2);

        // Reset contacts view
        contactsBtn.performClick();
    }

    //recursive blind checks removal for everything inside a View
    private void removeAllChecks(ViewGroup vg) {
        View v = null;
        for(int i = 0; i < vg.getChildCount(); i++){
            try {
                v = vg.getChildAt(i);
                ((CheckBox)v).setChecked(false);
            }
            catch(Exception e1){ //if not checkBox, null View, etc
                try {
                    removeAllChecks((ViewGroup)v);
                }
                catch(Exception e2){ //v is not a view group
                    continue;
                }
            }
        }

    }


}