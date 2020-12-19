package com.myapp.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectUID extends AppCompatActivity {

    EditText editTextView;
    Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_u_i_d);

        editTextView = findViewById(R.id.editText);
        connectButton = findViewById(R.id.button3);

    }

    public void getOtherDeviceUID (View view) {
        SharedPreferences sp = getSharedPreferences("UIDInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putString ("UID", editTextView.getText().toString());
        editor.apply();

        String message = "Your device has been paired";

        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();

    }
}