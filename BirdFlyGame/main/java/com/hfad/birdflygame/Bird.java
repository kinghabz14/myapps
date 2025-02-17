package com.hfad.birdflygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import static com.hfad.birdflygame.GameView.screenRatioX;
import static com.hfad.birdflygame.GameView.screenRatioY;

public class Bird {

    public int speed = 20;
    int x = 0;
    int y;
    int width;
    int height;
    Bitmap bird1;
    Bitmap bird2;
    Bitmap bird3;
    Bitmap bird4;
    int birdCounter = 1;
    Boolean wasShot = true;

    Bird(Resources res) {

        bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
        bird3 = BitmapFactory.decodeResource (res, R.drawable.bird3);
        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);

        width = bird1.getWidth ();
        height = bird1.getHeight ();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);
        bird3 = Bitmap.createScaledBitmap(bird3, width, height, false);
        bird4 = Bitmap.createScaledBitmap(bird4, width, height, false);

        y = -height;
    }

    Bitmap getBird () {

        if (birdCounter == 1) {
            birdCounter++;
            return bird1;
        }

        if (birdCounter == 2) {
            birdCounter++;
            return bird2;
        }

        if (birdCounter == 3) {
            birdCounter++;
            return bird3;
        }

        birdCounter = 1;

        return bird4;
    }

    Rect getCollisionShape () {
        return new Rect (x, y, x + width, y + height);
    }
}
