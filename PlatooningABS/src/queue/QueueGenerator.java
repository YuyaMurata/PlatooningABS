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
public class QueueGenerator {
    private QueueType type;
    
    public QueueGenerator(QueueType type){
        this.type = type;
    }
    
    public void generate(BusStop stop){
        type.occureQueue(stop);
    }
    
    public String getName(){
        return type.getName();
    }
}
