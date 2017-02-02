/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package park;

import agent.BusAgent;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import obj.BusStop;

/**
 *
 * @author kaeru
 */
public class AmusementPark { 
    private static AmusementPark park = new AmusementPark();
    public static AmusementPark getInstance(){
        return park;
    }
    
    private Map map = new ConcurrentHashMap<>();
    private Map agentMap = new ConcurrentHashMap<>();;
    public void init() {
        if(!map.isEmpty()) map = new ConcurrentHashMap<>();
        if(!agentMap.isEmpty()) agentMap = new ConcurrentHashMap<>();
    }
    
    public void setBusStop(BusStop stop){
        map.put(stop.key, stop);
    }
    
    public void setBusAgent(BusAgent bus){
        Object removeKey = bus.agentKey.get(0);
        Object putKey = bus.agentKey.get(1);
        
        List agentList;
        
        //Remove Map
        if(agentMap.get(removeKey) != null){
            agentList = (List) agentMap.get(removeKey);
            agentList.remove(bus);
            
            if(agentList.isEmpty())
                agentMap.remove(removeKey);
        }
        
        //Register Map
        if(agentMap.get(putKey)== null)
            agentMap.put(putKey, new CopyOnWriteArrayList());
        agentList = (List) agentMap.get(putKey);
        agentList.add(bus);
        agentMap.put(putKey, agentList);
    }
    
    public BusStop arrivalBusStop(Object key){
        return (BusStop) map.get(key);
    }
    
    public Collection<BusStop> getBusStops(){
        return map.values();
    }
    
    public Collection<List<BusAgent>> getBusAgents(){
        return agentMap.values();
    }
}