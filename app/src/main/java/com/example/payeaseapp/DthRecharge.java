package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class DthRecharge extends AppCompatActivity {

    ImageButton backD;
    Spinner operatorD, packD;
    MaterialButton paybtnD;
    EditText upiD;
    String operatorStr, upiPin;
    int packPrice;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dth_recharge);
        upiD = findViewById(R.id.upiD);


        operatorD = findViewById(R.id.operatorD);
        String[] DTHOperator = {"TATA Play", "Airtel Digital TV", "Dish TV", "d2h", "Sun Direct"};

        List<String> operatorList = new ArrayList<>();
        operatorList.add("TATA Play");
        operatorList.add("Airtel Digital TV");
        operatorList.add("Dish TV");
        operatorList.add("d2h");
        operatorList.add("Sun Direct");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DTHOperator);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorD.setAdapter(adapter);

        operatorD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                operatorStr = operatorList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                operatorStr = null;
            }
        });

        packD = findViewById(R.id.packD);
        String[] pack = {"229", "899", "1599", "329", "1749"};

        List<Integer> packList = new ArrayList<>();
        packList.add(229);
        packList.add(899);
        packList.add(1599);
        packList.add(329);
        packList.add(1749);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pack);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        packD.setAdapter(adapter1);

        packD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packPrice = packList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                packPrice = 0;
            }
        });

        paybtnD = findViewById(R.id.paybtnD);
        paybtnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upiPin = upiD.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
                String user_str = sharedPreferences.getString("username_key", "");

                dbHandler = new DBHandler(DthRecharge.this);
                SQLiteDatabase db = dbHandler.getReadableDatabase();

                String[] columns = {"BALANCE"};
                String selection = "BANK_ACC_NAME = ?";
                String[] selectionArgs = {user_str};
                Cursor cursor = db.query("payEaseBank", columns, selection, selectionArgs, null, null, null);

                if (cursor.moveToFirst()) {
                    int balance1index = cursor.getColumnIndex("BALANCE");

                    if (balance1index >= 0) {
                        double balance1 = Double.parseDouble(cursor.getString(balance1index));
                        if (balance1 >= packPrice) {
                            // Perform the transaction
                            balance1 -= packPrice;
                            ContentValues values1 = new ContentValues();
                            values1.put("BALANCE", balance1);
                            db.update("payEaseBank", values1, "BANK_ACC_NAME = ?", new String[]{user_str});

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = dateFormat.format(new Date());
                            ContentValues transactionValues = new ContentValues();
                            transactionValues.put("userName", user_str);
                            transactionValues.put("BALANCE", balance1);
                            transactionValues.put("receiver", operatorStr);
                            transactionValues.put("deduct", packPrice);
                            transactionValues.put("timedate", formattedDate);
                            db.insert("payEaseBTransaction", null, transactionValues);

                            String bal1 = Double.toString(balance1);
                            String packP = Double.toString(packPrice);

                            Intent i1 = new Intent(DthRecharge.this, SuccessActivity.class);
                            i1.putExtra("Username", operatorStr);
                            i1.putExtra("AmountPaid", packP);
                            i1.putExtra("UpdatedBalance", bal1);
                            startActivity(i1);

                            Toast.makeText(DthRecharge.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DthRecharge.this, "Insufficient balance for the transaction", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DthRecharge.this, "Invalid details", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(DthRecharge.this, "No bank account found for the logged-in user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backD = findViewById(R.id.backD);
        backD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DthRecharge.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}