package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Balance_History_Activity extends AppCompatActivity {

    TextView balance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_history);

        DBHandler dbHandler = new DBHandler(this);

        balance = findViewById(R.id.balance);
        String bal_str = dbHandler.getBalance(this);
        balance.setText("Rs. " +bal_str+ "/-");

        ImageButton backbalancebtn = (ImageButton) findViewById(R.id.backbalancebtn);
        backbalancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Balance_History_Activity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}