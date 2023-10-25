package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

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

public class DthRecharge extends AppCompatActivity {

    ImageButton backD;
    Spinner operatorD, packD;
    MaterialButton paybtnD;
    EditText upiD;
    String operatorStr, upiPin;
    int packPrice;

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
                Toast.makeText(DthRecharge.this, "DTH Operator: " +operatorStr+ " DTH Pack: " +packPrice+ " UPI PIN: " +upiPin, Toast.LENGTH_SHORT).show();
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