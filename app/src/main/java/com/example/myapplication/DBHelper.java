package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE Table Products(name TEXT(60) PRIMARY KEY, unit TEXT(60),  price DOUBLE(255), expiry TEXT(60), inventory INT(255), inventoryCost DOUBLE(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP Table if EXISTS Products");
    }

    public Boolean insertProductData(String name, String unit, double price, String expiry, int inventory, double inventoryCost) {
        SQLiteDatabase DB = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("unit", unit);
            contentValues.put("price", price);
            contentValues.put("expiry", expiry);
            contentValues.put("inventory",inventory);
            contentValues.put("inventoryCost", inventoryCost);

            long result = DB.insert("Products", null, contentValues);

            if (result == 1) {
                return false;
            }else{
                return true;
            }
    }

    public Boolean updateProductData(String name, String unit, double price, String expiry, int inventory, double inventoryCost) {
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("unit", unit);
        contentValues.put("price", price);
        contentValues.put("expiry", expiry);
        contentValues.put("inventory", inventory);
        contentValues.put("inventoryCost", inventoryCost);

        Cursor cursor = DB.rawQuery("SELECT * FROM Products WHERE name = ?", new String [] {name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Products", contentValues, "name=?",new String []{name});

            if (result == 1) {
                return false;
            }else{
                return true;
            }
        } else
            {
            return false;
        }
    }

    public Boolean deleteProductData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM Products WHERE name = ?", new String [] {name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Products", "name=?",new String []{name});

            if (result == 1) {
                return false;
            }else{
                return true;
            }
        } else
        {
            return false;
        }
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM Products",null);
        return cursor;
    }
}
