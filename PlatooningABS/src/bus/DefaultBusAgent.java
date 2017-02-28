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
public class DefaultBusAgent extends AbstractBusAgent{
    Object root;
    Object leader;
    
    public DefaultBusAgent(String name, String type) {
        super(name, type);
    }
    
    @Override
    public void init(String param) {
        //Param
        print(param);
        
        //Root
        root = "No Root";
        print("Get Root="+root);
        
        //Leader
        leader = "No Leader";
        print("Get Leader="+leader);
        
        //Position
        super.setBusPos(0, 0);
        print("Get Position="+super.getBusPos());
        
        //Passenger
        print("Get Passenger="+super.getNumPassengers());
    }

    //情報の取得と表示
    @Override
    public Point planning(CenterInfo info) {
        //センター情報
        print("\n\tCenterInformation = \n\t\t"+info+"\n\t");
        
        //近隣バスの探索
        print("\n\tNearest Bus r=1 : "+BusAgents.nearestBus(super.key[1], 1)+"\n\t");
        
        //乗客の取得
        print("\n\tGet Passenger : "+super.getPassengers()+"\n\t");
        
        //停留所の到着確認
        print("\n\tArrival BusStop : "+super.arrivalBusStop()+"\n\t");
        
        //全停留所の取得
        print("\n\tGet BusStops : "+BusStops.getBusStops()+"\n\t");
        
        //停留所の待ち人を取得 e.g.　バス停(0番)のroot0を利用する人を取得
        print("\n\tGet BusStops : "+BusStops.getBusStop(0).getQueue("root0")+"\n\t");
        
        //全バスの取得
        print("\n\tGet Bus : "+BusAgents.getBuses()+"\n\t");
        
        //X=5 Y=5 地点に移動
        return new Point(5, 5);
    }

    @Override
    public Object root() {
        return root;
    }
    
    public void print(String str){
        System.out.println("DefaultBusAgent:["+name+", "+type+"]<msg="+str+">");
    }
}
