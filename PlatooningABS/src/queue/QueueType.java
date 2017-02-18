/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import obj.BusStop;

/**
 *
 * @author murata
 */
public interface QueueType {
    public abstract Integer occureQueue(BusStop stop);
    
    public abstract String getName();
    
    public abstract void setSeed(long seed);
    
    @Override
    public String toString();
}
