/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.ArrayList;
import java.util.List;
import obj.BusStop;
import obj.BusStops;
import park.AmusementPark;

/**
 *
 * @author kaeru
 */
public class BusAgent {
    public String name, type;
    public List agentKey = new ArrayList();;
    public int x, y, bx, by;
    private int goal;
    
    public BusAgent(int index, String type){
        this.name = "Bus_"+index;
        this.type = type;
        
        this.x = 0;
        this.y = 0;
        this.goal = 0;
        
        //Init Agentkey
        String key = x+"-"+y;
        agentKey.add(key);
        agentKey.add(key);
        
        busPosition(0, 0);
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
        if(agentKey.get(1).equals(BusStops.getBusStop(goal).key))
            goal = (goal + 1) % BusStops.getNumBusStop();
        
        BusStop busStop = BusStops.getBusStop(goal);
        
        //Test
        System.out.println("key ag="+agentKey+" g="+BusStops.getBusStop(goal).key);
        System.out.println("Target : "+busStop+" i="+goal);
        
        //Move
        deltaMove(x, busStop.x, y, busStop.y);
    }
    
    private void deltaMove(int xstart, int xgoal, int ystart, int ygoal){
        //X Move
        if(xstart < xgoal) x++;
        else if(xstart > xgoal) x--;
        
        //Y Move
        if(ystart < ygoal) y++;
        else if(ystart > ygoal) y--; 
        
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
    
    public String toString(){
        return name+"<"+type+">:[x="+x+" ,y="+y+"]";
    }
}
