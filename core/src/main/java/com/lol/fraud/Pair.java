package com.lol.fraud;

public class Pair<T, Float> {
    T key;
    Float value;

    public Pair(T item, Float priority) {
        this.key = item;
        this.value = priority;
    }

    public T getKey(){
        return key;
    }
    public Float getValue(){
        return value;
    }
}
