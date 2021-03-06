package com.edu4sure.myerp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    ListView l;
    ArrayList<String> column_1 = new ArrayList<String>();
    ArrayList<String> column_2 = new ArrayList<>();
    ArrayList<String> column_3 = new ArrayList<>();
    ArrayList<String> column_4 = new ArrayList<>();
    EditText nameEditText, t1, t2, t3;
    mysqldatabase m;
    Button saveBtn;
    int pos = 0;

    public void add(View v) {

        Dialog d = new Dialog(ProductsActivity.this);
        d.setTitle("ADD PRODUCTS");
        d.setContentView(R.layout.customdialog);

        //INITIALIZE VIEWS
        nameEditText = (EditText) d.findViewById(R.id.nameEditTxt);
        t1 = d.findViewById(R.id.nameEditTxt1);
        t2 = d.findViewById(R.id.nameEditTxt2);
        t3 = d.findViewById(R.id.nameEditTxt3);
        saveBtn = (Button) d.findViewById(R.id.saveBtn);


        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = nameEditText.getText().toString();
                String name = t1.getText().toString();
                String quantity = t2.getText().toString();
                String price = t3.getText().toString();
                Boolean state = MainActivity.m.insert_data_products(id, name, quantity, price);
                if (state) {
                    Toast.makeText(ProductsActivity.this, "SAVED", Toast.LENGTH_SHORT).show();
                    display();

                } else {
                    Toast.makeText(ProductsActivity.this, "Sorry error", Toast.LENGTH_SHORT).show();
                }
                //Log.i("info","INSERT INTO `Products` VALUES ("+id+","+name+","+quantity+","+price+")");
                //String query="INSERT INTO `Products` VALUES ("+id+","+name+","+quantity+","+price+")";
                       /*column_1.add(id);
                       column_2.add(name);
                       column_3.add(quantity);
                       column_4.add(price);
                       MainActivity.database.execSQL(query,null);
                       l.notify();*/


            }
        });


        d.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Intent i = getIntent();

        display();
    }

    public void display() {
        column_1.clear();
        column_2.clear();
        column_3.clear();
        column_4.clear();
        SQLiteDatabase database = this.openOrCreateDatabase("Database", MODE_PRIVATE, null, null);
        Log.i("inside", "Products");
        column_1.add("Product_id");
        column_2.add("Product name");
        column_3.add("Quantity");
        column_4.add("Price");
        Cursor c1 = database.rawQuery("select * from 'Products'", null);
        c1.moveToFirst();
        if (c1.getCount() != 0) {
            while (!c1.isAfterLast()) {
                column_1.add(c1.getString(0));
                column_2.add(c1.getString(1));
                column_3.add(c1.getString(2));
                column_4.add(c1.getString(3));

                c1.moveToNext();
            }
        } else {
            column_1.add("NULL");
            column_2.add("NULL");
            column_3.add("NULL");
            column_4.add("NULL");
        }
        l = findViewById(R.id.listview3);
        l.setAdapter(new Prodcuttableadapter(this, R.layout.producttable, column_1, column_2, column_3, column_4));
    }
}
