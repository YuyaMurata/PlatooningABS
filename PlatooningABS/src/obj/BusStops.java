/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import fileout.OutputInstance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import prop.ABSSettings;
import queue.QueueGenerator;
import queue.QueueType;
import root.RootManager;

/**
 * バス停を管理するクラス
 * @author kaeru
 */
public class BusStops implements ABSSettings{
    private static int numOfBusStops; //バス停の数
    private static Map<String, BusStop> busStops; //全バス停
    private static List<String>nameList; //全バス停の名前
    private static int amount; //実験で発生する人の数
    private static Random rand = new Random(); //人発生用の乱数
    private static QueueGenerator queGen; //待ち行列発生
    private static int mode; //Mode 0 = PeopleMode 1 = StepMode
    
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
        
        //Mode
        mode = json.param.mode;
        
        //バス停の数
        numOfBusStops = json.param.numBusStops;
        
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
        
        try {
            //QueueGenerator 初期化
            Class clazz = Class.forName(json.param.queueClassName, true, absClassLoader);
            queGen = new QueueGenerator((QueueType) clazz.newInstance());
            queGen.setSeed(json.param.seed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //簡易初期化
    private static void initialize(){
        //init BusStop
        for(BusStop bs : json.param.busStops){
            BusStop busStop = getBusStop(bs.name);
            busStop.init();
            busStop.setBusStop(bs.x, bs.y);
        }
    }
    
    //バス停の数を取得
    public static Integer getNumBusStop(){
        return numOfBusStops;
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
        return new ArrayList(busStops.values());
    }
    
    //キューの発生処理
    public static void occureQueue(){
        //環境の1ステップの人発生数
        int n = json.param.maxPassengers;
            
        //発生限界のチェック
        if((amount - n) <= 0 && (mode == 0)) n = amount;
            amount = amount - n;
            
        //バス停に人を発生
        if(n != 0)
            for(int i=0; i < n; i++)
                queGen.generate(getBusStops());
    }
    
    //シミュレーションの終了確認
    public static Boolean finish(){
        if(amount > 0) return false;
        return busStops.values().stream()
                .allMatch(stop -> (stop.finish()));
    }
    
    //コンソールログ出力
    public static void printLog(){
        String str = busStops.values().stream()
                            .map(stop -> stop.toString())
                            .collect(Collectors.joining("\n"));
        OutputInstance.consoleOut(str);
    }
    
    //トレースログ出力
    public static String traceLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(String bsName : busStops.keySet()){
            sb.append(getBusStop(bsName).toString());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
    
    //人発生時の乱数シード
    private static void setSeed(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
}