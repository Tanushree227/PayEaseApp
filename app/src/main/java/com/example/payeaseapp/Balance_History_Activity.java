package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Balance_History_Activity extends AppCompatActivity{

    TextView balance;
    ListView transactionListView;

    private TransactionAdapter transactionAdapter;
    //receiverT, deductA, date;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_history);

        DBHandler dbHandler = new DBHandler(this);

        balance = findViewById(R.id.balance);
        /*receiverT = findViewById(R.id.receiverT);
        deductA = findViewById(R.id.deductA);
        date = findViewById(R.id.date);*/

        String bal_str = dbHandler.getBalance(this);
        balance.setText("Rs. " +bal_str+ "/-");

        ArrayList<TransactionClass> transactionHistory = dbHandler.getTransactionHistory(this);

        transactionListView = findViewById(R.id.TranList);

        ArrayList<TransactionClass> transactionList = dbHandler.getTransactionHistory(this);
        transactionAdapter = new TransactionAdapter(this, transactionList,dbHandler);
        transactionListView.setAdapter(transactionAdapter);

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