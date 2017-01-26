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
import obj.BusStop;
import obj.BusStops;
import prop.ABSSettings;

/**
 *
 * @author murata
 */
public class BusAgents implements ABSSettings{
    private static List<BusAgent> busAgents;
    private static int man;
    private static Map<Object, List<BusAgent>>busRoot;
    private static int leaderNum;
    public static Boolean changeLineSW;
    
    public static void generate(){
        leaderNum = 0;
        
        if(busAgents != null) {
            initialize();
            return ;
        }
        
        int n = json.param.numBusAgents;
        man = json.param.numHuman;
        changeLineSW = json.param.changeLineSW;
        
        busAgents = new ArrayList();
        BusAgent.maxPassengers = json.param.maxPassengers;
        BusAgent.setSeed(json.param.seed);
        BusAgent.lostProb = json.param.lostProb;
        busRoot = new HashMap();
        
        //Human Bus Create
        for(int i=0; i < man; i++){
            BusAgent bus = new BusAgent(i, "human"); 
            busAgents.add(bus);
        }
        
        //Deploy Bus at BusStop
        deployBusAgents();
        
        //Robot Bus Create
        if(n-man > 0)
            for(int i=man; i < n; i++){
                busAgents.add(new BusAgent(i, "robot"));
            }
        
        //Check BusStop
        busAgents.stream().forEach(System.out::println);
    }
    
    private static void initialize(){
        //System.out.println("Initalize BusAgents !");
        
        //init Human Bus
        deployBusAgents();
        
        //init Robot Bus
        for(BusAgent bus : busAgents)
            bus.init();
    }
    
    private static void deployBusAgents(){
        Map<Object, Integer> rootBusStopDeploy = new HashMap();
        for(BusAgent bus : busAgents){
            if(bus.type.equals("robot")) continue;
            
            if(rootBusStopDeploy.get(bus.root) == null)
                rootBusStopDeploy.put(bus.root, -1);
            List<String> rootBusStop = BusStops.getRootPath(bus.root);
            
            //BusStopのみに順に配置
            int deploy = (rootBusStopDeploy.get(bus.root) + 1) % rootBusStop.size();
            BusStop busStop = BusStops.getBusStop(rootBusStop.get(deploy));
            
            //Set Bus Position and Next BusStop
            bus.nextBusStop = deploy;
            bus.busPosition(busStop.x, busStop.y);
            
            rootBusStopDeploy.put(bus.root, deploy);
        }
    }
    
    public static List<BusAgent> getList(){
        return busAgents;
    }
    
    public static BusAgent getLeader(BusAgent robot){
        //Test
        return busAgents.get((leaderNum++) % man);
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
    
    public static String traceLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(BusAgent bus : busAgents){
            sb.append(bus.toString("trace"));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}
