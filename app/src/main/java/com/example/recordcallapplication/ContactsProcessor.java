package com.example.recordcallapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class ContactsProcessor extends AppCompatActivity {
    private static final String TAG = "ContactsProcessor";
    private final ArrayList<Contact> contactsList = new ArrayList<>();

    private static final String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_screen);
        Log.d(TAG, "onCreate: Starting contacts_screen");

        ListView contactsListView = findViewById(R.id.contactsListView);
        getContacts();
        Toast.makeText(ContactsProcessor.this, "Contacts: " + contactsList.toString(), Toast.LENGTH_SHORT).show();
        Button mainScreenButton = findViewById(R.id.mainScreenButton);

        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);
        contactsListView.setAdapter(adapter);

        contactsListView.setOnItemClickListener((parent, v, position, id) -> {
            Contact selectedItem = contactsList.get(position);

            Intent intent = new Intent(ContactsProcessor.this, com.example.recordcallapplication.MainActivity.class);
            intent.putExtra("phone_number", selectedItem.getPhoneNumber());
            startActivity(intent);
        });

        mainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: mainScreenButton");

                Intent intent = new Intent(ContactsProcessor.this, com.example.recordcallapplication.MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getContacts() {
        Log.d(TAG, "getContactList: started receiving contacts");

        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor != null) {
            HashSet<String> phoneNumSet = new HashSet<String>();
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String name, number;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);
                    number = number.replace(" ", "");
                    if (!phoneNumSet.contains(number)) {
                        contactsList.add(new Contact(name, number));
                        phoneNumSet.add(number);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }
}
