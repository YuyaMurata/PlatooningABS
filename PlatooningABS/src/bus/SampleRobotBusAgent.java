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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 後続車(Robot Bus) のサンプル
 * @author murata
 */
public class SampleRobotBusAgent extends AbstractBusAgent{
    private Object leader;
    private Object root;
    //待ち行列理論でのルート比較
    private LinkedList<Map<Object, List<Integer>>> rootTimeToQueue;
    
    public SampleRobotBusAgent(String name) {
        super(name, "robot");
        
        //初期の追従バスを設定し，ルートと位置を初期化
        AbstractBusAgent leaderBus = (AbstractBusAgent)BusAgents.getLeader(name);
        setLeader(leaderBus);
        super.setBusPos(leaderBus.x, leaderBus.y);
        
        //待ち行列理論の比較用リストを初期化
        rootTimeToQueue = new LinkedList();
    }
    
    //リーダーの設定
    public void setLeader(AbstractBusAgent bus){
        leader = bus.name;
        root = bus.root();
    }
    
    //隊列変更処理
    public void change(CenterInfo info){
        //乗員がいる場合は，隊列を変更しない
        if(super.getNumPassengers() != 0) return ;
        
        //ルートごとのキューを比較し，キューが最大のルートを返す
        //root = compRootQueue(info.getRootQueue());
        root = compRootQueue(info.getRootStepQueue(), 3);
    }
    
    //行動ルール
    @Override
    public Point planning(CenterInfo info) {
        //隊列変更
        change(info);
        
        //追従バスの位置をセンター情報から受け取り，移動目標とする
        Point p = (Point) info.getRootBus(root(), name)[1];
        
        //移動目標を返す
        return p;
    }
    
    //現在のルート
    @Override
    public Object root() {
        return root;
    }
    
    // ルート待ち人数の比較
    public Object compRootQueue(Map<Object, List<Integer>> rootQueue){
        //待ち人数が最大のルートを返す
        return rootQueue.entrySet().stream()
                            .max((e1, e2) -> 
                                e1.getValue().stream().mapToInt(q -> q).sum()
                                - e2.getValue().stream().mapToInt(q -> q).sum()
                            ).get().getKey();
    }
    
    // 待ち行列理論　ルート比較
    public Object compRootQueue(Map<Object, List<Integer>> rootQueue, int t){
        //t時間のルート待ち人数増加量を記憶
        rootTimeToQueue.add(rootQueue);
        if(rootTimeToQueue.size() > t) rootTimeToQueue.poll();
        
        //t時間のルート待ち人数の合計を計算
        Map<Object, Integer> queueMap = new HashMap();
        for(Map<Object, List<Integer>> queue : rootTimeToQueue){
            for(Object r : queue.keySet()){
                int total = 0;
                if(queueMap.get(r) != null)
                    total = queueMap.get(r);
                total += queue.get(r).stream().mapToInt(q -> q).sum();
                
                queueMap.put(r, total);
            }
        }
        
        // M/M/1 W=L/λ
        Map<Object, Integer> wmap = new HashMap();
        for(Object r : queueMap.keySet()){
            int line = queueMap.get(r);
            double w = (double)line / (double)rootTimeToQueue.size();
            
            System.out.println("L="+line+" W="+w+" Nstep="+rootTimeToQueue.size());
            
            wmap.put(r, (int)(w * 1000));
        }
        
        System.out.println(wmap);
        
        
        //t時間でのWが最大のルートを返す
        return wmap.entrySet().stream()
                            .max((e1, e2) -> e1.getValue() - e2.getValue())
                            .get().getKey();
    }
}
