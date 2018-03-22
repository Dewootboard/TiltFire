package com.example.c15jmd.tiltfire.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by jocke on 3/16/2018.
 */

public class PowerUp extends GameObject implements Parcelable {

    public static Bitmap texture;

    public PowerUp(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    protected PowerUp(Parcel in) {
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

    public static final Creator<PowerUp> CREATOR = new Creator<PowerUp>() {
        @Override
        public PowerUp createFromParcel(Parcel in) {
            return new PowerUp(in);
        }

        @Override
        public PowerUp[] newArray(int size) {
            return new PowerUp[size];
        }
    };

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(texture, null, rect, paint);
    }

    @Override
    void update(double dt, ArrayList<GameObject> objects){

    }
}
