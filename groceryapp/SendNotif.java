package com.myapp.groceryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.myapp.groceryapp.network.APIService;
import com.myapp.groceryapp.network.MyClient;
import com.myapp.groceryapp.network.MyResponse;
import com.myapp.groceryapp.network.NotificationSender;
import com.myapp.groceryapp.network.Token;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotif extends FirebaseMessagingService {
    private APIService apiservice;
    FirebaseUser firebaseUser;
    public static String otherDeviceUID;
    public static String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static ArrayList <ItemData> itemArray = new ArrayList<>();
    private Context context;
    public String itemList;

    public void sendData (final Context context) throws IOException {
        this.context = context;
        apiservice = MyClient.getClient("https://fcm.googleapis.com/").create(APIService.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences sp = context.getSharedPreferences("UIDInfo", MODE_PRIVATE);

        otherDeviceUID = sp.getString("UID", null);

        //Log.d("gold", otherDeviceUID);

        //otherDeviceUID = "HH6Ex125GLciIcxG8lC6eTKbmz62";

        if (apiservice != null) {
            Log.d ("red", "apiservice is not null");
        }

        if (firebaseUser != null) {
            Log.d("grey", firebaseUser.toString());
        }

        if (otherDeviceUID != null) {
            Log.d ("grey", "The value of otherDeviceUID: " + otherDeviceUID);
        }

        if (myUID != null) {
            Log.d ("grey", "The value of myUID: " + myUID);
        }


        if (otherDeviceUID != null) {
            Log.d("red", "if statement has ran");
            readFromFIle();

            FirebaseDatabase.getInstance().getReference().child ("Tokens").child (otherDeviceUID).child("token").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userToken = snapshot.getValue(String.class);

                    Log.d("grey", "value of userToken: " + userToken);


                    if (userToken != null) {
                        Log.d("yellow", "user token is not null");
                    }

                    if (itemList == null) {
                        String message = "You haven't added any items to the list";
                        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                        toast.show();

                    }

                    if (itemList != null) {
                        sendNotification (userToken, "Your Grocery List!", itemList);
                        String message = "List has been successfully sent";

                        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                        Log.d ("red", "toast has been created");
                        toast.show();

                    } else {
                        String message = "You haven't added any items to the list";
                        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                        toast.show();
                    }

                    Log.d ("red", "onDataChange () runs");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d ("red", "reading from the firebase database failed");

                }
            });
        } else {
            String message = "Opps looks like you're not connected to a device";
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();
        }
        
        updateToken ();
    }

    public void onNewToken (String token) {
        super.onNewToken (token);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String refreshToken = FirebaseInstanceId.getInstance ().getToken();

        
        Log.d("red", "onNewToken ()1 runs");


    }

    private void updateToken() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();

        String localUid = firebaseUser.getUid();

        Token token = new Token (refreshToken);

        Log.d ("red", token.getToken());

        FirebaseDatabase.getInstance().getReference("Tokens").child (localUid).setValue(token);

        Log.d ("red", "updateToken () runs");
    }

    public void sendNotification (String userToken, String title, String itemList) {
        Data data = new Data (title, itemList);

        NotificationSender sender = new NotificationSender(data, userToken);

        apiservice.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {

                    if (response.body().success != 1) {
                        Log.d("red", "failed to send");
                    }

                    Log.d ("red", "if statement response.code ran");
                }

                Log.d ("red", "onResponse () has ran");
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.d ("red", "onFailure () has ran");

            }
        });

        Log.d ("red", "sendNotification () runs");
        String message = "Grocery List Successfully sent";
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void readFromFIle () throws IOException {

        File filePath = context.getFilesDir();
        String pathName = filePath + "/chosen_items/list_data.txt";

        Path file = Paths.get (pathName);
        List<String> text = Files.readAllLines(file);

        if (text.size() != 0) {
            itemList = text.get(0);

        } else {
            itemList = null;
        }

        try {
            BufferedWriter writer = Files.newBufferedWriter(file);
            writer.flush();
            Log.d("red", "the file has been flushed");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d ("green", "the value of itemList is:" + itemList);

    }

}
