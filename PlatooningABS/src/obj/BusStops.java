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
    private static List busStops;
    public static void generate(int n){
        busStops = new ArrayList();
        
        for(int i=0; i < n; i++){
            busStops.add(new BusStop(i));
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
}
