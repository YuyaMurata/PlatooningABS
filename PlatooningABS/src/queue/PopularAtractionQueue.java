/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import obj.BusStop;
import obj.BusStops;
import org.apache.commons.math3.random.RandomDataGenerator;

/**
 *
 * @author murata
 */
public class PopularAtractionQueue implements QueueType{
    private String name;
    private RandomDataGenerator rand = new RandomDataGenerator();
    private static double mu, sigma, mode;
    
    public PopularAtractionQueue(String name) {
        this.name = name;
    }
    
    @Override
    public Integer occureQueue(BusStop stop) {
        
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setSeed(long seed) {
        mu = 0;
        sigma = 1;
        rand.reSeed(seed);
    }
    
    public Integer getGaussRandom(){
        return rand.nextGaussian(mu, sigma);
    }
}
