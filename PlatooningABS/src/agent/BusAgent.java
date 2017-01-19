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
    public static int maxPassengers = 10;
    public String name, type;
    public List agentKey;
    public int x, y;
    private int nextBusStop;
    private List<People> passengers = new ArrayList();
    public List<BusStop> root;
    private BusAgent leader;
    private AmusementPark park;
    
    public BusAgent(int index, String type){
        this.name = "Bus_"+index;
        this.type = type;
        
        this.x = 0;
        this.y = 0;
        this.nextBusStop = 0;
        
        //Init Agentkey
        Object key = x+"-"+y;
        agentKey = new ArrayList();
        agentKey.add(key);
        agentKey.add(key);
        
        park = AmusementPark.getInstance();
        
        if(type.equals("robot")){
            leader = BusAgents.getLeader(this);
            x = leader.x;
            y = leader.y;
            root = leader.root;
        }else
            root = BusStops.getRoot("root"+name.split("_")[1]);
        
        busPosition(x, y);
    }
    
    public void changeLeader(){
        if(BusStops.compareRoot() > 0)
            this.leader = BusAgents.getLeader(0);
        else
            this.leader = BusAgents.getLeader(1);
        
        root = leader.root;
    }
    
    public void move(){
        nextBusStop = busStopCheck();
        
        if(type.equals("robot")) planning();
        else patrol(nextBusStop);
    }
    
    private void planning(){
        if(numGetOn() == 0) changeLeader();
        deltaMove(x, leader.x, y, leader.y);
    }
    
    private void patrol(int next){
        BusStop busStop = root.get(next);
        
        //Test
        //System.out.println("key ag="+agentKey+" g="+BusStops.getBusStop(next).key);
        //System.out.println("Target : "+busStop+" i="+next);
        
        //Move
        deltaMove(x, busStop.x, y, busStop.y);
    }
    
    private int busStopCheck(){
        BusStop busStop = park.arrivalBusStop(agentKey.get(1));
        if(busStop != null){
            getOn(busStop);
            getOff(busStop);
            
            //logPrint(" <Check>"+busStop.name +" "+ root.get(nextBusStop).name);
            if(busStop == root.get(nextBusStop))
                nextBusStop = (nextBusStop + 1) % root.size();
        }
        
        return nextBusStop;
    }
    
    private Boolean getOn(BusStop busStop){
        Iterator<People> it = busStop.getQueue().iterator();
        while(it.hasNext()){
            if(maxPassengers <= passengers.size()) return false;
            
            People people = it.next();
            if(root.contains(people.destination)){
                passengers.add(people);
                people.getOnTime(name);
                it.remove();
            }
        }
        return true;
    }
    
    private void getOff(BusStop busStop){
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
        
        int bx = this.x;
        int by = this.y;
        
        //if(Math.abs(xd) > Math.abs(yd)){
        //X Move
            if(xd > 0) bx++;
            else if(xd < 0) bx--;
        //}else{
        //Y Move
            if(yd > 0) by++;
            else if(yd < 0) by--; 
        //}
        busPosition(bx, by);
    }
    
    public void busPosition(int x, int y){
        agentKey.clear();
        
        //Before Position
        agentKey.add(this.x+"-"+this.y);
        
        //After Move
        this.x = x;
        this.y = y;
        agentKey.add(x+"-"+y);
        
        //logPrint("key Check<"+agentKey);
        //Location
        park.setBusAgent(this);
    }
    
    public Integer numGetOn(){
        return passengers.size();
    }
    
    public String toString(){
        return name+"<"+type+">:[x="+x+" ,y="+y+"]-["+passengers.size()+"]";
    }
    
    private void logPrint(String str){
        System.out.println(name+"<"+type+">-log : "+str);
    }
}