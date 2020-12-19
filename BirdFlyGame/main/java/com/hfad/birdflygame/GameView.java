package com.hfad.birdflygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1;
    private Background background2;
    private  int screenX;
    private  int screenY;
    private Paint paint;
    public static float screenRatioX;
    public static float screenRatioY;
    private SurfaceHolder surfaceHolder;
    private Flight flight;
    private boolean down = true;
    private boolean gate = true;
    private Timer timer = new Timer ();
    private TimerTask task;
    int check;
    private List<Bullet> bullets;
    Bird [] birds;
    Random rand;
    Boolean isGameOver = false;
    Boolean heldDown = false;
    int score = 0;
    SharedPreferences pref;
    private GameActivity activity;
    private SoundPool soundPool;
    int sound;

    public GameView(GameActivity activity, final int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        pref = activity.getSharedPreferences ("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();


        } else {
            soundPool = new SoundPool (1, AudioManager.STREAM_MUSIC, 0);
        }

        sound = soundPool.load (activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        rand = new Random();

        background1 = new Background (screenX, screenY, getResources());
        background2 = new Background (screenX, screenY, getResources());

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize (128);
        paint.setColor (Color.WHITE);

        flight = new Flight(screenY, getResources());

        bullets = new ArrayList<>();

        birds = new Bird [4];

        for (int i = 0; i < 4; i++) {
            Bird bird = new Bird (getResources ());
            birds [i] = bird;
        }

        task = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };

        timer.scheduleAtFixedRate(task, 0, 20);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                isPlaying = true;
                thread = new Thread (GameView.this);
                thread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                try {
                    isPlaying = false;
                    thread.join ();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void run() {
        while (isPlaying) {
            draw ();
            sleep ();
        }
    }

    public void update () {
        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0 ) {
            background2.x = screenX;
        }

        flyCheck();

        List <Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {

            if (bullet.x > screenX) {
                trash.add (bullet);
            }

            bullet.x += 50 * screenRatioX;

            for (Bird bird : birds) {

                if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape ())) {
                    score ++;
                    bird.x = -500;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;
                }
            }
        }

        for (Bullet bullet : trash) {
            bullets.remove (bullet);
        }

        for (Bird bird : birds) {
            bird.x -= bird.speed;

            if (bird.x + bird.width < 0) {

                if (!bird.wasShot) {
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                bird.speed = rand.nextInt(bound);

                if (bird.speed < 10 * screenRatioX) {
                    bird.speed = (int) (10 * screenRatioX);
                }

                bird.x = screenX;
                bird.y = rand.nextInt (screenY  - bird.height);

                bird.wasShot = false;
            }


            if (Rect.intersects(flight.getCollisionShape(), bird.getCollisionShape())) {
                isGameOver = true;
                return;
            }
        }


    }

    public void draw () {
        Canvas canvas = getHolder().lockCanvas();

        if (!surfaceHolder.getSurface().isValid()) {
        } else {
            canvas.drawBitmap (background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap (background2.background, background2.x, background2.y, paint);

            for (Bird bird : birds) {
                canvas.drawBitmap(bird.getBird (), bird.x, bird.y, paint);
            }

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap (flight.getDead (), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore ();
                waitBeforeExiting ();
                return;
            }

            canvas.drawBitmap (flight.getFlight(), flight.x, flight.y, paint);


            for (Bullet bullet : bullets) {
                canvas.drawBitmap (bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep (3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {

        if (pref.getInt ("highScore", 0) < score) {
            SharedPreferences.Editor editor = pref.edit ();
            editor.putInt("highScore", score);
            editor.apply();
        }
    }

    private void flyCheck () {
        if (flight.isGoingUp && flight.y < 1140 && gate && flight.y !=0) {
            flight.y -= 30;
        }

        if (!flight.isGoingUp && flight.y < 1140 && gate) {
            flight.y += 30;
        }

        if (flight.y < 0) {
            flight.y = 0;
        }

        if (!flight.isGoingUp && flight.y > 882) {


            if (check >= 258) {
                flight.y--;

                if (gate && !flight.isGoingUp) {
                    gate = false;
                }
            } else if (check < 258 && gate) {
                check++;
            }
        } else if (flight.isGoingUp && flight.y > 882) {
            flight.y-= 30;
        }

        if (flight.y == 882 && !gate) {
            gate = true;
            check = 0;
        }

        if (flight.isGoingUp) {
            gate = true;
        }
    }

    public void sleep () {
        try {
            Thread.sleep (17);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume () {
        isPlaying = true;
        thread = new Thread (this);
        thread.start();
    }


    public void pause () {
        try {
            isPlaying = false;
            thread.join ();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean onTouchEvent (MotionEvent event) {
        switch (event.getAction () & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                if (event.getX () < screenX / 2) {
                    flight.isGoingUp = true;
                    heldDown = true;
                }

                break;
            case MotionEvent.ACTION_UP :
                flight.isGoingUp = false;
                heldDown = false;

            case MotionEvent.ACTION_POINTER_DOWN :

                if (!heldDown && event.getX() > screenX / 2) {
                    flight.toShoot++;
                }
                break;

        }

      return true;
    }

    public void newBullet() {

        if (!pref.getBoolean ("isMute", false)) {
            soundPool.play (sound, 1, 1, 0, 0, 1);
        }

        Bullet bullet = new Bullet (getResources ());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add (bullet);
    }

}
