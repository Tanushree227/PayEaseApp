package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ElectricityRecharge extends AppCompatActivity {
    EditText amtSendE, upi2E;
    MaterialButton sendbtnE;

    String electricityBoardStr, amount, upiPin;
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
                Toast.makeText(ElectricityRecharge.this, "Electricty Board: " +electricityBoardStr+ " Amount: " +amount+ " UPI PIN: " +upiPin, Toast.LENGTH_SHORT).show();
            }
        });
    }
}