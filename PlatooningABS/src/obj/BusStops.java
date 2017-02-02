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
import java.util.Random;
import prop.ABSSettings;
import root.RootManager;

/**
 *
 * @author kaeru
 */
public class BusStops implements ABSSettings{
    private static Map<String, BusStop> busStops;
    private static List<String>nameList;
    private static int amount;
    
    public static void generate(){
        //Initalize People
        People.pid = 0L;
        People.setRandom(json.param.seed);
        amount = json.param.amountPeople;
        
        if(busStops != null) {
            initialize();
            return ;
        }
        
        int n = json.param.numBusStops;
        
        busStops = new HashMap();
        nameList = new ArrayList<>();
        
        for(BusStop bs : json.param.busStops){
            busStops.put(bs.name, new BusStop(bs.name, bs.x, bs.y));    
            nameList.add(bs.name);
        }
        
        //Check BusStop
        busStops.values().stream().forEach(System.out::println);
        
        //Create Candidate People Path
        RootManager.getInstance().createRoot(json.param.root);
        
        //Form Line
        setSeed(json.param.seed);
        RootManager.setSeed(json.param.seed);
    }
    
    private static void initialize(){
        //System.out.println("Initalize BusStops !");
        
        //init BusStop
        for(BusStop bs : json.param.busStops){
            BusStop busStop = getBusStop(bs.name);
            busStop.setBusStop(bs.x, bs.y);
        }
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
        return RootManager.getInstance().getRootPath(rootNo);
    }
    
    public static Object getRootNO(String busName){
        return RootManager.getInstance().getRoot(busName);
    }
    
    public static Object getRootNO(String deptBusStop, String destBusStop){
        return RootManager.getInstance().getRoot(deptBusStop, destBusStop);
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
        }
    }
    
    public static Object compareRoot(){
        return RootManager.getInstance().compareRoot();
    }
    
    public static List<String> getCandidatePath(String bsName){
        return RootManager.getInstance().getCandidatePath(bsName);
    }
    
    public static Boolean finish(){
        return busStops.values().stream()
                .allMatch(stop -> (stop.getAllQueueLength() == 0));
    }
    
    public static void printLog(){
        if(json.param.loggingSW)
            busStops.values().stream().forEach(System.out::println);
    }
    
    public static String traceLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(String bsName : busStops.keySet()){
            sb.append(getBusStop(bsName).toString("trace"));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
    
    private static void setSeed(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
    
    public static void main(String[] args) {
        json.absJSONRead();
        BusStops.generate();
    }
}