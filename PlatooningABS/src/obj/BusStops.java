/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaeru
 */
public class BusStops {
    private static List<BusStop> busStops;
    public static void generate(int n){
        busStops = new ArrayList();
        
        for(int i=0; i < n; i++){
            busStops.add(new BusStop(i));
            busStops.get(i).setBusStop(i*5, i*5);
        }
        
        //Check BusStop
        busStops.stream().forEach(System.out::println);
    }
    
    public static BusStop getBusStop(int i){
        return (BusStop)busStops.get(i);
    }
    
    public static Integer getNumBusStop(){
        return busStops.size();
    }
    
    public static List<BusStop> getList(){
        return busStops;
    }
}
