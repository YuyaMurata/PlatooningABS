/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import park.AmusementPark;

/**
 *
 * @author murata
 */
public class BusStop {
    public final String name;
    public int x, y;
    
    public transient Object key;
    private transient Map<Object, List<People>> queue = new HashMap<>();;
    
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
    
    private Map<Object, Long> countStepQueue = new HashMap<>();
    public void queuePeople(People people){
        people.queueTime();
        
        Object rootNo = BusStops.getRootNO(people.getDeprture(), people.getDestination());
        if(queue.get(rootNo) == null)
            queue.put(rootNo, new ArrayList<>());
        
        queue.get(rootNo).add(people);
        
        Long count = 0L;
        if(countStepQueue.get(rootNo) != null)
            count = countStepQueue.get(rootNo) + 1;
        
        countStepQueue.put(rootNo, count);
    }
    
    public Long getStepQueue(Object rootNo){
        Long queuePeople = countStepQueue.get(rootNo);
        countStepQueue = new HashMap<>();
        if(queuePeople != null)
            return queuePeople;
        return 0L;
    }
    
    public List<People> getQueue(Object rootNo){
        if(queue.get(rootNo) == null) return new ArrayList();
        return queue.get(rootNo);
    }
    
    public int getQueueLength(Object rootNo){
        if(queue == null) return 0;
        else if(queue.get(rootNo) == null) return 0;
        return queue.get(rootNo).size();
    }
    
    public int getAllQueueLength(){
        if(queue == null) return 0;
        return queue.values().stream().mapToInt(q -> q.size()).sum();
    }
    
    public String toString(){
        if(queue == null) return name+":[x="+x+" ,y="+y+"]";
        return name+":[x="+x+" ,y="+y+"]-[QLine="+getAllQueueLength()+"]";
    }
    
    public String toString(String trace){
        return "("+name+","+x+" ,"+y+","+getAllQueueLength()+")";
    }
}
