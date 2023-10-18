package com.example.payeaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class DigitalWalletActivity extends AppCompatActivity {

    private TextView walletAmountTextView;
    private EditText addMoneyEditText;
    private DBHandler dbHandler;

    Button backwallet;

    MaterialButton proceedbtn;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet);

        walletAmountTextView = findViewById(R.id.walletamount);
        addMoneyEditText = findViewById(R.id.addmoney);
        dbHandler = new DBHandler(this);
        backwallet = findViewById(R.id.backwallet);
        proceedbtn = findViewById(R.id.proceedbtn);

        backwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(DigitalWalletActivity.this, HomeActivity.class);
                startActivity(i1);
            }
        });

        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProceedToAddMoney(v);
            }
        });
    }

    public void onProceedToAddMoney(View view) {
        String addMoneyText = addMoneyEditText.getText().toString().trim();
        if (addMoneyText.isEmpty()) {
            Toast.makeText(this, "Please enter the amount to add.", Toast.LENGTH_SHORT).show();
        } else {
            double amountToAdd = Double.parseDouble(addMoneyText);

            // You can implement validation for the amountToAdd here if needed.

            // Deduct the balance from the logged-in user and add to the digital wallet
            boolean success = dbHandler.transferToDigitalWallet(amountToAdd, this);

            if (success) {
                Toast.makeText(this, "Money added to digital wallet.", Toast.LENGTH_SHORT).show();

                // Clear the addMoneyEditText and update the displayed wallet balance
                addMoneyEditText.setText("");
                updateWalletBalance();
            } else {
                Toast.makeText(this, "Insufficient balance.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateWalletBalance() {
        // Fetch and display the current wallet balance
        double walletBalance = dbHandler.getDigitalWalletBalance();

        walletAmountTextView.setText("Rs. " + walletBalance + "/-");
    }
}
