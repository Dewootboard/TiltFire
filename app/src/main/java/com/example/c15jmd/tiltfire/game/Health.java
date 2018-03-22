package com.example.c15jmd.tiltfire.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jocke on 3/19/2018.
 */

public class Health implements Parcelable {

    public static Bitmap texture;
    private ArrayList<RectF> health;

    public Health(int initHealth){
        health = new ArrayList<>();

        for(int i = 0; i < initHealth; i++) {
            float left, top, right, bottom;
            left = getInitX() + (Enemy.texture.getWidth() + 5)*i;
            bottom = Background.size.bottom;
            top = Background.size.bottom - Enemy.texture.getHeight();
            right = left + Enemy.texture.getWidth();
            health.add(new RectF(left, top, right, bottom));
        }
    }

    protected Health(Parcel in) {
        this(in.readInt());
    }

    public static final Creator<Health> CREATOR = new Creator<Health>() {
        @Override
        public Health createFromParcel(Parcel in) {
            return new Health(in);
        }

        @Override
        public Health[] newArray(int size) {
            return new Health[size];
        }
    };

    public void draw(Canvas canvas, Paint paint){
        for (int i = 0; i < health.size(); i++) {
            canvas.drawBitmap(texture, null, health.get(i) ,paint);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(health.size());
    }

    public float getInitX() {
        return Background.size.width()/2 - Enemy.texture.getWidth()*1.5f;
    }

    public void removeHp() {
        health.remove(health.size()-1);
        if(health.size() == 0)
            health = null;
    }

    public int getHealth(){
        return health.size();
    }
}
