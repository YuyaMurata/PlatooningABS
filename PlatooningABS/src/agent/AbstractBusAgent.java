/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import center.CenterInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import obj.BusStop;
import obj.People;
import park.AmusementPark;
import root.RootManager;

/**
 * Bus Agentの抽象化クラス　BusAgent作成時に継承する
 * (SampleBusAgent を参照)
 * @author murata
 */
public abstract class AbstractBusAgent {
    public static int maxPassengers; //最大乗車数
    public static double lostProb; //迷子確率
    private static long seed; //乱数のシード
    private static AmusementPark park = AmusementPark.getInstance(); //遊園地クラス
    private static RootManager rootManager = RootManager.getInstance(); //ルート管理
    
    public String name, type; //type = Human or Robot
    public Object[] key = new Object[2]; //現在と前の状態をキーとする(遊園地への記録用)
    public int x, y; //位置
    private List<People> passengers = new ArrayList(); //乗車リスト
    
    //Bus Agent 行動ルール
    public abstract Point planning(CenterInfo info);
    //Bus Agent の巡回ルート
    public abstract Object root();
    
    public AbstractBusAgent(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    //パラメータセット(最大乗車数)
    public static void setParam(int m, double prob, long s){
        maxPassengers = m;
        lostProb = prob;
        seed = s;
    }
    
    //Agentの移動
    public void move(CenterInfo info){
        //BusAgent Control
        Point target = planning(info);
        
        //Move
        deltaMove(x, target.x, y, target.y);
        
        //バス停の到着処理
        busStopCheck();
    }
    
    //Agent 1マス移動 (現在地(x,y start)　→　移動目標(x,y goal))
    private void deltaMove(int xstart, int xgoal, int ystart, int ygoal){
        //x,yの距離
        int xd = xgoal - xstart;
        int yd = ygoal - ystart;
        
        //移動前の位置
        int bx = this.x;
        int by = this.y;

        //x Move
        if(xd > 0) bx++;
        else if(xd < 0) bx--;
        
        //y Move
        if(yd > 0) by++;
        else if(yd < 0) by--; 
        
        //移動後の位置をセット
        setBusPos(bx, by);
    }
    
    //バス位置の設定
    public void setBusPos(int x, int y){        
        //Before
        key[0] = this.x+","+this.y;
        
        //After
        this.x = x;
        this.y = y;
        key[1] = x+","+y;
        
        //遊園地に状態を記録
        park.setBusAgent(this);
    }
    
    //バスの現在位置の取得
    public Point getBusPos(){        
        return new Point(x, y);
    }
    
    //バス停処理
    private void busStopCheck(){
        //現在地にバス停が存在するかチェック
        BusStop busStop = park.arrivalBusStop(key[1]);
        if(busStop != null){
            //降車
            getOff(busStop);
            //乗車
            getOn(busStop);
        }
    }
    
    //乗車処理
    private Integer getOn(BusStop busStop){
        //乗車人数
        int num = 0;
        
        Iterator<People> it = busStop.getQueue(root()).iterator();
        while(it.hasNext()){
            //乗車が可能かチェック
            if(maxPassengers <= passengers.size()) return num;
            
            //バス停のキューから巡回ルートが同一の人を取り出し，バスに追加
            People people = it.next();
            if(rootManager.getRootPath(root()).contains(people.getDestination())){
                passengers.add(people);
                
                //乗客の乗車時間を記録
                people.getOnTime(name);
                it.remove();
                num++;
            }
        }
        return num;
    }
    
    //降車処理
    private Integer getOff(BusStop busStop){
        //降車人数
        int num = 0;
        
        Iterator it = passengers.iterator();
        while(it.hasNext()){
            People people = (People) it.next();
            
            //降車する人を取り出し，バスから削除
            if(people.getOffCheck(busStop)){
                it.remove();
                num++;
            }
        }
        return num;
    }
    
    //乗客の人数の取得
    public Integer getNumPassengers(){
        return passengers.size();
    }
    
    //Bus Agent の終了チェック
    public Boolean finish(){
        return passengers.isEmpty();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("<");
        sb.append(type);
        sb.append(">");
        sb.append(":[x=");
        sb.append(x);
        sb.append(" ,y=");
        sb.append(y);
        sb.append("]");
        sb.append("-[PAX=");
        sb.append(getNumPassengers());
        sb.append("]");
        return sb.toString();
    }
}
