package com.example.c15jmd.tiltfire.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jocke on 3/16/2018.
 */

public class Projectile extends GameObject implements Parcelable{

    public static Bitmap texture;
    private PointF velocity = new PointF(0 , -450f);

    public Projectile(int x, int y){
        super(x, y, texture.getWidth(), texture.getHeight());
    }

    private Projectile(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Projectile> CREATOR = new Creator<Projectile>() {
        @Override
        public Projectile createFromParcel(Parcel in) {
            return new Projectile(in);
        }

        @Override
        public Projectile[] newArray(int size) {
            return new Projectile[size];
        }
    };

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(texture, null, rect, paint);
    }

    @Override
    void update(double dt, ArrayList<GameObject> objects){
        rect.offset(velocity.x * (float)dt, velocity.y * (float)dt);
        getCollidingObjects(objects);
    }

    /**
     * Returns all colliding objects that are not the background or a projectile.
     * @param objects The objects of the game.
     * @return A list of the colliding objects.
     */
    private void getCollidingObjects(ArrayList<GameObject> objects){
        ArrayList<GameObject> objs = new ArrayList<>();

        for (GameObject object : objects) {
            if(object.rect.intersect(rect) && !object.equals(this)){
                if(object instanceof Enemy){
                    objects.remove(object);
                    objects.remove(this);
                    break;
                }
            }
        }

        if(rect.top < -rect.height() && objects.contains(this))
            objects.remove(this);
    }
}
