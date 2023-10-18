package com.example.payeaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "payEaseDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "payEaseSignUp";
    private static final String BANK_Table = "payEaseBank";
    private static final String Transaction_Table = "payEaseBTransaction";
    private static final String ID_COL = "id";
    private static final String USERNAME = "userName";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String C_PASSWORD = "C_password";
    private static final String BANK_ACC_NO = "Bank_ACC_NO";
    private static final String BANK_ACC_NAME = "Bank_ACC_NAME";
    private static final String BANK_NAME = "BANK_NAME";
    private static final String UPI_PIN = "UPI_PIN";
    private static final String IFSC_CODE = "IFSC_CODE";
    private static final String BALANCE = "BALANCE";
    private static final String Deduct = "deduct";
    private static final String USerID = "receiver";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT,"
                + EMAIL + " TEXT UNIQUE, "
                + PHONE + " TEXT UNIQUE, "
                + PASSWORD + " TEXT UNIQUE, "
                + C_PASSWORD + " TEXT UNIQUE)";
        db.execSQL(query);


        String query1 = "CREATE TABLE " + BANK_Table + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BANK_ACC_NO + " TEXT,"
                + BANK_ACC_NAME + " TEXT,"
                + BANK_NAME + " TEXT,"
                + UPI_PIN + " TEXT,"
                + IFSC_CODE + " TEXT,"
                + BALANCE + " TEXT,"
                + "FOREIGN KEY(" + BANK_ACC_NAME + ") REFERENCES " + TABLE_NAME + "(" + USERNAME + "))";
        db.execSQL(query1);

        String query4 = "CREATE TABLE Wallet" + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "USERNAME TEXT,"
                + BALANCE + " TEXT,"
                + "FOREIGN KEY(USERNAME) REFERENCES " + TABLE_NAME + "(" + USERNAME + "))";
        db.execSQL(query4);

        String query3 = "CREATE TABLE " + Transaction_Table + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT,"
                + BALANCE + " TEXT,"
                + USerID + " TEXT,"
                + Deduct + " TEXT, " +
                "FOREIGN KEY(" + USERNAME + ") REFERENCES " + TABLE_NAME + "(" + USERNAME + "), " +
                "FOREIGN KEY(" + BALANCE + ") REFERENCES " + BANK_Table + "(" + BALANCE + "))";
        db.execSQL(query3);

    }
    public void addNewUser(String username1, String email1, String phone1, String password1, String c_password1) {
        SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(USERNAME, username1);
            values.put(EMAIL, email1);
            values.put(PHONE, phone1);
            values.put(PASSWORD, password1);
            values.put(C_PASSWORD, c_password1);

            db.insert(TABLE_NAME, null, values);
    }

    public boolean isEmailUnique(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + EMAIL + " FROM " + TABLE_NAME + " WHERE " + EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean isUnique = cursor.getCount() == 0;
        cursor.close();
        return isUnique;
    }

    public boolean isPhoneUnique(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + PHONE + " FROM " + TABLE_NAME + " WHERE " + PHONE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{phone});
        boolean isUnique = cursor.getCount() == 0;
        cursor.close();
        return isUnique;
    }

    public boolean isPasswordUnique(String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + PASSWORD + " FROM " + TABLE_NAME + " WHERE " + PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{password});
        boolean isUnique = cursor.getCount() == 0;
        cursor.close();
        return isUnique;
    }

    public boolean isCPasswordUnique(String c_password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + C_PASSWORD + " FROM " + TABLE_NAME + " WHERE " + C_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{c_password});
        boolean isUnique = cursor.getCount() == 0;
        cursor.close();
        return isUnique;
    }


    public void addBankDetails(String bankAccNo, String bankAccName, String bankName, String upiPin, String ifscCode, String balance)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BANK_ACC_NO, bankAccNo);
        values.put(BANK_ACC_NAME, bankAccName);
        values.put(BANK_NAME, bankName);
        values.put(UPI_PIN, upiPin);
        values.put(IFSC_CODE, ifscCode);
        values.put(BALANCE, balance);

        db.insert(BANK_Table, null, values);
    }

    public String checkTransaction(String loggedInUsername, String Damt, String userIdName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Fetch balance1 for the logged-in user
            String selectBalance1Query = "SELECT " + BALANCE + " FROM " + BANK_Table +
                    " WHERE " + BANK_ACC_NAME + " = '" + loggedInUsername + "'";
            Cursor cursor1 = db.rawQuery(selectBalance1Query, null);

            if (cursor1.moveToFirst()) {
                double balance1 = Double.parseDouble(cursor1.getString(0));
                double transactionAmount = Double.parseDouble(Damt);

                // Check if balance1 is sufficient for the transaction
                if (balance1 >= transactionAmount) {
                    // Fetch balance2 for the target user
                    String selectBalance2Query = "SELECT " + BALANCE + " FROM " + BANK_Table +
                            " WHERE " + BANK_ACC_NAME + " = '" + userIdName + "'";
                    Cursor cursor2 = db.rawQuery(selectBalance2Query, null);

                    if (cursor2.moveToFirst()) {
                        double balance2 = Double.parseDouble(cursor2.getString(0));

                        // Update balance2 by adding the transaction amount
                        balance2 += transactionAmount;

                        // Update balance1 by subtracting the transaction amount
                        balance1 -= transactionAmount;

                        // Update the balances in the database
                        ContentValues values1 = new ContentValues();
                        values1.put(BALANCE, balance1);
                        db.update(BANK_Table, values1, BANK_ACC_NAME + " = ?", new String[]{loggedInUsername});

                        ContentValues values2 = new ContentValues();
                        values2.put(BALANCE, balance2);
                        db.update(BANK_Table, values2, BANK_ACC_NAME + " = ?", new String[]{userIdName});

                        // Insert a record into the transaction table
                        ContentValues transactionValues = new ContentValues();
                        transactionValues.put(USERNAME, loggedInUsername);
                        transactionValues.put(BALANCE, balance1);
                        transactionValues.put(USerID, userIdName);
                        transactionValues.put(Deduct, Damt);
                        db.insert(Transaction_Table, null, transactionValues);

                        cursor2.close();
                    }
                }

                cursor1.close();
                db.setTransactionSuccessful();

                // Return the updated balance as a String
                return String.valueOf(balance1);
            }
        }
        finally {
            db.endTransaction();
        }

        db.close();
        return loggedInUsername;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
