/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import obj.BusStop;
import obj.BusStops;
import obj.People;
import park.AmusementPark;

/**
 *
 * @author murata
 */
public class BusAgent {
    public static int maxPassengers;
    public static double lostProb;
    
    public final String name, type;
    public List agentKey;
    public int x, y;
    public int nextBusStop;
    private List<People> passengers = new ArrayList();
    public Object root;
    public Integer rootNO;
    private BusAgent leader;
    private AmusementPark park;
    public Boolean state = true;
    
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
        
        //Get Root
        root = BusStops.getRoot(name);
        rootNO = Integer.valueOf(((String)root).split("root")[1]);
        leader = null;
        
        if(type.equals("robot")){
            leader = BusAgents.getLeader(this);
            x = leader.x;
            y = leader.y;
            root = leader.root;
        }else
            BusAgents.setRootBus(this);
        
        busPosition(x, y);
    }
    
    public void init(){
        if(type.equals("robot")){
            leader = BusAgents.getLeader(this);
            x = leader.x;
            y = leader.y;
            root = leader.root;
            
            busPosition(x, y);
        }
    }
    
    public void changeLeader(){
        Object change = BusStops.compareRoot();
        if(root.equals(change)) return;
        
        this.leader = BusAgents.getRootBus(change, name);
        root = leader.root;
        rootNO = Integer.valueOf(((String)root).split("root")[1]);
    }
    
    public void move(){
        if(type.equals("robot")) planning();
        else patrol(nextBusStop);
        
        //getting on and off Process
        nextBusStop = busStopCheck();
    }
    
    private void planning(){
        if((numPassenger() == 0) && (state) && BusAgents.changeLineSW)
            changeLeader();
        
        if((rand.nextDouble() < lostProb) || (BusAgents.getCommState())) lost();
        
        deltaMove(x, leader.x, y, leader.y);
    }
    
    private void patrol(int next){
        List<String> rootPath = BusStops.getRootPath(root);
        BusStop busStop = BusStops.getBusStop(rootPath.get(next));
        
        //Test
        //System.out.println("key ag="+agentKey+" g="+BusStops.getBusStop(next).key);
        //System.out.println("Target : "+busStop+" i="+next);
        
        //Move
        deltaMove(x, busStop.x, y, busStop.y);
    }
    
    private int busStopCheck(){
        BusStop busStop = park.arrivalBusStop(agentKey.get(1));
        if(busStop != null){
            getOff(busStop);
            getOn(busStop);
            
            //Next BusStop in Root
            Object nextBusStopName = BusStops.getRootPath(root).get(nextBusStop);
            if(busStop.name.equals(nextBusStopName))
                nextBusStop = (nextBusStop + 1) % BusStops.getRootPath(root).size();
        }
        
        return nextBusStop;
    }
    
    private Integer getOn(BusStop busStop){
        int num = 0;
        Iterator<People> it = busStop.getQueue().iterator();
        while(it.hasNext()){
            if(maxPassengers <= passengers.size()) return num;
            
            People people = it.next();
            if(BusStops.getRootPath(root).contains(people.getDestination())){
                passengers.add(people);
                people.getOnTime(name);
                it.remove();
                num++;
            }
        }
        return num;
    }
    
    private Integer getOff(BusStop busStop){
        int num = 0;
        Iterator it = passengers.iterator();
        while(it.hasNext()){
            People people = (People) it.next();
            if(people.getOffCheck(busStop)){
                it.remove();
                num++;
            }
        }
        return num;
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
    
    private void lost(){
        logPrint("Lost Leader");
        state = false;
        
        leader = this;
        root = "";
    }
    
    public Integer numPassenger(){
        return passengers.size();
    }
    
    private static Random rand = new Random();
    public static void setSeed(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
    
    public String toString(){
        return name+"<"+type+">:[x="+x+" ,y="+y+"]-["+passengers.size()+"]";
    }
    
    public String toString(String trace){
        String leaderName = "--";
        if(type.equals("robot")) leaderName = leader.name;
        return "("+name+","+type+","+x+","+y+","+passengers.size()+","+root+","+leaderName+")";
    }
    
    private void logPrint(String str){
        System.out.println(name+"<"+type+">-log : "+str);
    }
}