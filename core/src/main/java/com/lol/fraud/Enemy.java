package com.lol.fraud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Enemy {
    Vector2 position = new Vector2();
    float hp,basespeed,speed;
    ArrayList<Debuff> debuffs = new ArrayList<>();
    HashMap<Tile,Tile> path;
    Tile current;
    Vector2 moveVector = new Vector2();
    boolean hitEnd = false;
    int timeAlive;
    Enemy(float x, float y, float hp, float basespeed, Tile startTile){
        this.position.x=x;
        this.position.y=y;
        this.hp=hp;
        this.basespeed=basespeed;
        current = startTile;
    }
    public Tile getNext(){
        return path.get(current);
    }
    public void getClosest(){
        float currentdist = 10000;
        float distance;
        for(Map.Entry<Tile,Tile> e: path.entrySet()){
            distance = e.getKey().distance(this.position.x,this.position.y);
            if(distance < currentdist){
                current = e.getKey();
                currentdist = distance;
            }
        }
    }
    public void update(){
        timeAlive++;
        processDebuffs();
        move();
    }
    public void move(){
        if(current!=null){
            if(!path.containsKey(current)){
                getClosest();
            }
            float distance = current.distance(position.x,position.y);
            if(distance<=speed){
                speed-=distance;
                this.position.x=current.px+15;
                this.position.y=current.py+15;
                current = getNext();
                if(!MathUtils.isEqual(speed,0)){
                    move();
                }
            }else{
                moveVector.set(speed,0);
                moveVector.setAngleRad((float) Math.atan2(current.py+15-position.y,current.px+15-position.x));
                this.position.x += moveVector.x;
                this.position.y += moveVector.y;
            }
        }else{
            hitEnd=true;
        }
    }
    public void processDebuffs(){
        speed = basespeed;
        if(!debuffs.isEmpty()) {
            for (int i = debuffs.size() - 1; i >= 0; i--) {
                speed *= debuffs.get(i).speedmulti;
                hp -= debuffs.get(i).damage;
                debuffs.get(i).duration--;
                if (debuffs.get(i).duration <= 0) {
                    debuffs.remove(i);
                }
            }
        }
    }
    public void addDebuff(Debuff d){
        this.debuffs.add(d);
    }
    public void draw(ShapeDrawer sd){
        sd.setColor(Color.OLIVE);
        if(timeAlive%32<=8){
            sd.filledEllipse(position.x,position.y,15,10);
        }else if(timeAlive%32<=16){
            sd.filledEllipse(position.x,position.y,15,15);
        }else if(timeAlive%32<=24){
            sd.filledEllipse(position.x,position.y,15,20);
        }else{
            sd.filledEllipse(position.x,position.y,10,15);
        }
    }
}
