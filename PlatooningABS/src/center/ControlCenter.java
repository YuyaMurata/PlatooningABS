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
 *
 * @author murata
 */
public class ControlCenter {
    public static CenterInfo comm(){
        //start
        long start = System.currentTimeMillis();
        
        CenterInfo info = new CenterInfo();
        
        Map map = new HashMap();
        
        List root = RootManager.getInstance().getRootList();
        Map rootQueueMap = new HashMap();
        Map rootStepQueueMap = new HashMap();
        Map rootBusMap = new HashMap();
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
            List busPointList = new ArrayList();
            BusAgents.getBuses().stream()
                                .filter(bus -> (bus.type.equals("human") && bus.root.equals(no)))
                                .forEach(bus -> {
                                    busPointList.add(bus.getBusPos());
                                });
            rootBusMap.put(no, busPointList);
        }
        map.put(CenterInfo.paramID.ROOT_QUEUE, rootQueueMap);
        map.put(CenterInfo.paramID.ROOT_STEP_QUEUE, rootStepQueueMap);
        map.put(CenterInfo.paramID.BUS_POS, rootBusMap);
        
        long stop = System.currentTimeMillis();
        
        map.put(CenterInfo.paramID.CALC_TIME, stop-start);
        
        //Set CenterInformation
        info.setParam(map);
        
        return info;
    }
}
