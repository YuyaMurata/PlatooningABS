/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import bus.SampleBusAgent;
import bus.SampleRobotBusAgent;
import center.CenterInfo;
import java.util.ArrayList;
import java.util.List;
import prop.ABSSettings;

/**
 *
 * @author murata
 */
public class BusAgents implements ABSSettings{
    private static List<AbstractBusAgent> busAgents; //BusAgentリスト
    private static int man; //バス(人)の数
    private static int leaderNum; //初期のリーダーの振り分け 
    public static Boolean changeLineSW; //隊列の組み換え　true=あり
    
    //Bus Agentの作成
    public static void generate(){
        //リーダーバスの振り分け初期化
        leaderNum = 0;
        
        //BusAgent 初期化
        int n = json.param.numBusAgents;
        man = json.param.numHuman;
        changeLineSW = json.param.changeLineSW;
        busAgents = new ArrayList();
        AbstractBusAgent.setParam(json.param.maxPassengers, 
                                   json.param.lostProb,
                                   json.param.seed);
        
        //Human Bus Create
        for(int i=0; i < man; i++){
            //BusAgent bus = new BusAgent(i, "human"); 
            String name = "BUS_"+i;
            AbstractBusAgent bus = new SampleBusAgent(name);
            busAgents.add(bus);
        }
        
        //Robot Bus Create
        if(n-man > 0)
            for(int i=man; i < n; i++){
                String name = "BUS_RB_"+i;
                busAgents.add(new SampleRobotBusAgent(name));
            }
        
        //Check BusStop
        busAgents.stream().forEach(System.out::println);
    }
    
    //全BusAgentの取得
    public static List<AbstractBusAgent> getBuses(){
        return busAgents;
    }
    
    //BusAgent(Robot)にリーダーを振り分ける
    public static Object getLeader(Object robot){
        return busAgents.get((leaderNum++) % man);
    }
    
    //通信障害の発生
    public static Boolean getCommState(){
        return json.param.commFailure;
    }
    
    //TypeごとにBusAgentを実行
    public static void execute(String type, CenterInfo info){
        busAgents.stream()
                .filter(bus -> bus.type.equals(type))
                .forEach(bus -> ((AbstractBusAgent)bus).move(info));
    }
    
    //BusAgentの終了確認
    public static Boolean finish(){
        return busAgents.stream()
                .allMatch(bus -> bus.finish());
    
    }
    
    //ログ出力
    public static void printLog(){
        if(json.param.loggingSW)
            busAgents.stream().forEach(System.out::println);
    }
    
    //トレースログの出力
    public static String traceLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(AbstractBusAgent bus : busAgents){
            sb.append(bus.toString());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}
