package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Balance_History_Activity extends AppCompatActivity{

    TextView balance;
    ListView transactionListView;
    EditText addBalance;
    MaterialButton addMoney;

    private TransactionAdapter transactionAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_history);

        DBHandler dbHandler = new DBHandler(this);

        balance = findViewById(R.id.balance);
        addBalance = findViewById(R.id.addBalance);
        addMoney = findViewById(R.id.addMoney);

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

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addBal = addBalance.getText().toString();
                double addBal1 = Double.parseDouble(addBal);
                SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
                String user_str = sharedPreferences.getString("username_key", "");

                SQLiteDatabase db = dbHandler.getReadableDatabase();

                String[] columns = {"BALANCE"};
                String selection = "BANK_ACC_NAME = ?";
                String[] selectionArgs = {user_str};
                Cursor cursor = db.query("payEaseBank", columns, selection, selectionArgs, null, null, null);
                int balance1index = cursor.getColumnIndex("BALANCE");
                double balance1 = Double.parseDouble(cursor.getString(balance1index));
                balance1 += addBal1;
                ContentValues values1 = new ContentValues();
                values1.put("BALANCE", balance1);
                db.update("payEaseBank", values1, "BANK_ACC_NAME = ?", new String[]{user_str});
                String bal1 = Double.toString(balance1);
                balance.setText("Rs. " +bal1+ "/-");
            }
        });
    }
}