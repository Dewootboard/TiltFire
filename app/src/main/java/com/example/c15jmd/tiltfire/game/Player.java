package com.example.c15jmd.tiltfire.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jocke on 3/15/2018.
 */

public class Player extends GameObject implements Parcelable {

    public static Bitmap texture;
    private float cooldown;
    private float canShoot;
    private PointF velocity;
    private Health health;
    private int damage;

    public Player(int x, int y){
        super(x, y, Enemy.texture.getWidth(), Enemy.texture.getHeight());

        cooldown = 0.25f;
        velocity = new PointF(0,0);
        health = new Health(3);
        damage = 10;
        canShoot = 0;
    }

    private Player(Parcel in){
        super(in);
        health = new Health(in);
    }

    public boolean canShoot(){
        return canShoot <= 0;
    }

    public PointF shoot(int width, int height){
        float x = rect.left + rect.width()/2 - width/2;
        float y = rect.top - height;

        canShoot = cooldown;

        return new PointF(x, y);
    }

    public Player takeDamage(){
        health.removeHp();
        if(health.getHealth() == 0){
            return this;
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(texture, null, rect, paint);
        health.draw(canvas, paint);
    }

    @Override
    void update(double dt, ArrayList<GameObject> objects){
        if(!canShoot())
            canShoot -= dt;

        rect.offset(velocity.x * (float) dt, velocity.y * (float) dt);
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
                if(!(object instanceof Projectile)){
                    if(object instanceof Enemy){
                        objs.add(takeDamage());
                    }else if(object instanceof PowerUp){
                        cooldown *= 0.9f;
                    }

                    objs.add(object);

                    if(health == null){
                        objs.add(this);
                        break;
                    }
                }
            }
        }
        objects.removeAll(objs);
    }

    public void setVelocity(float x, float y){
        velocity.set(x, y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, 0);
        health.writeToParcel(dest, 0);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
