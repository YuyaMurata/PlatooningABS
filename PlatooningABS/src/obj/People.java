/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import exec.StepExecutor;
import fileout.OutputInstance;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import root.RootManager;

/**
 *
 * @author murata
 */
public class People {
    private enum param{
        PID, BUS, ROOT, BTIME, QTIME
    }
    
    public static long pid;
    private Long waitTime, getTime;
    public LinkedList path = new LinkedList<>();
    private Map log = new LinkedHashMap();
    private static Random rand = new Random();
    private Object dept;
    public People(BusStop deptBusStop){
        //People ID
        pid++;
        
        //Get Path
        path.addAll(RootManager.getInstance().getCandidatePath(deptBusStop.name));
        
        //Log
        log.put(param.PID, "people-"+pid);
        log.put(param.BUS, "bus");
        
        //Depart
        dept = path.poll();
        log.put(param.ROOT, dept);
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
        Object destination = path.peek();
        if(destination.equals(location.name)){
            getTime = StepExecutor.step - getTime;
            if(log.get(param.BTIME) != null){
                getTime = getTime + (Long)log.get(param.BTIME);
                waitTime = waitTime + (Long)log.get(param.QTIME);
            }
            log.put(param.BTIME, getTime);
            log.put(param.QTIME, waitTime);
            log.put(param.ROOT, log.get(param.ROOT)+"->"+destination);
            
            //Check
            dept = path.poll();
            if(!path.isEmpty()) location.queuePeople(this);
            else printLog();
            
            return true;
        }
        return false;
    }
    
    public String getDestination(){
        return (String) path.peek();
    }
    
    public String getDeprture(){
        return (String) dept;
    }
    
    public static void setRandom(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
    
    public void printLog(){
        OutputInstance.dataPeopleLog.write(log.values().toString());
    }
}
