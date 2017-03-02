/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import java.util.List;
import java.util.Random;
import obj.BusStop;
import obj.People;

/**
 *
 * @author murata
 */
public class SimpleQueue implements QueueType{
    private String name;
    private Random rand = new Random();

    public SimpleQueue() {
        this.name = "Simple";
    }
    
    //毎ステップ　待ち行列発生(ステップ)回呼び出される
    @Override
    public void occureQueue(List<BusStop> busStops) {
        int i = rand.nextInt(busStops.size());
        
        BusStop busStop = busStops.get(i);
        busStop.queuePeople(new People(busStop));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setSeed(long seed) {
        if(seed != -1)
            rand.setSeed(seed);
    }
    
}
