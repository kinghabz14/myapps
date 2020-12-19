package com.myapp.groceryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "grocery_database";
    private static final int DB_VERSION = 10;
    private Context context;
    private BitmapFiles faan;
    private BitmapFiles bread;
    private BitmapFiles coriander;
    private BitmapFiles egg;
    private BitmapFiles flour;
    private BitmapFiles kitchen_tissue;
    private BitmapFiles milk;
    private BitmapFiles onion;
    private BitmapFiles potatoe;
    private BitmapFiles toilet_tissue;
    private BitmapFiles tomatoe;
    private BitmapFiles cucumber1;
    private BitmapFiles bell_pepper;
    private BitmapFiles double_cream;
    private BitmapFiles evaporated_milk;
    private BitmapFiles grapes;
    private BitmapFiles jam;
    private BitmapFiles ketchup;
    private BitmapFiles mayonaise;
    private BitmapFiles nutella;
    private BitmapFiles salt;
    private BitmapFiles single_cream;
    private BitmapFiles sugar;
    private BitmapFiles apple;
    private BitmapFiles bannana;
    private BitmapFiles broccoli;
    private BitmapFiles butter;
    private BitmapFiles crossiant;
    private BitmapFiles hand_wash;
    private BitmapFiles orange;
    private BitmapFiles rich_tea;
    private BitmapFiles mashroom;


    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GROCERY_DATA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "PATHS TEXT);");
        upgradeDatabase(db);

        Log.d ("red", "onCreateDB has ran");
    }

    public void upgradeDatabase (SQLiteDatabase db) {
        createBitmapFiles();
        addItems(db);

        Log.d ("red", "upgradeDatabase has ran");
    }

    public void createBitmapFiles () {
        faan = new BitmapFiles (context, R.drawable.faan, "faan");
        bread = new BitmapFiles (context, R.drawable.bread, "bread");
        coriander = new BitmapFiles (context, R.drawable.coriander, "coriander");
        egg = new BitmapFiles (context, R.drawable.eggs, "egg");
        flour = new BitmapFiles (context, R.drawable.flour, "flour");
        kitchen_tissue = new BitmapFiles (context, R.drawable.kitchen_tissue, "kitchen tissue");
        milk = new BitmapFiles (context, R.drawable.milk2, "milk");
        onion = new BitmapFiles (context, R.drawable.onion, "onion");
        potatoe = new BitmapFiles (context, R.drawable.potatoe, "potatoe");
        toilet_tissue = new BitmapFiles (context, R.drawable.toilettissue, "toilet tissue");
        tomatoe = new BitmapFiles (context, R.drawable.tomato, "tomatoe");
        apple = new BitmapFiles(context, R.drawable.apple, "apple");
        bannana = new BitmapFiles(context, R.drawable.bannana1, "bannana");
        broccoli = new BitmapFiles(context, R.drawable.broccoli, "broccoli");
        butter = new BitmapFiles(context, R.drawable.butter, "butter");
        crossiant = new BitmapFiles(context, R.drawable.crossiant, "crossiant");
        double_cream = new BitmapFiles(context, R.drawable.double_cream, "double cream");
        grapes = new BitmapFiles(context, R.drawable.grapes, "grapes");
        hand_wash = new BitmapFiles(context, R.drawable.hand_wash, "hand wash");
        ketchup = new BitmapFiles(context, R.drawable.ketchup, "ketchup");
        mayonaise = new BitmapFiles(context, R.drawable.mayonaise, "mayonaise");
        nutella = new BitmapFiles(context, R.drawable.nutella, "nutella");
        orange = new BitmapFiles(context, R.drawable.orange, "orange");
        bell_pepper = new BitmapFiles(context, R.drawable.pepper, "pepper");
        orange = new BitmapFiles(context, R.drawable.orange, "orange");
        rich_tea = new BitmapFiles(context, R.drawable.rich_tea, "rich tea");
        salt = new BitmapFiles(context, R.drawable.salt, "salt");
        single_cream = new BitmapFiles(context, R.drawable.single_cream, "single cream");
        sugar = new BitmapFiles(context, R.drawable.sugar, "sugar");
        evaporated_milk = new BitmapFiles(context, R.drawable.evaporated_milk, "evaporated milk");
        cucumber1 = new BitmapFiles(context, R.drawable.cucumber1, "cucumber");
        mashroom = new BitmapFiles(context, R.drawable.mushroom1, "mushroom");
        jam = new BitmapFiles(context, R.drawable.jam, "jam");

        Log.d ("red", "createBitmapFiles has ran");
    }

    public void addItems (SQLiteDatabase db) {
        insertItems (db, faan.name, faan.getImagePath ());
        insertItems(db, bread.name, bread.getImagePath());
        insertItems (db, coriander.name, coriander.getImagePath());
        insertItems (db, egg.name, egg.getImagePath());
        insertItems (db, flour.name, flour.getImagePath());
        insertItems (db, kitchen_tissue.name, kitchen_tissue.getImagePath());
        insertItems (db, milk.name, milk.getImagePath());
        insertItems (db, onion.name, onion.getImagePath());
        insertItems (db, potatoe.name, potatoe.getImagePath());
        insertItems (db, toilet_tissue.name, toilet_tissue.getImagePath());
        insertItems (db, tomatoe.name, tomatoe.getImagePath());
        insertItems(db, cucumber1.name, cucumber1.getImagePath());
        insertItems(db, bell_pepper.name, bell_pepper.getImagePath());
        insertItems(db, double_cream.name, double_cream.getImagePath());
        insertItems(db, evaporated_milk.name, evaporated_milk.getImagePath());
        insertItems(db, grapes.name, grapes.getImagePath());
        insertItems(db, jam.name, jam.getImagePath());
        insertItems(db, ketchup.name, ketchup.getImagePath());
        insertItems(db, mayonaise.name, mayonaise.getImagePath());
        insertItems(db, nutella.name, nutella.getImagePath());
        insertItems(db, salt.name, salt.getImagePath());
        insertItems(db, single_cream.name, single_cream.getImagePath());
        insertItems(db, sugar.name, sugar.getImagePath());
        insertItems(db, apple.name, apple.getImagePath());
        insertItems(db, bannana.name, bannana.getImagePath());
        insertItems(db, broccoli.name, broccoli.getImagePath());
        insertItems(db, butter.name, butter.getImagePath());
        insertItems(db, crossiant.name, crossiant.getImagePath());
        insertItems(db, hand_wash.name, hand_wash.getImagePath());
        insertItems(db, orange.name, orange.getImagePath());
        insertItems(db, rich_tea.name, rich_tea.getImagePath());
        insertItems(db, mashroom.name, mashroom.getImagePath());

        Log.d ("red", "addItems has ran");

    }

    public void insertItems (SQLiteDatabase db, String name, String filePath) {
        ContentValues contentValues = new ContentValues();
        contentValues.put ("NAME", name);
        contentValues.put ("PATHS", filePath);

        db.insert ("GROCERY_TABLE", null, contentValues);

        Log.d ("red", "insertItems has ran");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE GROCERY_DATA");

        db.execSQL("CREATE TABLE GROCERY_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "PATHS TEXT);");

        upgradeDatabase(db);

        Log.d ("red", "onUpgrade has ran");
    }
}

