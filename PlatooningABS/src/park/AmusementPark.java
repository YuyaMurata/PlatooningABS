/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package park;

import agent.AbstractBusAgent;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import obj.BusStop;

/**
 * 遊園地クラス
 * Agent <-> Object ABS <-> Visualizer の橋渡しを行う
 * @author murata
 */
public class AmusementPark { 
    private static AmusementPark park = new AmusementPark();
    
    private Map objectMap = new ConcurrentHashMap<>(); //オブジェクトの管理
    private Map agentMap = new ConcurrentHashMap<>(); //Agentの管理
    
    
    //Singleton
    public static AmusementPark getInstance(){
        return park;
    }
    
    //初期化
    public void init() {
        if(!objectMap.isEmpty()) objectMap = new ConcurrentHashMap<>();
        if(!agentMap.isEmpty()) agentMap = new ConcurrentHashMap<>();
    }
    
    //バス停(オブジェクト)の登録
    public void setBusStop(BusStop stop){
        objectMap.put(stop.key, stop);
    }
    
    //バス(Agent)の登録
    public void setBusAgent(AbstractBusAgent bus){
        Object removeKey = bus.key[0]; //前情報
        Object putKey = bus.key[1]; //現情報
        
        List agentList;
        
        //前情報を管理変数から削除
        if(agentMap.get(removeKey) != null){
            agentList = (List) agentMap.get(removeKey);
            agentList.remove(bus);
            
            if(agentList.isEmpty())
                agentMap.remove(removeKey);
        }
        
        //現情報を管理変数に登録
        if(agentMap.get(putKey)== null)
            agentMap.put(putKey, new CopyOnWriteArrayList());
        agentList = (List) agentMap.get(putKey);
        agentList.add(bus);
        agentMap.put(putKey, agentList);
    }
    
    //到着したバス停の取得
    public BusStop arrivalBusStop(Object key){
        return (BusStop) objectMap.get(key);
    }
    
    //可視化用にオブジェクト情報を取得
    public Collection<BusStop> getBusStops(){
        return objectMap.values();
    }
    
    //可視化用にAgent情報を取得
    public Collection<List<AbstractBusAgent>> getBusAgents(){
        return agentMap.values();
    }
}