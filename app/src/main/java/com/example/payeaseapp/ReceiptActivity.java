package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {

    ImageButton backbtn;
    TextView amountPaid, fromText, toText, date;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        backbtn = findViewById(R.id.backbtnReceipt);
        amountPaid = findViewById(R.id.amountPaid);
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);
        date = findViewById(R.id.date);

        Intent i1 = getIntent();
        String sender = i1.getStringExtra("From");
        String receiver = i1.getStringExtra("To");
        String amt = i1.getStringExtra("Amount");
        String date1 = i1.getStringExtra("Date");

        amountPaid.setText("₹ "+amt);
        fromText.setText(sender);
        toText.setText(receiver);
        date.setText("Transaction done at " +date1);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(ReceiptActivity.this, Balance_History_Activity.class);
                startActivity(i1);
                finish();
            }
        });
    }
}