package com.example.c15jmd.tiltfire.game;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.ArrayList;

/**
 * Created by jocke on 3/15/2018.
 */

@SuppressLint("ParcelCreator")
public class Background{


    public static Rect size;
    private ArrayList<BackgroundTile> tiles;

    public Background(){
        tiles = new ArrayList<>();
        setBackground();
    }

    public float getWidth(){
        return size.width();
    }

    public float getHeight(){
        return size.height();
    }

    public void draw(Canvas canvas, Paint paint) {
        for (GameObject tile : tiles) {
            tile.draw(canvas, paint);
        }
    }

    private void setBackground(){
        tiles.clear();

        for (int x = 0; x <= getWidth(); x+=BackgroundTile.texture.getWidth()*6) {
            for (int y = 0; y <= getHeight(); y+=BackgroundTile.texture.getHeight()*6) {
                tiles.add(new BackgroundTile(x, y, BackgroundTile.texture.getWidth()*6,
                                            BackgroundTile.texture.getHeight()*6));
            }
        }
    }
}
