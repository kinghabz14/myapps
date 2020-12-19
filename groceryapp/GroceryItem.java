package com.myapp.groceryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;

public class GroceryItem extends AppCompatActivity {

    MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
    SQLiteDatabase db;
    Cursor cursor;
    GroceryAdapter adapter;
    File file;
    String name;
    GroceryAdapter.ViewHolder vh;
    RecyclerView groceryRecycler;
    FirebaseUser firebaseUser;

    public static final String intentName = "com.myapp.groceryapp.name";

    private static final int STORAGE_PERMISSION_CODE = 101;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        StartDatabase sd = new StartDatabase();
        sd.execute(databaseHelper);

        Log.d ("red", "onCreate has ran");

    }

    public void startIntent () {
        Intent intent = new Intent (this, GroceryDetails.class);
        intent.putExtra(intentName, name);
        startActivity(intent);
    }

    public void sendNotification (View view) throws IOException {
        SendNotif sendNotif = new SendNotif();
        sendNotif.sendData(this);
    }

    public void accessDataBase () {

        try {

            db = databaseHelper.getReadableDatabase();

            cursor = db.query ("GROCERY_TABLE", new String[] {"NAME", "PATHS"}, null, null, null, null, null);

            //db.close();

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        Log.d ("red", "accessDataBase has ran");

    }

    public void createRecyclerView () {

        groceryRecycler = (RecyclerView) findViewById(R.id.grocery_recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(GroceryItem.this, 2, GridLayoutManager.VERTICAL, false);
        adapter = new GroceryAdapter (GroceryItem.this, cursor);

        groceryRecycler.setLayoutManager(layoutManager);
        groceryRecycler.setAdapter (adapter);

        Log.d ("red", "createRecyclerView has ran");

    }

    public void askForPermission (String permission, int requestCode) {
        if (isStoragePermissionGranted()) {
            ActivityCompat.requestPermissions(this, new String [] {permission}, requestCode);
        } else {
            Toast.makeText (this, "Permission already granted", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    public void onRequestPermissionsResult (int requestCode, String [] permissions, int [] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }

    public boolean isStoragePermissionGranted () {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }

        return false;
    }

    private class StartDatabase extends AsyncTask<MyDatabaseHelper, Void, Boolean> {

        protected void onPreExecute () {}

        @Override
        protected Boolean doInBackground(MyDatabaseHelper... myDatabaseHelpers) {

            try {
                accessDataBase();

                Log.d ("red", "doInBackground has ran");

                return true;
            } catch (SQLiteException e) {

                Log.d ("red", "doInBackground has ran");

                return false;

            }


        }

        protected void onPostExecute (Boolean success) {

            createRecyclerView();

            adapter.setOnItemClickListener(new GroceryAdapter.OnItemClickListener() {

                public void setFileName (int position) {

                    vh = (GroceryAdapter.ViewHolder) groceryRecycler.findViewHolderForAdapterPosition(position);

                    if (vh == null) {
                        Log.d ("red", "vh is null");
                    }

                    name = (String) vh.textView.getText();

                    Log.d ("red", "String value is: " + name);
                    Log.d ("red", adapter.info_text);
                }

                public void onItemClick(int position) {
                    startIntent();
                }

            });

            if (!success) {
                Toast toast = Toast.makeText(GroceryItem.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }

            Log.d ("red", "onPostExcecute has ran");

        }
    }

}