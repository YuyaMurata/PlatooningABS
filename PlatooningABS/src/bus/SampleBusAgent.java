/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus;

import agent.AbstractBusAgent;
import center.CenterInfo;
import java.awt.Point;
import java.util.List;
import obj.BusStop;
import obj.BusStops;
import root.RootManager;

/**
 * 先頭車(巡回バス)のサンプル　
 * @author murata
 */
public class SampleBusAgent extends AbstractBusAgent{
    private Object root;
    private int next;
    private Point p;
    private List<String> rootPath;
    
    public SampleBusAgent(String name) {
        super(name, "man");
        
        //ルート情報の設定と初期化
        RootManager rootManager = RootManager.getInstance();
        this.root = rootManager.getRoot(name);
        this.next = 0;
        this.rootPath = rootManager.getRootPath(root);
        BusStop busStop = BusStops.getBusStop(rootPath.get(next));
        super.setBusPos(busStop.x, busStop.y);
    }
    
    @Override
    public Point planning(CenterInfo info) {
        //目標バス停到着後，次のバス停の設定
        if(super.getBusPos().equals(p)) 
            next = (next + 1) % rootPath.size();
        
        //目標のバス停を取得し，移動目標を設定
        p = BusStops.getBusStop(rootPath.get(next)).getBusStopPos();
        
        //移動目標を返す
        return  p;
    }

    //現在のルート
    @Override
    public Object root() {
        return root;
    } 
}
