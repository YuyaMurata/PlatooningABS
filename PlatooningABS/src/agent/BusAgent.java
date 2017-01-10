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
        
        busPosition(0, 0);
    }
    
    public void move(){
        if(type.equals("robot")) planning();
        else patrol();
    }
    
    private void planning(){
        
    }
    
    private void patrol(){
        if(agentKey.get(1) == BusStops.getBusStop(goal).key)
            goal = (goal + 1) % BusStops.getNumBusStop();
        
        BusStop busStop = BusStops.getBusStop(goal);
        
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
    }
    
    public void busPosition(int x, int y){
        //Before Position
        this.bx = this.x;
        this.by = this.y;
        agentKey.add(0, bx+"-"+by);
        
        //After Move
        this.x = x;
        this.y = y;
        agentKey.add(1, x+"-"+y);
        
        AmusementPark.getInstance().setBusAgent(this);
    }
}
