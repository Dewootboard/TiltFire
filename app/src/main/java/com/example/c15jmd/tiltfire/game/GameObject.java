package com.example.c15jmd.tiltfire.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by c15jmd on 2018-03-15.
 */
public abstract class GameObject implements Parcelable{

    RectF rect;

    /**
     * Creates a rectangle for the given game object.
     * @param x the left side of the object.
     * @param y the top side of the object.
     * @param width the width of the object.
     * @param height the height of the object.
     */
    public GameObject(int x,int y,int width ,int height){
        rect = new RectF(x ,y ,x+width ,y+height);
    }

    public GameObject(Parcel in){
        rect = (RectF)in.readValue(RectF.class.getClassLoader());
    }

    /**
     * Updates this object with the given delta time.
     * @param dt The given time since last update.
     */
    abstract void update(double dt, ArrayList<GameObject> objects);

    /**
     * A method to draw this object onto a given canvas.
     * @param canvas The given canvas to draw this object on.
     */
    abstract void draw(Canvas canvas, Paint paint);

    /**
     * returns the y value of the game object.
     * @return an integer representing the game object's y value
     */
    public float getY(){
        return rect.top;
    }

    /**
     * returns the x value of the game object.
     * @return an integer representing the game object's x value
     */
    public float getX(){
        return rect.left;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rect);
    }
}