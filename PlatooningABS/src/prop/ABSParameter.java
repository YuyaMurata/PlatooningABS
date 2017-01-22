/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prop;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import obj.BusStop;

/**
 *
 * @author kaeru
 */
public class ABSParameter {
    //File
    public String fileName = "platooning_abs_log.txt";
    public String fieldName = "PID, BUS, Root, BusTime, QueueTime";
    public Boolean consoleSW = false;
    public Boolean loggingSW = true;
    
    //Visual
    public long renderTime = 200; //[ms]
    
    //Executable
    public long stepWaitTime = 400; //[ms]
    
    //Environment
    public int column = 9;
    public int row = 9;
    
    //BusAgent
    public int numBusAgents = 4;
    public int numHuman = 2;
    public int maxPassengers = 10;
    public enum type {human, robot};
    public double lostProb = -1;
    public Boolean commFailure = false;
    
    //Bus Root
    public Map<Object, List<String>> root = new HashMap(){
        {put("root0", Arrays.asList("BusStop_0", "BusStop_1", "BusStop_4"));}
        {put("root1", Arrays.asList("BusStop_2", "BusStop_3", "BusStop_4"));}
    };
    
    //BusStop
    public int numBusStops = 5;
    public int queuingByStep = 20;
    public List<BusStop> busStops = Arrays.asList(
            new BusStop("BusStop_0",0, 0),
            new BusStop("BusStop_1",0, 8),
            new BusStop("BusStop_2",8, 8),
            new BusStop("BusStop_3",8, 0),
            new BusStop("BusStop_4",4, 4)
        );
    
    //People -1 = random else set seed
    public long seed = -1;//Integer.MAX_VALUE;
    public int amountPeople = 100;
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Class: " + this.getClass().getCanonicalName() + "\n");
        sb.append("Settings:\n");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                sb.append(field.getName() + " = " + field.get(this) + "\n");
            } catch (IllegalAccessException e) {
                sb.append(field.getName() + " = " + "access denied\n");
            }
        }
        
        return sb.toString();
    }
}
