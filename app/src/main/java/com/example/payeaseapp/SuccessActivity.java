package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    ImageButton backS;
    TextView receiver, amtPaid, updateBalance;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        receiver = findViewById(R.id.receiver);
        amtPaid = findViewById(R.id.paidAmt);
        updateBalance = findViewById(R.id.updatedBal);
        backS = findViewById(R.id.backSuccess);

        Intent i1 = getIntent();
        String username = i1.getStringExtra("Username");
        String amtPaid1 = i1.getStringExtra("AmountPaid");
        String updateB = i1.getStringExtra("UpdatedBalance");

        receiver.setText("Amount Paid to: " +username);
        amtPaid.setText("Amount Paid: " +amtPaid1);
        updateBalance.setText("Updated Balance: " +updateB);

        backS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessActivity.this, ToPhoneActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}