package com.example.c15jmd.tiltfire.game;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by jocke on 3/17/2018.
 */

@SuppressLint("ParcelCreator")
public class BackgroundTile extends GameObject {

    public static Bitmap texture;

    public BackgroundTile(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    @Override
    void update(double dt, ArrayList<GameObject> objects) {
    }

    @Override
    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(texture, null, rect, paint);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
