/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import prop.ABSSettings;

/**
 *
 * @author kaeru
 */
public class BusAgents implements ABSSettings{
    private static List<BusAgent> busAgents;
    private static int man;
    private static Map<Object, List<BusAgent>>busRoot;
    
    public static void generate(){
        int n = json.param.numBusAgents;
        man = json.param.numHuman;
        
        busAgents = new ArrayList();
        BusAgent.maxPassengers = json.param.maxPassengers;
        BusAgent.setSeed(json.param.seed);
        BusAgent.lostProb = json.param.lostProb;
        busRoot = new HashMap();
        
        for(int i=0; i < man; i++){
            busAgents.add(new BusAgent(i, "human"));
            busAgents.get(i).busPosition(i*8, i*8);
        }
        
        if(n-man > 0)
            for(int i=man; i < n; i++){
                busAgents.add(new BusAgent(i, "robot"));
            }
        
        //Check BusStop
        busAgents.stream().forEach(System.out::println);
    }
    
    public static BusAgent getBusAgent(int i){
        return (BusAgent)busAgents.get(i);
    }
    
    public static Integer getNumBusStop(){
        return busAgents.size();
    }
    
    public static List<BusAgent> getList(){
        return busAgents;
    }
    
    private static int leaderNum = 0;
    public static BusAgent getLeader(BusAgent robot){
        //Test
        return busAgents.get((leaderNum++) % man);
    }
    
    public static BusAgent getLeader(int rootNo){
        //Test
        if(rootNo == 0) return busAgents.get((0));
        else return busAgents.get((1));
    }
    
    public static Boolean getCommState(){
        return json.param.commFailure;
    }
    
    public static void setRootBus(BusAgent bus){
        if(busRoot.get(bus.root) == null)
            busRoot.put(bus.root, new ArrayList<>());
        
        busRoot.get(bus.root).add(bus);
    }
    
    public static BusAgent getRootBus(Object rootNo, String name){
        int i = Math.abs(name.hashCode()) % busRoot.get(rootNo).size();
        return busRoot.get(rootNo).get(i);
    }
    
    public static void printLog(){
        if(json.param.loggingSW)
            busAgents.stream().forEach(System.out::println);
    }
}
