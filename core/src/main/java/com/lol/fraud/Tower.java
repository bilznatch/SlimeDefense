package com.lol.fraud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class Tower {
    Vector2 position = new Vector2();
    float damage;
    Debuff debuff = new Debuff();
    float range, projectileSpeed, fireRate = 0, cooldown = 0;
    Enemy target;
    int cost = 10;
    float rotation;
    int ID, projectileSize;
    Color projectileColor = new Color();
    Vector2 rotationVector = new Vector2();
    Tower(Tower model){
        this.position.set(model.position);
        this.damage = model.damage;
        this.debuff = new Debuff(model.debuff);
        this.range = model.range;
        this.projectileSpeed = model.projectileSpeed;
        this.cost = model.cost;
        this.fireRate = model.fireRate;
        this.ID = model.ID;
        this.projectileColor = model.projectileColor.cpy();
        this.projectileSize = model.projectileSize;
    }
    Tower(float x, float y, int ID){
        this.position.x=x+15;
        this.position.y=y+15;
        setToTower(ID);
    }

    public void getTarget(ArrayList<Enemy> enemyList, int targetingType) {
        if(enemyList.isEmpty())return;
        target=enemyList.get(0);
        if (targetingType == 0) {
            getNearestTarget(enemyList);
        } else if (targetingType == 1) {
            getLowestHPTarget(enemyList);
        } else {
            getHighestHPTarget(enemyList);
        }
    }
    public Projectile fire(ArrayList<Enemy> enemyList, int targetingType){
        if(cooldown<=0){
            getTarget(enemyList,targetingType);
            if(distance(target)<=range){
                rotation = (float) Math.atan2(target.position.y - this.position.y, target.position.x - this.position.x);
                cooldown= (60f/fireRate);
                return new Projectile(this.position.x,this.position.y,this.projectileSize,this.damage,this.projectileSpeed,this.debuff,this.target,projectileColor);
            }
        }else{
            cooldown--;
        }
        return null;
    }
    public float distance(Enemy e){
        if(e==null)return 10000;
        return this.position.dst(e.position);
    }
    public void getNearestTarget(ArrayList<Enemy> enemyList){
        for(Enemy e: enemyList){
            if(distance(target)>distance(e)){
                target = e;
            }
        }
    }
    public void getLowestHPTarget(ArrayList<Enemy> enemyList){
        for(Enemy e: enemyList){
            if(target.hp > e.hp){
                target = e;
            }
        }
    }
    public void getHighestHPTarget(ArrayList<Enemy> enemyList){
        for(Enemy e: enemyList){
            if(target.hp < e.hp){
                target = e;
            }
        }
    }
    public void draw(ShapeDrawer sd){
        if(ID==1){
            sd.setColor(Color.GRAY);
            rotationVector.set(10,0);
            rotationVector.rotateDeg(rotation*MathUtils.radDeg-90);
            sd.filledRectangle(this.position.x-15+rotationVector.x,this.position.y-5+rotationVector.y,30,10, rotation);
            rotationVector.set(10,0);
            rotationVector.rotateDeg(rotation*MathUtils.radDeg+90);
            sd.filledRectangle(this.position.x-15+rotationVector.x,this.position.y-5+rotationVector.y,30,10, rotation);
        }else if(ID==2){
            sd.setColor(Color.CYAN);
            sd.filledPolygon(this.position.x,this.position.y,6,10, rotation);
        }else if(ID==3){
            sd.setColor(Color.ORANGE);
            sd.filledPolygon(this.position.x,this.position.y,3,15, rotation);
        }else if(ID==4){
            sd.setColor(Color.GRAY);
            sd.filledRectangle(this.position.x-15,this.position.y-5,30,10, rotation);
        }else if(ID==5){
            sd.setColor(Color.GRAY);
            sd.filledRectangle(this.position.x-15,this.position.y-5,30,10, rotation);
            sd.setColor(Color.YELLOW);
            rotationVector.set(0,10);
            rotationVector.rotateDeg(rotation*MathUtils.radDeg-90);
            sd.filledRectangle(this.position.x-10+rotationVector.x,this.position.y-2.5f+rotationVector.y,20,5, rotation-(90/MathUtils.radDeg));
        }

    }

    public void setToTower(int ID){
        this.ID = ID;
        if(ID==1){
            damage = 25;
            debuff.damage=0;
            debuff.duration=60;
            debuff.speedmulti=1;
            projectileSpeed = 5;
            fireRate = 0.5f;
            range = 300;
            projectileColor.set(Color.RED);
            projectileSize=3;
        }else if(ID==2){
            damage = 0;
            debuff.damage=0.05f;
            debuff.duration=60;
            debuff.speedmulti=0.2f;
            projectileSpeed = 1;
            fireRate = 1;
            range = 150;
            projectileColor.set(Color.CYAN);
            projectileSize = 5;
        }else if(ID==3){
            damage = 0;
            debuff.damage=0.2f;
            debuff.duration=60;
            debuff.speedmulti=0.7f;
            projectileSpeed = 1f;
            fireRate = 30;
            range = 100;
            projectileColor.set(Color.ORANGE);
            projectileSize = 8;
        }else if(ID==4){
            damage = 0.5f;
            debuff.damage=0.05f;
            debuff.duration=60;
            debuff.speedmulti=0.7f;
            projectileSpeed = 10;
            fireRate = 30;
            range = 300;
            projectileColor.set(Color.YELLOW);
            projectileSize = 2;
        }else if(ID==5){
            damage = 0;
            debuff.damage=0f;
            debuff.duration=600;
            debuff.speedmulti=0f;
            projectileSpeed = 2f;
            fireRate = 0.3f;
            range = 1000;
            projectileColor.set(Color.YELLOW);
        }
    }
}
