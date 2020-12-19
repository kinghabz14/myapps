package com.myapp.groceryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BitmapFiles {

    public String imagePath;
    public String name;
    private Bitmap bitmap;
    private int resourceId;
    private Context context;
    private OutputStream outputStream;
    private Path file;
    private Path imageFile;

    public BitmapFiles (Context context, int resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
        this.context = context;

        convertToBitmap();
        saveBitmap();
        setImagePath();

        Log.d ("red", "bitmapFiles constructor has ran");
    }

    public void convertToBitmap () {
        bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        if (bitmap == null) {
            Log.d ("blue", "bitmap is null");
        }

        Log.d ("red", "the convertToBitmap has run");

    }

    public void saveBitmap () {

        if (isExternalStorageWritable()) {
            Log.d ("blue", "external storage is writable");
        } else {
            Log.d ("blue", "external storage is not writable");
        }

        createDirectory("items");
        createFile(name);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte [] byteArray = stream.toByteArray();

        if (imageFile == null) {
            Log.d ("red", imageFile.toString() + " " + "is null");
        }

        if (byteArray == null) {
            Log.d ("red", "byteArray is null");
        }

        try {
            Files.write (imageFile, byteArray);
        } catch (IOException e) {
            Log.d ("red", "jpg couldn't be written to" + " " + imageFile);
            e.printStackTrace();
        }

        Log.d ("red", "the saveBitmap has run");
    }

    public void createDirectory (String name) {
        File filePath = context.getFilesDir();
        String pathName = filePath + "/" + name;

        file = Paths.get(pathName);

        boolean dirExists = Files.exists(file);

        if (dirExists) {
            Log.d ("blue", "this directory already exists");
        } else {

            try {
                Files.createDirectories(file);
                Log.d ("blue", "the directory" + file + "has been created");
            } catch (IOException e) {
                Log.d ("blue", "couldn't create directory");
                e.printStackTrace();
            }
        }

        Log.d ("red", "createDirectory has ran");
    }

    public void createFile (String name) {
        Path image = Paths.get(file + "/" + name + ".jpg");

        if (Files.exists(image)) {
            Log.d ("red", "the" + image.toString() + "already exists");

            imageFile = Paths.get(image.toString());
        } else {
            try {
                imageFile = Files.createFile(image);
                Log.d ("red", "the" + imageFile.toString() + "has been created");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d ("red", "failed to create the file");
            }
        }

        Log.d ("red", "createFile has ran");
    }

    public boolean isExternalStorageWritable () {
        File fileDir = context.getFilesDir();

       String state = fileDir.toString();

        if (Environment.MEDIA_MOUNTED.equals (state)) {

            Log.d ("red", "isExternalStorageWritable has ran");

            return true;
        }

        Log.d ("red", "isExternalStorageWritable has ran");

        return false;
    }

    public void setImagePath () {
        imagePath = imageFile.toString();

        Log.d ("red", "the setImagePath has run");
    }

    public String getImagePath () {
        Log.d ("red", "getImagePath has ran");

        return imagePath;
    }


}

