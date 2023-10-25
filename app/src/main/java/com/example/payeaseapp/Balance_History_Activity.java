package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Balance_History_Activity extends AppCompatActivity {

    TextView balance, receiverT, deductA;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_history);

        DBHandler dbHandler = new DBHandler(this);

        balance = findViewById(R.id.balance);
        receiverT = findViewById(R.id.receiverT);
        deductA = findViewById(R.id.deductA);

        String bal_str = dbHandler.getBalance(this);
        balance.setText("Rs. " +bal_str+ "/-");

        ArrayList<String> transactionHistory = dbHandler.getTransactionHistory(this);

        if (!transactionHistory.isEmpty()) {
            String receivr1 = transactionHistory.get(0);
            String deductA1 = transactionHistory.get(1);

            receiverT.setText("Paid to: " +receivr1);
            deductA.setText("Rs." +deductA1+ "/-");
        } else {
            deductA.setText("No Transaction History Available.");
        }

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