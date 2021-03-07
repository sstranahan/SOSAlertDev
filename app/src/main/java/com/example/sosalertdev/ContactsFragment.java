package com.example.sosalertdev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the CONTACT_KEY column
    private static final int CONTACT_KEY_INDEX = 1;

    EditText editText;

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView contactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long contactId;
    // The contact's LOOKUP_KEY
    String contactKey;
    // A content URI for the selected contact
    Uri contactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter cursorAdapter;

    public ContactsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        // Inflate the fragment layout
        return inflater.inflate(R.layout.contact_frag,
                container, false);


    }

    public void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(this.getView(), savedInstanceState);

        // Gets the ListView from the View list of the parent activity
        contactsList =
                (ListView) getView().findViewById(R.id.listView);
        // Gets a CursorAdapter
        cursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contacts_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        // Sets the adapter for the ListView
        contactsList.setAdapter(cursorAdapter);

        contactsList.setOnItemClickListener(this);

    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        selectionArgs[0] = "%" + searchString + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        cursorAdapter.swapCursor(null);
    }

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines a variable for the search string
    private String searchString;
    // Defines the array to hold values that replace the ?
    private String[] selectionArgs = { searchString };

    @Override
    public void onItemClick(
            AdapterView<?> parent, View item, int position, long rowID) {
        // Get the Cursor
        Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        contactId = cursor.getLong(CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        contactKey = cursor.getString(CONTACT_KEY_INDEX);
        // Create the contact's content Uri
        contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey);
        /*
         * You can use contactUri as the content URI for retrieving
         * the details for a contact.
         */
    }

        // Called just before the Fragment displays its UI
        @Override
        public void onCreate(Bundle savedInstanceState) {
            // Always call the super method first
            super.onCreate(savedInstanceState);

            // Initializes the loader
            getLoaderManager().initLoader(0, null, this);
        }


}
