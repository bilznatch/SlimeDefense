package com.lol.fraud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    Vector2 position = new Vector2();
    Vector2 moveVec = new Vector2();
    float damage, speed;
    Debuff debuff;
    Enemy target;
    boolean alive = true;
    Color color;
    int size;
    Projectile(float x, float y, int projectileSize, float damage, float speed, Debuff d, Enemy target, Color color){
        position.set(x,y);
        this.speed = speed;
        this.damage = damage;
        this.debuff = d;
        this.target = target;
        this.color = color;
        this.size = projectileSize;
    }
    public void update(){
        if(target!=null){
            if(target.hp <= 0){
                alive=false;
            }else{
                move();
            }
        }
    }
    public void move(){
        if(this.position.dst(target.position)<=speed){
            if(target.hp>damage){
                target.hp-=damage;
                target.addDebuff(debuff);
                alive=false;
            }else{
                target.hp=0;
            }
        }else{
            moveVec.set(speed,0);
            moveVec.setAngleRad((float)Math.atan2(target.position.y-this.position.y,target.position.x-this.position.x));
            this.position.add(moveVec);
        }
    }
}
