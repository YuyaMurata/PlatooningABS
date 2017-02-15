/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package center;

import agent.BusAgents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import obj.BusStops;
import root.RootManager;

/**
 * センター間通信で送られる情報を作成するクラス
 * CenterInformation を作成し，BusAgentに送る
 * @author murata
 */
public class ControlCenter{
    public static Boolean commfailur = true;
    
    //センター間の通信処理
    public static CenterInfo comm(){
        //start
        long start = System.currentTimeMillis();
        
        //センターが送る情報
        CenterInfo info = new CenterInfo();
        
        Map map = new HashMap();
        
        //設定されているルートの取得
        List root = RootManager.getInstance().getRootList();
        Map rootQueueMap = new HashMap(); //ルートごとのキューを保存
        Map rootStepQueueMap = new HashMap(); //ルートごとの毎ステップのキューの増加量を保存
        Map rootBusMap = new HashMap(); //ルートごとの先頭車両の位置情報を保存
        for(Object no : root){
            //Get BusStop Queue
            List busStopQueueList = new ArrayList();
            List busStopStepQueueList = new ArrayList();
            BusStops.getBusStops().stream()
                                    .forEach(bs->{
                                        busStopQueueList.add(bs.getQueueLength(no));
                                        busStopStepQueueList.add(bs.getStepQueue(no));
                                    });
            rootQueueMap.put(no, busStopQueueList);
            rootStepQueueMap.put(no, busStopStepQueueList);
        
            //Get Bus(Human) Position
            List rootBusList = new ArrayList();
            BusAgents.getBuses().stream()
                            .filter(bus -> bus.type.equals("man") && bus.root().equals(no))
                            .forEach(bus -> {
                                rootBusList.add(new Object[]{bus.name, bus.getBusPos()});
                            });
            rootBusMap.put(no, rootBusList);
        }
        
        //センターの通信情報を保存
        map.put(CenterInfo.infoID.ROOT_QUEUE, rootQueueMap);
        map.put(CenterInfo.infoID.ROOT_STEP_QUEUE, rootStepQueueMap);
        map.put(CenterInfo.infoID.ROOT_BUS, rootBusMap);
        
        //stop
        long stop = System.currentTimeMillis();
        map.put(CenterInfo.infoID.CALC_TIME, stop-start);
        
        //Set CenterInformation
        if(commfailur) info.setInfo(map);
        else info.setInfo(null);
        
        return info;
    }
}
