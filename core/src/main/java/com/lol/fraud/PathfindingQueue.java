package com.lol.fraud;

import java.util.ArrayList;
import java.util.List;

public class PathfindingQueue<T> {
    private List<Pair<T, Float>> elements = new ArrayList<Pair<T, Float>>();

    public int count(){
        return elements.size();
    }

    public void enqueue(T item, float priority){
        elements.add(new Pair<>(item, priority));
    }

    public T dequeue()
    {
        int bestIndex = 0;

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getValue() < elements.get(bestIndex).getValue()) {
                bestIndex = i;
            }
        }

        T bestItem = elements.get(bestIndex).getKey();
        elements.remove(bestIndex);
        return bestItem;
    }
}