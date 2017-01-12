/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import obj.BusStop;
import obj.BusStops;
import obj.People;
import park.AmusementPark;

/**
 *
 * @author kaeru
 */
public class BusAgent {
    public String name, type;
    public List agentKey;
    public int x, y, bx, by;
    private int goal;
    private int maxPassengers = 10;
    private List<People> passengers = new ArrayList();
    private List<BusStop> root;
    
    public BusAgent(int index, String type){
        this.name = "Bus_"+index;
        this.type = type;
        
        this.x = 0;
        this.y = 0;
        this.goal = 0;
        
        //Init Agentkey
        String key = x+"-"+y;
        agentKey = new ArrayList();
        agentKey.add(key);
        agentKey.add(key);
        
        busPosition(0, 0);
        root = BusStops.getRoot("root"+name.split("_")[1]);
    }
    
    public void move(){
        if(type.equals("robot")) planning();
        else patrol();
        
        //Test
        System.out.println(toString());
    }
    
    private void planning(){      
    }
    
    private void patrol(){
        if(agentKey.get(1).equals(root.get(goal).key)){
            getOn(root.get(goal));
            getOff(root.get(goal));
            goal = (goal + 1) % root.size();
        }
        
        BusStop busStop = root.get(goal);
        
        //Test
        //System.out.println("key ag="+agentKey+" g="+BusStops.getBusStop(goal).key);
        //System.out.println("Target : "+busStop+" i="+goal);
        
        //Move
        deltaMove(x, busStop.x, y, busStop.y);
    }
    
    public Boolean getOn(BusStop busStop){
        Iterator<People> it = busStop.getQueue().iterator();
        while(it.hasNext()){
            if(maxPassengers <= passengers.size()) return false;
            
            People people = it.next();
            if(root.contains(people.destination)){
                passengers.add(people);
                people.getOnTime();
                it.remove();
            }
        }
        return true;
    }
    
    public void getOff(BusStop busStop){
        Iterator it = passengers.iterator();
        while(it.hasNext()){
            People people = (People) it.next();
            if(people.getOffCheck(busStop))
                it.remove();
        }
    }
    
    private void deltaMove(int xstart, int xgoal, int ystart, int ygoal){
        int xd = xgoal - xstart;
        int yd = ygoal - ystart;
        
        //if(Math.abs(xd) > Math.abs(yd)){
        //X Move
            if(xd > 0) x++;
            else if(xd < 0) x--;
        //}else{
        //Y Move
            if(yd > 0) y++;
            else if(yd < 0) y--; 
        //}
        busPosition(x, y);
    }
    
    public void busPosition(int x, int y){
        //Before Position
        this.bx = this.x;
        this.by = this.y;
        String beforeKey = bx+"-"+by;
        agentKey.set(0, beforeKey);
        
        //After Move
        this.x = x;
        this.y = y;
        String key = x+"-"+y;
        agentKey.set(1, key);
        
        AmusementPark.getInstance().setBusAgent(this);
    }
    
    public Integer numGetOn(){
        return passengers.size();
    }
    
    public String toString(){
        return name+"<"+type+">:[x="+x+" ,y="+y+"]-["+passengers.size()+"]";
    }
}
