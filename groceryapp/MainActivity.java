package com.myapp.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            firebaseAuth = FirebaseAuth.getInstance();

            if (firebaseAuth == null) {
                Log.d("red", "firebaseAuth is null");
            }


            Log.d ("red", "mainActivity has ran");
        }

        public void itemButton (View view) {

            firebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Log.d ("red", "anonymous log in successful");

                    }
                }
            });


            Intent intent = new Intent(this, GroceryItem.class);
            startActivity (intent);

            Log.d ("red", "itemButton has ran");
        }

        public boolean onCreateOptionsMenu (Menu menu) {
            getMenuInflater().inflate(R.menu.main_action, menu);
            return super.onCreateOptionsMenu(menu);
        }

        public boolean onOptionsItemSelected (MenuItem item) {
            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    }