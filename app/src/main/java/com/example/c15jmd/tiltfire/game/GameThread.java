package com.example.c15jmd.tiltfire.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.c15jmd.tiltfire.GameView;

/**
 * Created by c15jmd on 2018-03-15.
 */

public class GameThread extends Thread implements Parcelable {

    private final double frameRate = 60.0;
    private final double updateFreq = 1000*1000*1000 / frameRate;
    private final double ns = 1000*1000*1000;

    private GameLogic gameLogic;

    private Resources gameResources;
    private Canvas gameCanvas;
    private Paint paint;
    private boolean running;
    private int fps;
    private final Object drawLock = new Object();

    private GameView view;

    public GameThread(Resources res, Canvas canvas, GameView view) {
        this.view = view;
        gameCanvas = canvas;
        gameResources = res;
        gameLogic = new GameLogic(res, canvas);
        paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
        paint.setTextSize(60);
        paint.setColor(Color.BLACK);
        running = true;
    }

    protected GameThread(Parcel in) {
        in.readValue(GameLogic.class.getClassLoader());
    }

    public static final Creator<GameThread> CREATOR = new Creator<GameThread>() {
        @Override
        public GameThread createFromParcel(Parcel in) {
            return new GameThread(in);
        }

        @Override
        public GameThread[] newArray(int size) {
            return new GameThread[size];
        }
    };

    @Override
    public void run()
    {
        double delta = 0;
        double lastTime = System.nanoTime();
        int frames = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();

            if((now - lastTime) >= updateFreq){
                delta += (now - lastTime);
                lastTime = now;

                while(delta >= updateFreq){
                    gameLogic.update(delta/ns);
                    delta -= updateFreq;
                }

                synchronized (drawLock){
                    draw();
                }

                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                frames = 0;
            }
        }
    }

    private void draw() {
        gameLogic.draw();
        gameCanvas.drawText(Integer.toString(fps),100,100, paint);
    }

    public void addShot(){
        gameLogic.addShot();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gameLogic, 0);
    }

    public void tilt(float x, float y) {
        gameLogic.tilt(x, y);
    }

    public Object getLock(){
        return drawLock;
    }
}
