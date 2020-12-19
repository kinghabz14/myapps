package com.myapp.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    ListView listView;
    private String [] category_list = {"UID Information", "Pair Device"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        listView = findViewById(R.id.setting_list_view);
        Setting_Adapter setting_adapter = new Setting_Adapter(this, category_list);
        listView.setAdapter(setting_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.category_name);

                if (textView.getText() == "UID Information") {
                    openUIDInfo();
                } else if (textView.getText() == "Pair Device") {
                    openConnectUID();
                }
            }
        });

    }

    public void openUIDInfo () {
        Intent intent = new Intent (this, UIDInfo.class);
        String myUID = SendNotif.myUID;
        SharedPreferences sp = getSharedPreferences("UIDInfo", MODE_PRIVATE);
        String otherDeviceUID = sp.getString("UID", null);
        intent.putExtra("myUID", myUID);
        intent.putExtra("otherDeviceUID", otherDeviceUID);
        startActivity(intent);
    }

    public void openConnectUID () {
        Intent intent = new Intent (this, ConnectUID.class);
        startActivity(intent);
    }
}