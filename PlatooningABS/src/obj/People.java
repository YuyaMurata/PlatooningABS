/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import exec.StepExecutor;
import fileout.OutputInstance;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author 悠也
 */
public class People {
    private enum param{
        PID, BUS, ROOT, BTIME, QTIME
    }
    
    private static long pid = 0L;
    private Long waitTime, getTime;
    public BusStop destination, departure, transit;
    private Map log = new LinkedHashMap();

    public People(BusStop busStop){
        pid++;
        this.departure = busStop;
        this.destination = getDestination(busStop);
        this.transit = transitCheck(departure, destination);
        
        //Log
        log.put(param.PID, "people-"+pid);
        log.put(param.BUS, "bus");
        
        String str = "";
        if(transit != null){
            str = departure.name+"->"+transit.name+"->"+destination.name;
            BusStop temp = destination;
            destination = transit;
            transit = temp;
        }else{
            str = departure.name+"->"+"null"+"->"+destination.name;
        }
        log.put(param.ROOT, str);
    }
    
    public void queueTime(){
        this.waitTime = StepExecutor.step;
    }
    
    public void getOnTime(String busName){
        waitTime = StepExecutor.step - waitTime;
        getTime = StepExecutor.step;
        log.put(param.BUS, log.get(param.BUS)+"-"+busName);
    }
    
    public Boolean getOffCheck(BusStop location){
        if(destination.equals(location)){
            getTime = StepExecutor.step - getTime;
            if(log.get(param.BTIME) != null){
                getTime = getTime + (Long)log.get(param.BTIME);
                waitTime = waitTime + (Long)log.get(param.QTIME);
            }
            log.put(param.BTIME, getTime);
            log.put(param.QTIME, waitTime);
            
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
