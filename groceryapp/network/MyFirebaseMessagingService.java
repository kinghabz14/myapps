package com.myapp.groceryapp.network;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.myapp.groceryapp.ItemView;
import com.myapp.groceryapp.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String title;
    String itemList;
    ArrayList <String> itemNames = new ArrayList<>();
    ArrayList <String> itemAmount = new ArrayList<>();
    Context context;
    PendingIntent pendingIntent;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        title = remoteMessage.getData().get ("Title");

        itemList =  remoteMessage.getData().get("ItemList");

        if (title == null) {
            Log.d("red", "title is null");
        }

        if (itemList == null) {
            Log.d("red", "itemList is null");
        }

        createItemArray(itemList);
        createPendingIntent(itemNames, itemAmount);


        String CHANNEL_ID = "MESSAGE";
        String CHANNEL_NAME = "MESSAGE";
        String message = "You received a grocery list";

        NotificationManagerCompat manager = NotificationManagerCompat.from(MyFirebaseMessagingService.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

        }

        Notification notification = new NotificationCompat.Builder(MyFirebaseMessagingService.this, CHANNEL_ID).
                setSmallIcon(R.drawable.ic_launcher_foreground).
                setContentTitle(title).
                setContentText (message).
                setContentIntent(pendingIntent).
                build();

        manager.notify(getRandomNumber (), notification);

        Log.d("blue", "onMessageReceived method has ran");
    }

    private static int getRandomNumber() {
        Date dd = new Date ();
        SimpleDateFormat ft = new SimpleDateFormat("mmssSS");
        String s = ft.format(dd);

        return Integer.parseInt(s);
    }

    private void createItemArray (String itemList) {

        if (itemList == null) {
            Log.d ("red", "itemList is null");
        }
        String [] result = itemList.split (",");

        for (String list : result) {
            String [] item = list.split("/");

            itemNames.add(item [0]);
            itemAmount.add(item [1]);

            Arrays.fill(item, null);
        }

        Log.d ("red", "createItemArray has ran");
    }

    private void createPendingIntent (ArrayList <String> itemNames, ArrayList <String> itemAmount) {
        if (itemNames == null) {
            Log.d ("red", "itemNames is null");
        }

        if (itemAmount == null) {
            Log.d ("red", "itemAmount is null");
        }

        context = getApplicationContext();
        Intent intent = new Intent (context, ItemView.class);
        intent.putStringArrayListExtra("itemName", itemNames);
        intent.putStringArrayListExtra("itemAmount", itemAmount);

        pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d ("red", "createPendingIntent has ran");
    }
}
