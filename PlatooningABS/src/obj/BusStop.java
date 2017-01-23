/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.util.ArrayList;
import java.util.List;
import park.AmusementPark;

/**
 *
 * @author murata
 */
public class BusStop {
    public final String name;
    public int x, y;
    
    public transient Object key;
    private transient List<People> queue = new ArrayList();
    
    public BusStop(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        
        setBusStop(x, y);
    }
    
    public void setBusStop(int x, int y){
        this.x = x;
        this.y = y;
        
        key = x+"-"+y;
        AmusementPark.getInstance().setBusStop(this);
    }
    
    public void queuePeople(People people){
        people.queueTime();
        queue.add(people);
    }
    
    public List<People> getQueue(){
        return queue;
    }
    
    public String toString(){
        if(queue == null) return name+":[x="+x+" ,y="+y+"]";
        return name+":[x="+x+" ,y="+y+"]-[QLine="+queue.size()+"]";
    }
}
