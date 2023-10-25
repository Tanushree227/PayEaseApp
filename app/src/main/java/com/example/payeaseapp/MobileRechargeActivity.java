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

public class MobileRechargeActivity extends AppCompatActivity {

    ImageButton backM;
    Spinner operatorM, packM;
    MaterialButton paybtnM;
    EditText upiM;
    String operatorStr, upiPin;
    int packPrice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);

        upiM = findViewById(R.id.upiM);


        operatorM = findViewById(R.id.operatorM);
        String[] mobileOperator = {"Jio", "Airtel", "V!", "BSNL", "MTNL"};

        List<String> operatorList = new ArrayList<>();
        operatorList.add("Jio");
        operatorList.add("Airtel");
        operatorList.add("VI");
        operatorList.add("BSNL");
        operatorList.add("MTNL");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mobileOperator);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorM.setAdapter(adapter);

        operatorM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                operatorStr = operatorList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                operatorStr = null;
            }
        });

        packM = findViewById(R.id.packM);
        String[] pack = {"666", "479", "259", "199", "2545"};

        List<Integer> packList = new ArrayList<>();
        packList.add(666);
        packList.add(479);
        packList.add(259);
        packList.add(199);
        packList.add(2545);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pack);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        packM.setAdapter(adapter1);

        packM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packPrice = packList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                packPrice = 0;
            }
        });

        paybtnM = findViewById(R.id.paybtnM);
        paybtnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upiPin = upiM.getText().toString();
                Toast.makeText(MobileRechargeActivity.this, "Mobile Operator: " +operatorStr+ " Recharge Pack: " +packPrice+ " UPI PIN: " +upiPin, Toast.LENGTH_SHORT).show();
            }
        });

        backM = findViewById(R.id.backM);
        backM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MobileRechargeActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}