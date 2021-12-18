package com.lol.fraud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Tile {
    boolean blocked, buildable = true, isGoal, isSpawn;
    int weight;
    Tower tower;
    int x, y;
    float px,py;
    Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.px = x*32+45.5f;
        this.py = y*32+45.5f;
    }
    public void draw(SpriteBatch batch, ShapeDrawer sd){
        sd.filledRectangle(px,py,30,30, Color.GOLD);
    }
    public boolean equalTo(Tile t){
        return this.x == t.x && this.y == t.y;
    }
    public float distance(Tile t){
        return (float) Math.sqrt(Math.pow(this.x-t.x,2)+Math.pow(this.y-t.y,2));
        //return Math.abs(this.x-p.x) + Math.abs(this.y-p.y);
    }
    public float distance(float x, float y){
        return (float) Math.sqrt(Math.pow(this.px+15-x,2)+Math.pow(this.py+15-y,2));
    }
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
}
