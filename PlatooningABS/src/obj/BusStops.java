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
 * バス停を一括管理するクラス
 * @author kaeru
 */
public class BusStops implements ABSSettings{
    private static Map<String, BusStop> busStops; //全バス停
    private static List<String>nameList; //全バス停の名前
    private static int amount; //実験で発生する人の数
    private static Random rand = new Random(); //人発生用の乱数
    
    //バス停の作成
    public static void generate(){
        //Initalize People
        People.pid = 0L;
        People.setRandom(json.param.seed);
        amount = json.param.amountPeople;
        
        //繰り返し実験時は初期化を簡略化
        if(busStops != null) {
            initialize();
            return ;
        }
        
        //バス停の数
        int n = json.param.numBusStops;
        
        busStops = new HashMap();
        nameList = new ArrayList<>();
        
        //バス停の作成
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
    
    //簡易初期化
    private static void initialize(){
        //System.out.println("Initalize BusStops !");
        
        //init BusStop
        for(BusStop bs : json.param.busStops){
            BusStop busStop = getBusStop(bs.name);
            busStop.setBusStop(bs.x, bs.y);
        }
    }
    
    //バス停の数を取得
    public static Integer getNumBusStop(){
        return busStops.size();
    }
    
    //バス停を取得
    public static BusStop getBusStop(String name){
        return busStops.get(name);
    }
    
    //インデックスでバス停を取得
    public static BusStop getBusStop(int i){
        return (BusStop)busStops.get(nameList.get(i));
    }
    
    //全バス停を取得
    public static List<BusStop> getBusStops(){
        return new ArrayList<BusStop>(busStops.values());
    }
    
    /*
    public static List<String> getRootPath(Object rootNo){
        return RootManager.getInstance().getRootPath(rootNo);
    }
    
    public static Object getRootNO(String busName){
        return RootManager.getInstance().getRoot(busName);
    }
    */    

    //public static Object getRootNO(String deptBusStop, String destBusStop){
    //    return RootManager.getInstance().getRoot(deptBusStop, destBusStop);
    //}
    //キューの発生処理
    public static void occureQueue(){
        //各バス停で人の発生処理
        for(BusStop bs : busStops.values()){
            //バス停での人の発生数
            int n = rand.nextInt(json.param.maxPassengers);
            
            //環境での人の発生限界のチェック
            if((amount - n) <= 0) n = amount;
            amount = amount - n;
            
            //バス停に人を発生
            if(n != 0)
                for(int i=0; i < n; i++)
                    bs.queuePeople(new People(bs));
        }
    }
    
    //public static Object compareRoot(){
    //    //return RootManager.getInstance().compareRoot();
    //    return RootManager.getInstance().compareRootQueue();
    //}
    
    //public static List<String> getCandidatePath(String bsName){
    //    return RootManager.getInstance().getCandidatePath(bsName);
    //}
    
    public static Boolean finish(){
        return busStops.values().stream()
                .allMatch(stop -> (stop.finish()));
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