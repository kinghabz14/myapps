package com.myapp.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class GroceryDetails extends AppCompatActivity {

    private String name;
    private Cursor cursor;
    private int amount;
    private Context context;
    private Path file;
    private Path chosenItem;
    private static String oldItemName = "no_item";
    private String itemName;
    private String itemAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocerydetails);
        context = GroceryDetails.this;

        getTheIntent();
        getValue();
        setImageView();
        setImageName();
        setQuantity();

    }

    public void getTheIntent () {
        Intent intent = getIntent();
        name = intent.getStringExtra(GroceryItem.intentName);

        Log.d ("red", "value of: " + name);

        Log.d ("red", "getTheIntent () has ran");
    }

    public void getValue () {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Log.d ("red", "the value of " + " " + name);

        cursor = db.query("GROCERY_TABLE", new String [] {"NAME", "PATHS"}, "NAME = ?", new String [] {name}, null, null, null);

        if (cursor == null) {
            Log.d ("grey", "cursor is not null");
        }

        //db.close();

        Log.d ("red", "getValue () has ran");
    }

    public void setImageView () {
        String imagePath;
        ImageView imageView = (ImageView) findViewById (R.id.detail_image);

        if (cursor == null) {
            Log.d ("grey", "cursor is null");
        }

        if (cursor.moveToFirst()) {
            imagePath = cursor.getString (1);

        } else {
            imagePath = null;
            Log.d ("red", "cursor returns false");
        }

        File file = new File (imagePath);

        if (file.exists()){
            Glide.with(this).load (file).into (imageView);
        }

        Log.d ("red", "setImageView () has ran");

    }

    public void setImageName () {
        String imageName;
        TextView textView = (TextView) findViewById (R.id.detail_groceryName);

        if (cursor.moveToFirst()) {
            imageName = cursor.getString (0);

        } else {
            imageName = null;
            Log.d ("red", "cursor returns false");
        }

        textView.setText(imageName);

        Log.d ("red", "setImageName () has ran");
    }

    public void setQuantity () {
        TextView textView = (TextView) findViewById(R.id.detail_amount);

        textView.setText("0");
    }

    public void incrementAmount (View view) {
        TextView textView = (TextView) findViewById(R.id.detail_amount);

        amount++;
        String value = Integer.toString(amount);
        textView.setText(value);
    }

    public void decrementAmount (View view) {
        TextView textView = (TextView) findViewById(R.id.detail_amount);

        if (amount > 0) {
            amount--;
        }

        String value = Integer.toString(amount);
        textView.setText(value);
    }

    public void saveData (View view ) throws IOException {
        String directoryName = "chosen_items";
        String listData = "list_data";

        TextView groceryName = (TextView) findViewById(R.id.detail_groceryName);
        TextView groceryAmount = (TextView) findViewById(R.id.detail_amount);

        String name = (String) groceryName.getText();
        String amount = (String) groceryAmount.getText();

        createDirectory(directoryName);
        createFile(listData);
        writeToFile(name, amount);

        String message = "Item has been added to list";

        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();

        oldItemName = itemName;
    }

    public void createDirectory (String name) {
        File filePath = context.getFilesDir ();
        String pathName = filePath + "/" + name;

        file = Paths.get(pathName);

        boolean dirExists = Files.exists(file);

        if (dirExists) {
            Log.d ("green", "filePath: " + filePath);
            Log.d ("green", "dirExists: " + dirExists);
            Log.d ("red", "this direcotry already exists");
        } else {

            try {

                Files.createDirectories(file);
                Log.d ("red", "the directory" + file.toString() + "has been created");
            } catch (IOException e) {

                Log.d ("red", "couldn't create directory");
                e.printStackTrace();
            }
        }

        Log.d ("red", "createDirectory has ran");
    }

    public void createFile (String name) {
        Path fileName = Paths.get (file + "/" + name + ".txt");

        if (Files.exists (fileName)) {
            Log.d("green", "list_data: " + fileName);
            chosenItem = Paths.get(String.valueOf(fileName));
            Log.d ("red", "the" + fileName.toString() + "already exists");

        } else {

            try {

                Log.d("green", "list_data: " + fileName);
                chosenItem = Files.createFile (fileName);
                Log.d ("green", "list data file has been created");
            } catch (IOException e) {

                e.printStackTrace();
                Log.d ("red", "failed to create file");
            }
        }
    }

    public void writeToFile (String localName, String localAmount) {

        itemName = localName + "/";
        itemAmount = localAmount + ",";

        try {
            Files.write(chosenItem, itemName.getBytes(), StandardOpenOption.APPEND);
            Files.write(chosenItem, itemAmount.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}