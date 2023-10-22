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

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_contacts);

        dbHandler = new DBHandler(this);
        dbHandler.insertContactData();

        ListView listView = findViewById(R.id.cList);
        List<String> list = new ArrayList<>();
        list.add("Megha");
        list.add("Divya");
        list.add("Nireeksha");
        list.add("Jyothika");
        list.add("Tanushree");

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);


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
}