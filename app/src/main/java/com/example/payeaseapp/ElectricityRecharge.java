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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElectricityRecharge extends AppCompatActivity {
    EditText amtSendE, upi2E;
    MaterialButton sendbtnE;

    String electricityBoardStr, amount, upiPin;
    DBHandler dbHandler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_recharge);

        amtSendE = findViewById(R.id.amtSendE);
        upi2E = findViewById(R.id.upi2E);
        sendbtnE = findViewById(R.id.sendbtnE);
        ImageButton backE = findViewById(R.id.backE);

        backE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ElectricityRecharge.this, HomeActivity.class);
                startActivity(i);
            }
        });

        Spinner operatorE = findViewById(R.id.operatorE);
        String[] electricityBoard = {"MESCOM Electricity Bill Office", "MESCOM", "KEB- Karnataka Electricity Board", "Hiriadka Section Office", "Udupi Electricity Board"};

        List<String> boardList = new ArrayList<>();
        boardList.add("MESCOM Electricity Bill Office");
        boardList.add("MESCOM");
        boardList.add("KEB- Karnataka Electricity Board");
        boardList.add("Hiriadka Section Office");
        boardList.add("Udupi Electricity Board");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, electricityBoard);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorE.setAdapter(adapter);

        operatorE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                electricityBoardStr = boardList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                electricityBoardStr = null;
            }
        });

        sendbtnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amtSendE.getText().toString();
                upiPin = upi2E.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
                String user_str = sharedPreferences.getString("username_key", "");

                dbHandler = new DBHandler(ElectricityRecharge.this);
                SQLiteDatabase db = dbHandler.getReadableDatabase();

                String[] columns = {"BALANCE"};
                String selection = "BANK_ACC_NAME = ?";
                String[] selectionArgs = {user_str};
                Cursor cursor = db.query("payEaseBank", columns, selection, selectionArgs, null, null, null);

                if (cursor.moveToFirst()) {
                    int balance1index = cursor.getColumnIndex("BALANCE");

                    if (balance1index >= 0) {
                        double balance1 = Double.parseDouble(cursor.getString(balance1index));
                        double transactionAmount = Double.parseDouble(amount);
                        if (balance1 >= transactionAmount) {
                            // Perform the transaction
                            balance1 -= transactionAmount;
                            ContentValues values1 = new ContentValues();
                            values1.put("BALANCE", balance1);
                            db.update("payEaseBank", values1, "BANK_ACC_NAME = ?", new String[]{user_str});

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = dateFormat.format(new Date());
                            ContentValues transactionValues = new ContentValues();
                            transactionValues.put("userName", user_str);
                            transactionValues.put("BALANCE", balance1);
                            transactionValues.put("receiver", electricityBoardStr);
                            transactionValues.put("deduct", amount);
                            transactionValues.put("timedate", formattedDate);
                            db.insert("payEaseBTransaction", null, transactionValues);

                            String bal1 = Double.toString(balance1);

                            Intent i1 = new Intent(ElectricityRecharge.this, SuccessActivity.class);
                            i1.putExtra("Username", electricityBoardStr);
                            i1.putExtra("AmountPaid", amount);
                            i1.putExtra("UpdatedBalance", bal1);
                            startActivity(i1);

                            Toast.makeText(ElectricityRecharge.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ElectricityRecharge.this, "Insufficient balance for the transaction", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ElectricityRecharge.this, "Invalid details", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ElectricityRecharge.this, "No bank account found for the logged-in user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}