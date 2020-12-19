package com.myapp.groceryapp.network;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFireBaseIdService extends FirebaseMessagingService {

    public void onNewToken (String token) {
        super.onNewToken (token);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String refreshToken = FirebaseInstanceId.getInstance ().getToken();

        if (firebaseUser != null) {
            updateToken (refreshToken);
        }

        if (refreshToken != null) {
            Log.d ("grey", "refresh token is null");
        }

        Log.d ("grey", refreshToken);

        Log.d("red", "onNewToken ()1 runs");


    }

    private void updateToken(String refreshToken) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Token token = new Token (refreshToken);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("Tokens").child(uid).setValue(token);
    }
}