/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import prop.ABSSettings;
import search.BusStopVertex;
import search.RootDijkstra;

/**
 *
 * @author kaeru
 */
public class BusStops implements ABSSettings{
    private static Map<String, BusStop> busStops;
    private static List<String>nameList;
    private static Map rootMap;
    private static int amount;
    
    public static void generate(){
        int n = json.param.numBusStops;
        
        busStops = new HashMap();
        nameList = new ArrayList<>();
        
        for(BusStop bs : json.param.busStops){
            busStops.put(bs.name, new BusStop(bs.name, bs.x, bs.y));    
            nameList.add(bs.name);
        }
        //Check BusStop
        busStops.values().stream().forEach(System.out::println);
        
        //root
        rootMap = json.param.root;
        
        //Create Candidate People Path
        candidatePath();
        
        //QueuePeople
        People.setRandom(json.param.seed);
        
        //Form Line
        setSeed(json.param.seed);
        amount = json.param.amountPeople;
    }
    
    public static Integer getNumBusStop(){
        return busStops.size();
    }
    
    public static BusStop getBusStop(String name){
        return busStops.get(name);
    }
    
    public static BusStop getBusStop(int i){
        return (BusStop)busStops.get(nameList.get(i));
    }
    
    public static List<BusStop> getBusStops(){
        return new ArrayList<BusStop>(busStops.values());
    }
    
    public static List<String> getRootPath(Object rootNo){
        return (List<String>) rootMap.get(rootNo);
    }
    
    public static Object getRoot(String busName){
        int i = Math.abs(busName.hashCode()) % rootMap.size();
        return rootMap.keySet().toArray()[i];
    }
    
    private static Random rand = new Random();
    public static void occureQueue(){
        for(BusStop bs : busStops.values()){
            int n = rand.nextInt(json.param.maxPassengers);
            
            if((amount - n) <= 0) n = amount;
            amount = amount - n;
            
            if(n != 0)
                for(int i=0; i < n; i++)
                    bs.queuePeople(new People(bs));
            
            System.out.println("Amount People:"+amount);
        }
    }
    
    /*public static int compareRoot(){
        int comp = 0;
        for(String busStopName : getRoot("root0"))
            comp = comp + getBusStop(busStopName).getQueue().size();
        
        for(String busStopName : getRoot("root1"))
            comp = comp - getBusStop(busStopName).getQueue().size();
        
        // comp > 0 root0 > root1
        return comp;
    }*/
    
    public static Object compareRoot(){
        SimpleEntry<Object, Integer> compMap = new SimpleEntry("", -1);
        for(Object rootNo : rootMap.keySet()){
            int comp = 0;
            for(String busStopName : getRootPath(rootNo))
                comp = comp + getBusStop(busStopName).getQueue().size();
            if(compMap.getValue() < comp) compMap = new SimpleEntry(rootNo, comp);
        }
        return compMap.getKey();
    }
    
    private static Map<String, List> candidateRoot = new HashMap();
    private static void candidatePath(){
        RootDijkstra path = new RootDijkstra();
        int[][] adjRoot = path.createAdjencyMatrix();
        
        for(int i=0; i < adjRoot.length; i++){
            List rootShort = new ArrayList();
            BusStopVertex[] busStopPath = path.dijkstra(adjRoot, i);
            for(int j=0; j < adjRoot.length; j++){
                if(i == j) continue;
                
                //Vertex -> BusStopName
                List rootBusStopNames = new ArrayList();
                for(BusStopVertex v : path.getShortestPath(busStopPath, j))
                    rootBusStopNames.add(getBusStop(v.to).name);
                
                rootShort.add(rootBusStopNames);      
            }
            
            candidateRoot.put(getBusStop(i).name, rootShort);
        }
        
        //Check
        for(Object key : candidateRoot.keySet())
            System.out.println(key+":"+candidateRoot.get(key));
    }
    
    public static List<String> getCandidatePath(String bsName){
        int n = candidateRoot.get(bsName).size();
        return (List<String>) candidateRoot.get(bsName).get(rand.nextInt(n));
    }
    
    public static void printLog(){
        if(json.param.loggingSW)
            busStops.values().stream().forEach(System.out::println);
    }
    
    private static void setSeed(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
    
    public static void main(String[] args) {
        json.absJSONReade();
        BusStops.generate();
        
    }
}