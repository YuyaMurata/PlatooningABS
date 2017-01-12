/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import fileout.OutputInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 悠也
 */
public class People {
    private Long waitTime, getTime;
    public BusStop destination, departure, transit;
    private List log = new ArrayList();
    
    public People(BusStop busStop){
        this.departure = busStop;
        this.destination = getDestination(busStop);
        
        this.transit = transitCheck(departure, destination);
        if(transit != null){
            BusStop temp = destination;
            destination = transit;
            transit = destination;
        }
        
        log.add(departure);
        log.add(destination);
        log.add(transit);
    }
    
    public void queueTime(){
        this.waitTime = System.currentTimeMillis();
    }
    
    public void getOnTime(){
        waitTime = System.currentTimeMillis() - waitTime;
        getTime = System.currentTimeMillis();
    }
    
    public Boolean getOffCheck(BusStop location){
        if(destination.equals(location)){
            getTime = System.currentTimeMillis() - getTime;
            log.add(getTime);
            log.add(waitTime);
            
            //Check
            destination = null;
            transit(location);
            
            //Test log
            String str = printLog();
            if(!str.equals(""))
                OutputInstance.data.write(str);
            
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
    
    public String printLog(){
        if(destination == null)
            return log.toString();
        return "";
    }
}
