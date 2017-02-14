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
import java.util.List;
import java.util.Map;

/**
 * 後続車(Robot Bus) のサンプル
 * @author murata
 */
public class SampleRobotBusAgent extends AbstractBusAgent{
    private Object leader;
    private Object root;
    
    public SampleRobotBusAgent(String name) {
        super(name, "robot");
        
        //初期の追従バスを設定し，ルートと位置を初期化
        AbstractBusAgent leaderBus = (AbstractBusAgent)BusAgents.getLeader(name);
        setLeader(leaderBus);
        super.setBusPos(leaderBus.x, leaderBus.y);
    }
    
    //リーダーの設定
    public void setLeader(AbstractBusAgent bus){
        leader = bus.name;
        root = bus.root();
    }
    
    //隊列変更処理
    public void change(Map<Object, List<Integer>> rootQueue){
        //乗員がいる場合は，隊列を変更しない
        if(super.getNumPassengers() != 0) return ;
        
        //ルートごとのキューを比較し，キューが最大のルートを返す
        this.root = rootQueue.entrySet().stream()
                            .max((e1, e2) -> 
                                e1.getValue().stream().mapToInt(q -> q).sum()
                                - e2.getValue().stream().mapToInt(q -> q).sum()
                            ).get().getKey();
    }
    
    //行動ルール
    @Override
    public Point planning(CenterInfo info) {
        //隊列変更
        change(info.getRootQueue());
        
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
    
}
