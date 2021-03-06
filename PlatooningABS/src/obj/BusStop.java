/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import park.AmusementPark;
import root.RootManager;

/**
 * バス停クラス
 * @author murata
 */
public class BusStop {
    public final String name;
    public int x, y; //バス停の位置
    
    public transient Object key; //遊園地クラス上でのバス停の管理キー
    private transient Integer totalQueue;
    
    //バス停のルートごとのキュー
    private transient Map<Object, List<People>> queue;
    
    //バス停のルートごとのステップキューの増加量
    private transient Map<Object, Integer> countStepQueue;
    
    public BusStop(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        
        init();
        
        //バス停の配置
        setBusStop(x, y);
    }
    
    public void init(){
        this.queue = new HashMap<>();
        this.countStepQueue = new HashMap<>();
        this.totalQueue = 0;
    }
    
    //バス停の配置
    public void setBusStop(int x, int y){
        this.x = x;
        this.y = y;
        
        //遊園地にバス停を配置
        key = x+","+y;
        AmusementPark.getInstance().setBusStop(this);
    }
    
    //バス停の位置を取得
    public Point getBusStopPos(){
        return new Point(x, y);
    }
    
    //バス停での人の発生処理
    public void queuePeople(People people){
        //人の待ち時間の初期化
        people.queueTime();
        
        //人の巡回バスのルートを取得
        Object rootNo = RootManager.getInstance().getRoot(
                            people.getDeprture(), 
                            people.getDestination());
        if(queue.get(rootNo) == null)
            queue.put(rootNo, new ArrayList<>());
        
        //ルートごとのキューに人を追加
        queue.get(rootNo).add(people);
        
        //ステップごとに発生する人のカウント
        if(countStepQueue.get(rootNo) == null)
            countStepQueue.put(rootNo, 0);
        countStepQueue.put(rootNo, countStepQueue.get(rootNo)+1);
        totalQueue++;
    }
    
    //ステップごとのキューの増加量を取得
    public Integer getStepQueue(Object rootNo){
        //1ステップで発生した人数を取得
        Integer queuePeople = countStepQueue.get(rootNo);
        
        //人数カウント初期化
        countStepQueue.put(rootNo, 0);
        
        if(queuePeople == null) return 0;
        
        return queuePeople;
    }
    
    //ルートのキューを取得
    public List<People> getQueue(Object root){
        if(queue.get(root) == null) return new ArrayList();
        return queue.get(root);
    }
    
    //ルートのキューの長さを取得
    public int getQueueLength(Object rootNo){
        //キューが発生していないときの処理
        if(queue == null) return 0;
        else if(queue.get(rootNo) == null) return 0;
        
        //キュー長
        return queue.get(rootNo).size();
    }
    
    //ルート関係なく発生しているキューの長さ
    public int getAllQueueLength(){
        if(queue == null) return 0;
        return queue.values().stream().mapToInt(q -> q.size()).sum();
    }
    
    public int getTotalQueue(){
        return totalQueue;
    }
    
    //終了確認 バス停のキューがなくなったら終了
    public Boolean finish(){
        return queue.values().stream()
                        .allMatch(q -> q.isEmpty());
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(":[x=");
        sb.append(x);
        sb.append(" ,y=");
        sb.append(y);
        sb.append("]");
        
        if(queue == null) return sb.toString();
        
        sb.append("-[QLine=");
        sb.append(getAllQueueLength());
        sb.append("]");
        return sb.toString();
    }
}
