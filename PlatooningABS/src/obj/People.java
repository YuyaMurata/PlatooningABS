/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import exec.StepExecutor;
import fileout.OutputInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 悠也
 */
public class People {
    private static long pid = 0L;
    private Long waitTime, getTime;
    public BusStop destination, departure, transit;
    private List log = new ArrayList();
    
    public People(BusStop busStop){
        pid++;
        this.departure = busStop;
        this.destination = getDestination(busStop);
        this.transit = transitCheck(departure, destination);
        
        //Log
        log.add("people-"+pid);
        
        if(transit != null){
            String str = departure.name+"->"+transit.name+"->"+destination.name;
            log.add(str);
        
            BusStop temp = destination;
            destination = transit;
            transit = temp;
        }else{
            String str = departure.name+"->"+"null"+"->"+destination.name;
            log.add(str);    
        }
    }
    
    public void queueTime(){
        this.waitTime = StepExecutor.step;
    }
    
    public void getOnTime(){
        waitTime = StepExecutor.step - waitTime;
        getTime = StepExecutor.step;
    }
    
    public Boolean getOffCheck(BusStop location){
        if(destination.equals(location)){
            getTime = StepExecutor.step - getTime;
            log.add(getTime);
            log.add(waitTime);
            
            //Check
            destination = null;
            transit(location);
            
            //Test log
            printLog();
            
            return true;
        }
        return false;
    }
    
    private BusStop transitCheck(BusStop dept, BusStop dest){
        if(BusStops.transitCheck(dept, dest))
            return null;
        else return BusStops.getHub();
    }
    
    private void transit(BusStop location){
        if(transit == null) return;
        destination = transit;
        transit = null;
        
        location.queuePeople(this);
    }
    
    private Random rand = new Random();
    private BusStop getDestination(BusStop busStop){
        List<BusStop> candidate = new ArrayList();
        candidate.addAll(BusStops.getList());
        candidate.remove(busStop);
        return candidate.get(rand.nextInt(candidate.size()));
    }
    
    public void printLog(){
        if(destination == null)
            OutputInstance.data.write(log.toString());
    }
}
