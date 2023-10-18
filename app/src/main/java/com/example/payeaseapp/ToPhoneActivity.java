package com.example.payeaseapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ToPhoneActivity extends AppCompatActivity {
    private ImageButton backButton;
    private EditText phoneNumberEditText, amtSend, upi2;
    private Button sendButton;
    public static final String SHARED_PREFS = "login_prefs";
    public static final String UPI_KEY = "upi_key";
    public static final String SHARED_PREFS_BANk = "bank_prefs";
    public static final String USERNAME_KEY = "username_key";
    SharedPreferences sharedPreferences;
    String user_str;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_phone);

        backButton = findViewById(R.id.backPhone);
        phoneNumberEditText = findViewById(R.id.phonenumber);
        amtSend = findViewById(R.id.amtSend);
        upi2 = findViewById(R.id.upi2);
        sendButton = findViewById(R.id.sendbtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ToPhoneActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetUsernameClick(v);
            }
        });
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user_str = sharedPreferences.getString(USERNAME_KEY, "");
    }

    public void onGetUsernameClick(View view) {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String amountToSend = amtSend.getText().toString();
        String upiPin = upi2.getText().toString();

        if (!phoneNumber.isEmpty() && !amountToSend.isEmpty() && !upiPin.isEmpty()) {
            DBHandler dbHelper = new DBHandler(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] columns = { "userName" };

            String selection = "phone = ?";
            String[] selectionArgs = { phoneNumber };

            Cursor cursor = db.query("payEaseSignUp", columns, selection, selectionArgs, null, null, null);
            int usernameIndex = cursor.getColumnIndex("userName");
            if (cursor != null && cursor.moveToFirst()) {
                String username = cursor.getString(usernameIndex);
                String result = dbHelper.checkTransaction(user_str, amountToSend, username);

                if (result == null) {
                    // Transaction failed (e.g., insufficient balance)
                    Intent i2 = new Intent(ToPhoneActivity.this, FailureActivity.class);
                    i2.putExtra("AmountPaid", amountToSend);
                    i2.putExtra("Result", "Insufficient Balance");
                    startActivity(i2);
                } else {
                    // Transaction successful
                    Intent i1 = new Intent(ToPhoneActivity.this, SuccessActivity.class);
                    i1.putExtra("Username", username);
                    i1.putExtra("AmountPaid", amountToSend);
                    i1.putExtra("UpdatedBalance", result);
                    startActivity(i1);
                }

                cursor.close();
            }
            else {
                Intent i2 = new Intent(ToPhoneActivity.this, FailureActivity.class);
                i2.putExtra("AmountPaid", amountToSend);
                i2.putExtra("Result", phoneNumber);
                startActivity(i2);
            }

            db.close();
        } else {
            Toast.makeText(this, "Please enter a phone number and amount to send.", Toast.LENGTH_SHORT).show();
        }
    }

}


