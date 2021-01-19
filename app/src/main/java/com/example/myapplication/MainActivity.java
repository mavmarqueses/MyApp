package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private static final int PICK_IMAGE = 100;

    EditText name, unit, price, inventory;
    Button upload, expiry, insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        unit = findViewById(R.id.unit);
        price = findViewById(R.id.price);
        inventory = findViewById(R.id.inventory);

        upload = findViewById(R.id.upload);
        expiry = findViewById(R.id.expiry);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        initDatePicker();
        expiry.setText(getTodaysDate());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_PICK, Uri.parse(
                        "contents://media/internal/images/media"
                ));
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTEXT = name.getText().toString();
                String unitTEXT = unit.getText().toString();
                String priceTEXT = price.getText().toString();
                double priceDOUBLE = Double.parseDouble(priceTEXT);
                String expiryTEXT = expiry.getText().toString();
                String inventoryTEXT = inventory.getText().toString();
                int inventoryINT = Integer.parseInt(inventoryTEXT);
                double inventoryDOUBLE = Double.parseDouble(inventoryTEXT);

                double inventoryCostDOUBLE = inventoryDOUBLE * priceDOUBLE;

                Boolean checkInserted = DB.insertProductData(nameTEXT, unitTEXT, priceDOUBLE, expiryTEXT, inventoryINT, inventoryCostDOUBLE);
                if(checkInserted == true) {
                    Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Saving Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTEXT = name.getText().toString();
                String unitTEXT = unit.getText().toString();
                String priceTEXT = price.getText().toString();
                double priceDOUBLE = Double.parseDouble(priceTEXT);
                String expiryTEXT = expiry.getText().toString();
                String inventoryTEXT = inventory.getText().toString();
                int inventoryINT = Integer.parseInt(inventoryTEXT);
                double inventoryDOUBLE = Double.parseDouble(inventoryTEXT);

                double inventoryCostDOUBLE = inventoryDOUBLE * priceDOUBLE;

                Boolean checkUpdated = DB.updateProductData(nameTEXT, unitTEXT, priceDOUBLE, expiryTEXT, inventoryINT, inventoryCostDOUBLE);
                if(checkUpdated == true) {
                    Toast.makeText(MainActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Updating Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTEXT = name.getText().toString();

                Boolean checkDeleted = DB.deleteProductData(nameTEXT);
                if(checkDeleted == true) {
                    Toast.makeText(MainActivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Deleting Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getData();
                if (res.getCount()==0) {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("Name : "+res.getString(0)+"\n");
                    buffer.append("Unit : "+res.getString(1)+"\n");
                    buffer.append("Price : "+res.getString(2)+"\n");
                    buffer.append("Date of Expiry : "+res.getString(3)+"\n");
                    buffer.append("Inventory : "+res.getString(4)+"\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Product Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(dayOfMonth, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth,month,year);
                expiry.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "January";
        if (month == 2)
            return "February";
        if (month == 3)
            return "March";
        if (month == 4)
            return "April";
        if (month == 5)
            return "May";
        if (month == 6)
            return "June";
        if (month == 7)
            return "July";
        if (month == 8)
            return "August";
        if (month == 9)
            return "September";
        if (month == 10)
            return "October";
        if (month == 11)
            return "November";
        if (month == 12)
            return "December";

        return "January";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}