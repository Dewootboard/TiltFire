package com.example.c15jmd.tiltfire;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.c15jmd.tiltfire.game.Background;
import com.example.c15jmd.tiltfire.game.BackgroundTile;
import com.example.c15jmd.tiltfire.game.Enemy;
import com.example.c15jmd.tiltfire.game.GameThread;
import com.example.c15jmd.tiltfire.game.Health;
import com.example.c15jmd.tiltfire.game.Player;
import com.example.c15jmd.tiltfire.game.PowerUp;
import com.example.c15jmd.tiltfire.game.Projectile;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jocke on 3/16/2018.
 */

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    public static final String PLAYER = "GAME.PLAYER";

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameThread game;
    private GameView canvas;
    private View.OnTouchListener onTouchListener;
    private Timer drawTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadTextures(getApplicationContext());
        setSize();

        setContentView(R.layout.activity_game);

        canvas = findViewById(R.id.canvas);

        if(savedInstanceState != null && savedInstanceState.containsKey(PLAYER)) {
            //Load all
        }

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if(sensorManager != null)
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer == null)
            Toast.makeText(this,"Could not create sensor", Toast.LENGTH_LONG).show();

        start();
    }

    private void setSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Background.size = new Rect(0, 0, size.x, size.y);
        Log.d("BG_SIZE", "X:"+size.x+", Y:"+size.y);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }



    /**
     * Creates the GameTimer and schedules the first event
     */
    private void start() {
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(onTouchListener == null){
            onTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            canvas.performClick();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return false;
                }
            };
        }

        canvas.setOnTouchListener(onTouchListener);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        game = canvas.getGameThread();

        drawTimer = new Timer("drawTimer");
        schedule();
    }

    private void schedule() {
        drawTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(GameActivity.this::update);
            }
        }, 16);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,accelerometer);
    }

    /**
     * Updates the game one step
     */
    private synchronized void update() {
        canvas.redraw();
        schedule();
    }

    /**
     * Loads the textures required by the game from the GameActivity Context
     * @param context the Context to load resources from
     */
    private void loadTextures(Context context) {
        BackgroundTile.texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        Projectile.texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile);
        Enemy.texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        Player.texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.aircraft);
        Health.texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        PowerUp.texture = BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if(game != null) {
                if(Math.abs(sensorEvent.values[0]) > 0.01){
                    game.tilt(-sensorEvent.values[0]*100, 0);
                }else{
                    game.tilt(0, 0);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}