package com.myapp.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UIDInfo extends AppCompatActivity {

    private TextView myUID;
    private TextView pairedUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i_d_info);

        Intent intent = getIntent();

        String uidInfo = intent.getStringExtra("myUID");
        String otherDeviceUID = intent.getStringExtra("otherDeviceUID");

        myUID = findViewById(R.id.textView3);
        pairedUID = findViewById(R.id.textView5);

        setMyUIDText(uidInfo);
        connectedTo(otherDeviceUID);
    }

    public void setMyUIDText (String MyuidInfo) {
        myUID.setText(MyuidInfo);
    }

    public void connectedTo (String otherDeviceUID) {
        pairedUID.setText(otherDeviceUID);
    }
}