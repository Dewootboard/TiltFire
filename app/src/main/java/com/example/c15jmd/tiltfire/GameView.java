package com.example.c15jmd.tiltfire;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.c15jmd.tiltfire.game.GameThread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;

import static android.graphics.Bitmap.DENSITY_NONE;

/**
 * Created by c15jmd on 2018-03-14.
 */

public class GameView extends View {

    private GameThread gameThread;
    private Rect rect;
    private Bitmap gameMap;
    private Canvas gameCanvas;
    private Paint paint;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        gameCanvas = new Canvas();
        gameCanvas.setDensity(DENSITY_NONE);

        gameThread = new GameThread(getResources(), gameCanvas, this);
        gameThread.start();
    }

    public GameThread getGameThread(){
        return gameThread;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect = new Rect(0,0,w , h);

        gameMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        gameCanvas.setBitmap(gameMap);

        paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
        paint.setTextSize(50);
    }

    @Override
    public void onDraw(Canvas canvas) {
        synchronized (gameThread.getLock()){
            super.onDraw(canvas);
            canvas.drawBitmap(gameMap, null, rect, paint);
        }
    }

    public void redraw(){
        this.invalidate();
    }

    @Override
    public boolean performClick() {
        super.performClick();
        gameThread.addShot();
        return true;
    }
}
