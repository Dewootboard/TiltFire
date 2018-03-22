package com.example.c15jmd.tiltfire.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by c15jmd on 2018-03-15.
 */

public class Enemy extends GameObject implements Parcelable {

    public static Bitmap texture;
    private float speed = 300f;

    public Enemy(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    protected Enemy(Parcel in) {
        super(in);
    }

    @Override
    void update(double dt, ArrayList<GameObject> objects) {
        rect.offset(0, speed * (float) dt);

        if(rect.top >= 2560 - Enemy.texture.getHeight()){
            objects.remove(this);
        }
    }

    public static final Creator<Enemy> CREATOR = new Creator<Enemy>() {
        @Override
        public Enemy createFromParcel(Parcel in) {
            return new Enemy(in);
        }

        @Override
        public Enemy[] newArray(int size) {
            return new Enemy[size];
        }
    };

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(texture, null, rect, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, 0);
    }
}
