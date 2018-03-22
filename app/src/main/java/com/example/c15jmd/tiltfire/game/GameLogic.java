package com.example.c15jmd.tiltfire.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by c15jmd on 2018-03-15.
 */

public class GameLogic implements Parcelable {

    private Random rnd;
    private Resources resources;
    private Canvas canvas;
    private Paint paint;

    private Background background;
    private ArrayList<GameObject> objects;
    private ArrayList<GameObject> objToAdd;

    public GameLogic(Resources res, Canvas cas){
        objects = new ArrayList<>();
        objToAdd = new ArrayList<>();
        rnd = new Random();

        resources = res;
        canvas = cas;

        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);

        background = new Background();
        objects.add(new Player(Background.size.right/2,Background.size.bottom - Enemy.texture.getHeight()*3));
    }

    private GameLogic(Parcel in) {
        GameObject[] o = (GameObject[]) in.readArray(GameObject.class.getClassLoader());
        objects = new ArrayList<>();
        for (GameObject gameObject : o) {
            objects.add(gameObject);
        }
    }

    public static final Creator<GameLogic> CREATOR = new Creator<GameLogic>() {
        @Override
        public GameLogic createFromParcel(Parcel in) {
            return new GameLogic(in);
        }

        @Override
        public GameLogic[] newArray(int size) {
            return new GameLogic[size];
        }
    };

    public void addShot(){
        PointF shotPos = null;

        for (GameObject object : objects) {
            if(object instanceof Player){
                if(((Player)object).canShoot()) {
                    shotPos = ((Player) object).shoot(Projectile.texture.getWidth(),
                                                        Projectile.texture.getHeight());
                }
                break;
            }
        }

        if(shotPos != null)
            objToAdd.add(new Projectile((int)shotPos.x, (int)shotPos.y));
    }

    public void update(double dt) {
        objects.addAll(objToAdd);
        objToAdd.clear();

        int size = objects.size();
        for (int i = 0; i < size; i++) {
            objects.get(i).update(dt, objects);
            if(objects.size() < size){
                i--;
                size = objects.size();
            }
        }
        /*
        for (Object object : objects.toArray().clone()) {
            if(objects.contains(object)){
                ((GameObject)object).update(dt, objects);
            }
        }*/

        if(rnd.nextInt(100) == 0){
            objects.add(new Enemy(rnd.nextInt(Background.size.right-Enemy.texture.getWidth()),
                                    -Enemy.texture.getHeight(), Enemy.texture.getWidth(),
                                    Enemy.texture.getHeight()));
        }
    }

    public void draw() {
        background.draw(canvas, paint);

        for (GameObject object : objects) {
            object.draw(canvas, paint);
        }

        if(objects.size() > 0)
            canvas.drawText(Float.toString(objects.get(0).getX()) +"  "+Float.toString(objects.get(0).getY())  , 0, 100, paint);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(objects.toArray());
    }

    public void tilt(float x, float y) {
        for (Object object : objects.toArray().clone()) {
            if(object instanceof Player){
                ((Player)object).setVelocity(x, y);
            }
        }
    }
}
