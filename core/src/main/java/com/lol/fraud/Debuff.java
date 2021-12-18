package com.lol.fraud;

public class Debuff {
    int duration;
    float damage;
    float speedmulti;
    Debuff(Debuff d){
        this.duration = d.duration;
        this.damage = d.damage;
        this.speedmulti = d.speedmulti;
    }
    Debuff(){
        duration = 60;
        damage = 0;
        speedmulti = 0.9f;
    }
}
