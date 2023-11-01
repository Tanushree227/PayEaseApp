package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ToContactsActivity extends AppCompatActivity {

    DBHandler dbHandler;

    ListView cList;
    private ContactAdapter contactAdapter;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_contacts);

        dbHandler = new DBHandler(this);

        List<Contacts> contactList = fetchContactsFromDatabase();

        contactAdapter = new ContactAdapter(this, contactList);
        cList.setAdapter(contactAdapter);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton backContact = (ImageButton) findViewById(R.id.backContact);
        backContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ToContactsActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private List<Contacts> fetchContactsFromDatabase() {
        List<Contacts> contactList = new ArrayList<>();
        contactList = dbHandler.getContacts();
        return contactList;
    }
}