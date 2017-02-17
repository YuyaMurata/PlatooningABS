/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import bus.SampleBusAgent;
import bus.SampleRobotBusAgent;
import center.CenterInfo;
import java.awt.Point;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import park.AmusementPark;
import prop.ABSSettings;

/**
 * BusAgentを管理するクラス
 * @author murata
 */
public class BusAgents implements ABSSettings{
    private static Map<Object, AbstractBusAgent> busAgents; //BusAgentの管理
    private static int man; //バス(人)の数
    private static int leaderNum; //初期のリーダーの振り分け 
    public static Boolean changeLineSW; //隊列の組み換え　true=あり
    private static AmusementPark park; //遊園地クラス
    
    //Bus Agentの作成
    public static void generate(){
        //リーダーバスの振り分け初期化
        leaderNum = 0;
        
        //遊園地クラスの取得
        park = AmusementPark.getInstance();
        
        //BusAgent 初期化
        int n = json.param.numBusAgents;
        man = json.param.numHuman;
        changeLineSW = json.param.changeLineSW;
        busAgents = new LinkedHashMap();
        AbstractBusAgent.setParam(json.param.maxPassengers, 
                                   json.param.lostProb,
                                   json.param.seed);
        
        //Human Bus Create
        for(int i=0; i < man; i++){
            String name = "BUS_"+i;
            AbstractBusAgent bus = new SampleBusAgent(name);
            busAgents.put(name, bus);
        }
        
        //Robot Bus Create
        if(n-man > 0)
            for(int i=man; i < n; i++){
                String name = "BUS_RB_"+i;
                busAgents.put(name, new SampleRobotBusAgent(name));
            }
        
        //Check BusStop
        busAgents.values().stream().forEach(System.out::println);
    }
    
    //全BusAgentの取得
    public static Collection<AbstractBusAgent> getBuses(){
        return busAgents.values();
    }
    
    //BusName -> BusPoint
    public static Point getBusPos(Object name){
        return busAgents.get(name).getBusPos();
    }
    
    //自身を中心とする半径rのバスを取得
    public static List nearestBus(Object key, int r){
        int[] pos = park.keyToPos(key);
        Object lowerKey = (pos[0]-r)+","+(pos[1]-r);
        Object upperKey = (pos[0]+r)+","+(pos[1]+r);
        
        List nearList = park.nearestBus(lowerKey, upperKey);
        
        return nearList;
    }
    
    //BusAgent(Robot)にリーダーを振り分ける
    public static Object getLeader(Object robot){
        return busAgents.values().toArray()[(leaderNum++) % man];
    }
    
    //TypeごとにBusAgentを実行
    public static void execute(String type, CenterInfo info){
        busAgents.values().stream()
                .filter(bus -> bus.type.equals(type))
                .forEach(bus -> ((AbstractBusAgent)bus).move(info));
    }
    
    //BusAgentの終了確認
    public static Boolean finish(){
        return busAgents.values().stream()
                .allMatch(bus -> bus.finish());
    }
    
    //ログ出力
    public static void printLog(){
        if(json.param.loggingSW)
            busAgents.values().stream().forEach(System.out::println);
    }
    
    //トレースログの出力
    public static String traceLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(AbstractBusAgent bus : busAgents.values()){
            sb.append(bus.toString());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}
