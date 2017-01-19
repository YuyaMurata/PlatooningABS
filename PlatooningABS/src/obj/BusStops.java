/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class BusStops {
    private static List<BusStop> busStops;
    private static Map rootMap;
    public static void generate(int n){
        busStops = new ArrayList();
        
        busStops = fixPattern();
        rootMap = createRoot(busStops);
        
        //Check BusStop
        busStops.stream().forEach(System.out::println);
    }
    
    public static BusStop getBusStop(int i){
        return (BusStop)busStops.get(i);
    }
    
    public static Map createRoot(List<BusStop> bs){
        Map rMap = new HashMap();
        
        //2 Root Left
        List root = new ArrayList();
        root.add(bs.get(0));
        root.add(bs.get(4));
        root.add(bs.get(3));
        rMap.put("root0", root);
        
        // Right
        root = new ArrayList();
        root.add(bs.get(1));
        root.add(bs.get(4));
        root.add(bs.get(2));
        rMap.put("root1", root);
        
        return rMap;
    }
    
    private static List<BusStop> fixPattern(){
        List<BusStop> bs = new ArrayList();
        
        bs.add(new BusStop(0));
        bs.get(0).setBusStop(0, 0);
        bs.add(new BusStop(1));
        bs.get(1).setBusStop(8, 0);
        bs.add(new BusStop(2));
        bs.get(2).setBusStop(8, 8);
        bs.add(new BusStop(3));
        bs.get(3).setBusStop(0, 8);
        bs.add(new BusStop(4));
        bs.get(4).setBusStop(4, 4);
        
        return bs;
    }
    
    public static Integer getNumBusStop(){
        return busStops.size();
    }
    
    public static List<BusStop> getList(){
        return busStops;
    }
    
    public static List<BusStop> getRoot(String rootNo){
        return (List<BusStop>) rootMap.get(rootNo);
    }
    
    public static void occureQueue(){
        for(BusStop bs : busStops)
            for(int i=0; i < 20; i++)
                bs.queuePeople(new People(bs));
    }
    
    public static Boolean transitCheck(BusStop dept, BusStop dest){
        Boolean c1, c2, c3, c4;
        c1 = getRoot("root0").contains(dept);
        c2 = getRoot("root0").contains(dest);
        c3 = getRoot("root1").contains(dept);
        c4 = getRoot("root1").contains(dest);
        
        Boolean c12 = c1 & c2;
        Boolean c34 = c3 & c4;
        
        return c12 | c34;
    }
    
    public static int compareRoot(){
        int comp = 0;
        for(BusStop busStop : getRoot("root0"))
            comp = comp + busStop.getQueue().size();
        
        for(BusStop busStop : getRoot("root1"))
            comp = comp - busStop.getQueue().size();
        
        // comp > 0 root0 > root1
        return comp;
    }
    
    public static BusStop getHub(){
        return busStops.get(4);
    }
}