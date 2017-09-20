/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus;

import agent.AbstractBusAgent;
import agent.BusAgents;
import center.CenterInfo;
import java.awt.Point;
import obj.BusStops;

/**
 *
 * @author murata
 */
public class BusAgentInfo extends AbstractBusAgent{
    Object root;
    Object leader;
    
    public BusAgentInfo(String name, String type) {
        super(name, type);
    }
    
    @Override
    public void init(String param) {
        //Param
        infoPrint(param);
        
        //Root
        root = "No Root";
        infoPrint("Get Root="+root);
        
        //Leader
        leader = "No Leader";
        infoPrint("Get Leader="+leader);
        
        //Position
        super.setBusPos(0, 0);
        infoPrint("Get Position="+super.getBusPos());
        
        //Passenger
        infoPrint("Get Passenger="+super.getNumPassengers());
    }

    //情報の取得と表示
    @Override
    public Point planning(CenterInfo info) {
        //センター情報
        infoPrint("\n\tCenterInformation = \n\t\t"+info+"\n\t");
        
        //近隣バスの探索
        infoPrint("\n\tNearest Bus r=1 : "+BusAgents.nearestBus(super.key[1], 1)+"\n\t");
        
        //乗客の取得
        infoPrint("\n\tGet Passenger : "+super.getPassengers()+"\n\t");
        
        //停留所の到着確認
        infoPrint("\n\tArrival BusStop : "+super.arrivalBusStop()+"\n\t");
        
        //全停留所の取得
        infoPrint("\n\tGet BusStops : "+BusStops.getBusStops()+"\n\t");
        
        //停留所の待ち人を取得 e.g.　バス停(0番)のroot0を利用する人を取得
        infoPrint("\n\tGet BusStops : "+BusStops.getBusStop(0).getQueue("root0")+"\n\t");
        
        //全バスの取得
        infoPrint("\n\tGet Bus : "+BusAgents.getBuses()+"\n\t");
        
        //X=5 Y=5 地点に移動
        return new Point(5, 5);
    }

    @Override
    public Object root() {
        return root;
    }
    
    public void infoPrint(String str){
        System.out.println("BusAgentInfo:["+name+", "+type+"]<msg="+str+">");
    }
}
