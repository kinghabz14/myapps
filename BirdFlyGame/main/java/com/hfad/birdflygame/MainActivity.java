package com.hfad.birdflygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sizeOfScreen ();

        TextView highScoretxt = findViewById (R.id.highScoretxt);

        final SharedPreferences pref = getSharedPreferences("game", Context.MODE_PRIVATE);

        highScoretxt.setText ("HighScore: " + pref.getInt ("highScore", 0));

        isMute = pref.getBoolean("isMute", false);

        final ImageView volumeCtrl = findViewById (R.id.volumeCtrl);

        if (isMute) {
            volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        } else {
            volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }

        volumeCtrl.setOnClickListener (new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isMute = !isMute;

                if (isMute) {
                    volumeCtrl.setImageResource (R.drawable.ic_volume_off_black_24dp);
                } else {
                    volumeCtrl.setImageResource (R.drawable.ic_volume_up_black_24dp);
                }

                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean ("isMute", isMute);
                editor.apply ();
            }
        });
    }

    public void startGame (View view) {
        Intent intent = new Intent (MainActivity.this, GameActivity.class);
        startActivity (intent);
    }

    public void sizeOfScreen () {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize (size);
        int width = size.x;
        int height = size.y;
    }
}
