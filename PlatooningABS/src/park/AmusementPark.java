/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package park;

import agent.BusAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import obj.BusStop;

/**
 *
 * @author kaeru
 */
public class AmusementPark {
    private int c, r;
    
    private static AmusementPark park = new AmusementPark();
    public static AmusementPark getInstance(){
        return park;
    }
    
    public void init(int col, int row) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.c = col;
        this.r = row;
    }
    
    private Map map = new HashMap<>();
    private Map agentMap = new HashMap<>();
    
    public void setBusStop(BusStop stop){
        map.put(stop.key, stop.name);
    }
    
    public void setBusAgent(BusAgent bus){
        Object removeKey = bus.agentKey.get(0);
        Object putKey = bus.agentKey.get(1);
        
        //Register Map
        if(agentMap.get(putKey)== null)
            agentMap.put(putKey, new ArrayList());
        List agentList = (List) agentMap.get(putKey);
        agentList.add(bus.name);
        agentMap.put(putKey, agentList);
        
        //Remove Map
        if(agentMap.get(removeKey) != null){
            agentList = (List) agentMap.get(removeKey);
            agentList.remove(bus.name);
        }
    }
}