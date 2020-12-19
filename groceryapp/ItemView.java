package com.myapp.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemView extends AppCompatActivity {

    ListView listView;
    ArrayList <String> itemNames;
    ArrayList <String> itemAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        Intent intent = getIntent();

        itemNames = intent.getStringArrayListExtra("itemName");
        itemAmount = intent.getStringArrayListExtra("itemAmount");

        listView = findViewById(R.id.list_view);
        ItemViewAdapter itemViewAdapter = new ItemViewAdapter (this, itemNames, itemAmount);
        listView.setAdapter(itemViewAdapter);
    }
}